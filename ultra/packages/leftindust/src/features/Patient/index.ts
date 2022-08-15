import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';

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
  ethnicity: object,
  genderIdentity?: string,
  insuranceNumber?: string,
}

const defaultPatientForm: CreatePatient = {
  firstName: '',
  middleName: '',
  lastName: '',
  ethnicity:{},
  genderIdentity: '',
  insuranceNumber:''
};

export const createPatientFormValidator = () => {
  const schema = yup.object({
    firstName: yup.string().required('First name is required'),
    middleName: yup.string(),
    lastName: yup.string().required('Last name is required'),
    ethnicity: yup.object().required(),
    genderIdentity: yup.string(),
    insuranceNumber: yup.string(),
  });

  return createForm<yup.InferType<typeof schema>>({
    initialValues: defaultPatientForm,
    onSubmit: (form) => console.log(form),
    extend: [validator({ schema })]
  });
}