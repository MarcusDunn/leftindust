import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';

import { client, AddPatientDocument, type AddPatientMutation, EditPatientDocument, type EditPatientMutation, AddressType, EmailType, PhoneType, Countries } from '@/api/server';
import type {
  MutationAddPatientArgs,
  MutationEditPatientArgs,
} from '@/api/server';

import { Ethnicity, Sex, type EditPatient, type CreatePatient } from '@/api/server';

import getNativeAPI from '@/api/bridge';
import { get } from 'svelte/store';

import { _ } from '@/language';
import { closeWizard } from '../Wizard';
import { sendTrigger } from '../Triggers';

export enum PatientTab {
  Overview = 'Overview',
  Documents = 'Documents',
  Records = 'Records',
  Contacts = 'Contacts'
}

const { Dialog } = getNativeAPI();

const language = get(_);

/**
 * Creates form validator schema for patients
 */
const createPatientFormSchema = yup.object({
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

type PatientFormSchema = yup.InferType<typeof createPatientFormSchema>;

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
 * Creates a patient form on submit
 */
export const createPatientForm = (pid?: string) => createForm<PatientFormSchema>({
  initialValues: defaultPatientForm,
  onSubmit: async (form, { reset }) => {
    const patient = {
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

    try {
      if (pid) {
        /*
          TODO: Edit patient
          await editPatient({
            pid: {
              value: pid,
            },
            ...patient,
          });
          */
      } else {
        await addPatient(patient);
      }
      
      reset();
      sendTrigger('patient-update');
      
      closeWizard();
    } catch (error) {
      void Dialog.alert({
        message: language('errors.internalError'),
        detail: (error as Error).message,
        buttons: [language('generics.ok')],
        defaultId: 0,
      });
    }
  },
  onError: (error) => console.log(error),
  extend: [validator({ schema: createPatientFormSchema })],
});