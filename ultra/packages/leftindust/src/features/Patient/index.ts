import { validator } from '@felte/validator-yup';
import type { SubmitContext } from '@felte/common'
import { createForm } from 'felte';
import * as yup from 'yup';

import { Ethnicity, Month, Sex } from '@/api/server';

export enum PatientTab {
  Overview = 'Overview',
  Documents = 'Documents',
  Records = 'Records',
  Contacts = 'Contacts'
}

/**
 * Type of the parameters to create a patient
 */
export type CreatePatient = {
  firstName: string,
  middleName?: string,
  lastName: string,
  dateOfBirth: {
    day?: number,
    month?: Month,
    year?: number,
  }
  ethnicity?: Ethnicity,
  sex: Sex,
  genderIdentity?: string,
  insuranceNumber?: string,
}

/**
 * Default patient form inputs with CreatePatient type
 */
const defaultPatientForm: CreatePatient = {
  firstName: '',
  middleName: '',
  lastName: '',
  dateOfBirth: {
    day: 1,
    month: Month.Jan,
    year: new Date().getFullYear(),
  },
  ethnicity: undefined,
  sex: Sex.Male,
  genderIdentity: '',
  insuranceNumber:''
};

/**
 * Creates form validator schema for patients
 */
const createPatientFormSchema: yup.SchemaOf<CreatePatient> = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  ethnicity: yup.mixed<Ethnicity>().oneOf(Object.values(Ethnicity)),
  dateOfBirth: yup.object({
    day: yup.number().required(),
    month: yup.mixed<Month>().oneOf(Object.values(Month)).required(),
    year: yup.number().required(),
  }),
  sex: yup.mixed<Sex>().oneOf(Object.values(Sex)).required(),
  genderIdentity: yup.string(),
  insuranceNumber: yup.string(),
});

type CreatePatientFormSchema = yup.InferType<typeof createPatientFormSchema>;
/**
 * On submit callback for form submit
 */
type CreatePatientFormOnSubmit<Data extends Record<string, unknown>> = (
  values: Data,
  context: SubmitContext<Data>
) => Promise<unknown> | unknown;

/**
 * Creates a patient form on submit
 * @param onSubmit callback for submitting the form
 */
export const createPatientForm = (onSubmit: CreatePatientFormOnSubmit<CreatePatientFormSchema>) => {
  return createForm<CreatePatientFormSchema>({
    initialValues: defaultPatientForm,
    onSubmit,
    onError: (error) => console.log(error),
    extend: [validator({ schema: createPatientFormSchema })]
  });
}