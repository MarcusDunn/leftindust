import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';

import { Ethnicity } from '@/api/server';

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
  ethnicity?: Ethnicity,
  genderIdentity?: string,
  insuranceNumber?: string,
}

const defaultPatientForm: CreatePatient = {
  firstName: '',
  middleName: '',
  lastName: '',
  ethnicity: undefined,
  genderIdentity: '',
  insuranceNumber:''
};

export const createPatientFormValidator = () => {
  const schema = yup.object({
    firstName: yup.string().required(),
    middleName: yup.string(),
    lastName: yup.string().required(),
    ethnicity: yup.mixed<keyof typeof Ethnicity>().oneOf(Object.values(Ethnicity)),
    genderIdentity: yup.string(),
    insuranceNumber: yup.string(),
  });

  return createForm<yup.InferType<typeof schema >>({
    initialValues: defaultPatientForm,
    onSubmit: (form) => console.log(form),
    extend: [validator({ schema })]
  });
}