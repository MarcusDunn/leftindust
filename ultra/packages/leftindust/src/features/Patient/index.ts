import { _ } from '@/language';
import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';

import { client, AddPatientDocument, type AddPatientMutation, EditPatientDocument, type EditPatientMutation, AddressType, EmailType, PhoneType, Countries, type PatientFragment, Relationship } from '@/api/server';
import type {
  MutationAddPatientArgs,
  MutationEditPatientArgs,
} from '@/api/server';

import { Ethnicity, Sex } from '@/api/server';

import { get } from 'svelte/store';

import { openDialog } from '../UI/components/Dialog';

export enum PatientTab {
  Overview = 'Overview',
  Documents = 'Documents',
  Records = 'Records',
  Contacts = 'Contacts'
}

const language = get(_);

/**
 * Creates form validator schema for patients
 */
const patientFormSchema = yup.object({
  nameInfo: yup.object({
    firstName: yup.string().required(),
    middleName: yup.string(),
    lastName: yup.string().required(),
  }),
  ethnicity: yup.mixed<Ethnicity>().oneOf(Object.values(Ethnicity)).required(),
  dateOfBirth: yup.string().required(),
  sex: yup.mixed<Sex>().oneOf(Object.values(Sex)).required(),
  gender: yup.string(),
  insuranceNumber: yup.string(),
  addresses: yup.array(yup.object({
    address: yup.string().required(),
    addressType: yup.mixed<AddressType>().oneOf(Object.values(AddressType)).required(),
    city: yup.string().required(),
    country: yup.mixed<Countries>().oneOf(Object.values(Countries)).required(),
    postalCode: yup.string().required(),
    province: yup.string().required(),
  })),
  emails: yup.array(yup.object({
    email: yup.string().required(),
    type: yup.mixed<EmailType>().oneOf(Object.values(EmailType)).required(),
  })),
  phones: yup.array(yup.object({
    number: yup.string().required(),
    type: yup.mixed<PhoneType>().oneOf(Object.values(PhoneType)).required(),
  })),
});
type PatientFormSchema = yup.InferType<typeof patientFormSchema>;

/**
 * Default patient form inputs with CreatePatient type
 */
const defaultPatientForm: Partial<PatientFormSchema> = {
  nameInfo: {
    firstName: '',
    middleName: '',
    lastName: '',
  },
  dateOfBirth: '',
  ethnicity: undefined,
  sex: undefined,
  gender: '',
  insuranceNumber: '',
  addresses: [],
  emails: [],
  phones: [],
};

/**
 * @param patient the patient to edit
 * @returns A form with the fields filled out with the patient's data
 */
function filledPatientForm(patient: PatientFragment): Partial<PatientFormSchema> {
  return {
    nameInfo: { // TODO: schema inconsistency: (`patient` doesn't have object `nameinfo`)
      firstName: patient.firstName,
      middleName: patient.middleName,
      lastName: patient.lastName,
    },
    dateOfBirth: patient.dateOfBirth.replace(/\//g, '-'),
    ethnicity: patient.ethnicity,
    sex: patient.sex,
    gender: patient.gender ?? patient.sex,
    insuranceNumber: patient.insuranceNumber,
    addresses: [], // TODO: schema inconsistency: `addresses` of incompatible types
    emails: patient.emails,
    phones: patient.phoneNumbers,
  };
}

/**
 * Adds a patient
 * @param patient The patient to add
 * @returns The added patient
 */
export const addPatient = async (patient: NonNullable<MutationAddPatientArgs['createPatient']>): Promise<AddPatientMutation> => {
  const result = await client.mutation(AddPatientDocument, { patient }).toPromise();
  
  const data = result.data;
  const userName = `${patient.nameInfo.firstName} ${patient.nameInfo.lastName}`;
  if (result.error) throw new Error(`Failed to add patient ${userName}: ${result.error.message}`);
  else if (!data) throw new Error(`Failed to add patient ${userName}: No data returned`);
  return data;
};

/**
 * Edits a patient
 * @param patient The patient to edit
 * @returns The edited patient
 */
export const editPatient = async (patient: NonNullable<MutationEditPatientArgs['editPatient']>): Promise<EditPatientMutation> => {
  const result = await client.mutation(EditPatientDocument, { editPatient: patient }).toPromise();

  const data = result.data;
  if (result.error) throw new Error(`Failed to edit patient id ${patient.pid}: ${result.error.message}`);
  else if (!data) throw new Error(`Failed to edit patient id ${patient.pid}: No data returned`);
  return data;
};

/**
 * Creates or edits a patient form on submit
 */ 
export const createPatientForm = (closeWizardHandler: () => void, patient: PatientFragment | undefined) => createForm<PatientFormSchema>({
  initialValues: patient ? filledPatientForm(patient) : defaultPatientForm,
  onSubmit: async (form, { reset }) => {
    const createPatient: MutationAddPatientArgs['createPatient'] = {
      nameInfo: {
        firstName: form.nameInfo.firstName,
        middleName: form.nameInfo.middleName,
        lastName: form.nameInfo.lastName,
      },
      dateOfBirth: form.dateOfBirth.replace(/\//g, '-'),
      ethnicity: form.ethnicity,
      sex: form.sex,
      gender: form.gender ?? form.sex,
      insuranceNumber: form.insuranceNumber,
      addresses: form.addresses,
      emails: form.emails,
      phones: form.phones,
    };

    const editPatientForm: MutationEditPatientArgs['editPatient'] = {
      pid: {
        value: patient?.id.value,
      },
      nameInfo: {
        firstName: form.nameInfo.firstName,
        middleName: form.nameInfo.middleName,
        lastName: form.nameInfo.lastName,
      },
      dateOfBirth: form.dateOfBirth.replace(/\//g, '-'),
      ethnicity: form.ethnicity,
      sex: form.sex,
      gender: form.gender ?? form.sex,
      insuranceNumber: form.insuranceNumber,
      addresses: form.addresses?.map((address) => ({
        address: address.address,
        addressType: address.addressType,
        city: address.city,
        country: address.country,
        postalCode: address.postalCode,
        province: address.province,
      })),
      emails: form.emails?.map((email) => ({
        email: email.email,
        type: email.type,
      })),
      phones: form.phones?.map((phone) => ({
        number: phone.number,
        type: phone.type,
      })),
    };

    try {
      if (patient) {
        await editPatient({
          ...editPatientForm,
        });
        reset;
      } else {
        await addPatient(createPatient);
        reset;
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
  extend: [validator({ schema: patientFormSchema })],
});