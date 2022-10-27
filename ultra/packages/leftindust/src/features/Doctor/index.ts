import { _ } from '@/language';
import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';
import { get } from 'svelte/store';
import { AddDoctorMutationDocument, AddressType, client, Countries, CreateDoctorUserType, EditDoctorDocument, EmailType, PhoneType, type AddDoctorMutationMutation, type DoctorFragment, type EditDoctorMutation, type MutationAddDoctorArgs, type MutationEditDoctorArgs } from '@/api/server';
import { openDialog } from '../UI/components/Dialog';

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
 * Default doctor form for inputs of type CreateDoctor
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
 * @param doctor the doctor to edit
 * @return A form pre-filled with the doctor's data
 */
function filledDoctorForm(doctor: DoctorFragment): Partial<DoctorFormSchema> {
  return {
    ...doctor,
    addresses: [], // TODO: schema inconsistency: `addresses` of incompatible types
    phones: doctor.phoneNumbers,  // TODO: schema inconsistency: naming
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
export const createDoctorForm = (closeWizardHandler: () => void, doctor?: DoctorFragment) => createForm<DoctorFormSchema>({
  initialValues: doctor ? filledDoctorForm(doctor) : defaultDoctorForm,
  onSubmit: async (form, { reset }) => {
    try {
      if (doctor) {
        // Form attempts to modify existing doctor as specified by user
        await editDoctor({
          addresses: form.addresses,
          clinics: [],
          dateOfBirth: form.dateOfBirth,
          did: {
            value: doctor.id?.value,
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
        });
      } else {
        // Otherwise, form adds a new doctor
        await addDoctor({
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
        });
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