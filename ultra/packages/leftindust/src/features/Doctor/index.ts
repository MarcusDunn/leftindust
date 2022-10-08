import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';
import { get } from 'svelte/store';
import { _ } from '@/language';
import { AddDoctorMutationDocument, AddressType, client, Countries, CreateDoctorUserType, EmailType, PhoneType, type AddDoctorMutationMutation, type CreateDoctor, type MutationAddDoctorArgs } from '@/api/server';
import { closeWizard } from '../Wizard';
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

type DoctorFormSchema = yup.InferType<typeof createDoctorFormSchema>;

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

export const addDoctor = async (doctor: NonNullable<MutationAddDoctorArgs['createDoctor']>): Promise<AddDoctorMutationMutation> => {
  const result = await client.mutation(AddDoctorMutationDocument, { doctor }).toPromise();

  if (result.error) throw result.error;
  if (!result.data) throw new Error('No data returned from server');

  return result.data;
};

/**
 * Creates a doctor form on submit
 */
export const createDoctorForm = (did?: string, fetcher?: () => void) => createForm<DoctorFormSchema>({
  initialValues: defaultDoctorForm,
  onSubmit: async (form) => {
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


    try {
      if (did) {
        /*
          TODO: Edit patient
          await editDoctor({
            pid: {
              value: pid,
            },
            ...patient,
          });
          */
      } else {
        await addDoctor(doctor);
      }
      
      fetcher?.();
      closeWizard();
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
  onError: (error) => console.log(error),
  extend: [validator({ schema: createDoctorFormSchema })],
});