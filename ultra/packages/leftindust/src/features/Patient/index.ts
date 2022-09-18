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


export type CreatePatient = {
  firstName: string,
  middleName?: string,
  lastName: string,
  day?: number,
  month?: Month,
  year?: number,
  ethnicity?: Ethnicity,
  sex: Sex,
  genderIdentity?: string,
  insuranceNumber?: string,
}

const defaultPatientForm: CreatePatient = {
  firstName: '',
  middleName: '',
  lastName: '',
  day: undefined,
  month: Month.Jan,
  year: undefined,
  ethnicity: undefined,
  sex: Sex.Male,
  genderIdentity: '',
  insuranceNumber:''
};

const createPatientFormSchema = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  day: yup.number().required(),
  month: yup.mixed<Month>().oneOf(Object.values(Month)).required(),
  year: yup.number().required(),
  ethnicity: yup.mixed<Ethnicity>().oneOf(Object.values(Ethnicity)),
  sex: yup.mixed<Sex>().oneOf(Object.values(Sex)).required(),
  genderIdentity: yup.string(),
  insuranceNumber: yup.string(),
});

type CreatePatientFormSchema = yup.InferType<typeof createPatientFormSchema>;
type CreatePatientFormOnSubmit<Data extends Record<string, unknown>> = (
  values: Data,
  context: SubmitContext<Data>
) => Promise<unknown> | unknown;

export const createPatientForm = (onSubmit: CreatePatientFormOnSubmit<CreatePatientFormSchema>) => {
  return createForm<CreatePatientFormSchema>({
    initialValues: defaultPatientForm,
    onSubmit,
    onError: (error) => console.log(error),
    extend: [validator({ schema: createPatientFormSchema })]
  });
}