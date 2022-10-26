import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';
import { get } from 'svelte/store';
import { _ } from '@/language';
import { AddDoctorMutationDocument, AddressType, client, Countries, CreateDoctorUserType, EditDoctorDocument, EmailType, PhoneType, type AddDoctorMutationMutation, type CreateDoctor, type DoctorIdInput, type EditDoctor, type EditDoctorMutation, type MutationAddDoctorArgs, type MutationEditDoctorArgs } from '@/api/server';
import { closeWizard } from '../Wizard';
import { openDialog } from '../UI/components/Dialog';
import { da } from 'date-fns/locale';
import { format } from 'date-fns';
import { selectedDoctor, isDoctorSelected } from '../Doctors/store';

const language = get(_);

/**
 * Creates a doctor form schema validator
 */
const createDoctorFormSchema = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  title: yup.string().required(),
  dateOfBirth: yup.string(),
  addresses: yup.array(yup.object({
    address: yup.string().required(),
    addressType: yup.mixed<AddressType>().oneOf(Object.values(AddressType)).required(),
    city: yup.string().required(),
    country: yup.mixed<Countries>().oneOf(Object.values(Countries)).required(),
    postalCode: yup.string().required(),
    province: yup.string().required(),
  })).required(),
  emails: yup.array(yup.object({
    email: yup.string().required(),
    type: yup.mixed<EmailType>().oneOf(Object.values(EmailType)).required(),
  })).required(),
  phones: yup.array(yup.object({
    number: yup.string().required(),
    type: yup.mixed<PhoneType>().oneOf(Object.values(PhoneType)).required(),
  })).required(),
});

export type DoctorFormSchema = yup.InferType<typeof createDoctorFormSchema>;

/**
 * Default fields for a doctor form
 */
const defaultDoctorForm: DoctorFormSchema = {
  firstName: '',
  middleName: '',
  lastName: '',
  title: '',
  dateOfBirth: undefined,
  addresses: [],
  emails: [],
  phones: [],
};

/**
 * Generates a DoctorFormSchema for the currently selected doctor
 * @param did the doctor's id, returns defaultDoctorForm if not provided
 * @return the generated schema for the doctor, or defaultDoctorForm if none found
 */
function getDoctorFormData(): DoctorFormSchema {
  if (!get(isDoctorSelected)) return defaultDoctorForm;
  const doctor = get(selectedDoctor);
  if (!doctor) return defaultDoctorForm;

  return {
    ...doctor,
    title: doctor.title ?? '',
    addresses: [], // TODO: adjust after fixing schema inconsistencies
    phones: doctor.phoneNumbers, 
  };
}

/**
 * Adds a new doctor to the database
 * @param doctor The doctor object to add
 * @returns the added doctor object
 */
export const addDoctor = async (doctor: NonNullable<MutationAddDoctorArgs['createDoctor']>): Promise<AddDoctorMutationMutation> => {
  const result = await client.mutation(AddDoctorMutationDocument, { doctor }).toPromise();

  if (result.error) throw result.error;
  if (!result.data) throw new Error('No data returned from server');

  return result.data;
};

/**
 * Edits the data of a doctor in the database
 * @param doctor The doctor object to edit
 * @returns The edited doctor object
 */
export const editDoctor = async (doctor: NonNullable<MutationEditDoctorArgs['editDoctor']>): Promise<EditDoctorMutation> => {
  const result = await client.mutation(EditDoctorDocument, { editDoctor: doctor }).toPromise();

  console.log('Result', result);

  const data = result.data;
  if (result.error) throw new Error(`Failed to edit doctor id ${doctor.did}: ${result.error.message}`);
  else if (!data) throw new Error(`Failed to edit doctor id ${doctor.did}: No data returned`);

  return data;
};

/**
 * Creates a doctor form on submit
 * @returns the doctor form generated and its corresponding utilities
 */
export const createDoctorForm = (closeWizardHandler: () => void, did?: string) => createForm<DoctorFormSchema>({
  initialValues: getDoctorFormData(),
  onSubmit: async (form, { reset }) => {
    try {
      if (did) {
        // Modify doctor with specified ID, if one exists
        const doctor: EditDoctor = {
          addresses: form.addresses,
          clinics: [],
          dateOfBirth: form.dateOfBirth,
          did: {
            value: did,
          },
          emails: form.emails,
          nameInfo: {
            firstName: form.firstName,
            middleName: form.middleName,
            lastName: form.lastName,
          },
          patients: [],
          phones: form.phones,
          title: form.title,
        };

        await editDoctor(doctor);
      } else {
        // Otherwise, add new doctor with this ID
        const doctor: CreateDoctor = {
          dateOfBirth: form.dateOfBirth,
          addresses: form.addresses,
          emails: form.emails,
          phones: form.phones,
          title: form.title,
          clinic: [],
          patients: [],
          user: {
            discriminant: CreateDoctorUserType.NoUser,
            nameInfo: {
              firstName: form.firstName,
              middleName: form.middleName,
              lastName: form.lastName,
            },
          },
        };

        await addDoctor(doctor);
      }
      
      closeWizardHandler();
    } catch (error) {
      openDialog({
        title:  language('errors.internalError'),
        text: (error as Error).message,
        buttons: [
          {
            label: language('generics.ok'),
            primary: true,
          },
        ],
      });
    }
  },
  onError: (error) => console.error(error),
  extend: [validator({ schema: createDoctorFormSchema })],
});