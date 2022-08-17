import { validator } from '@felte/validator-yup';
import { createForm } from 'felte';
import * as yup from 'yup';

export type CreateDoctor = {
  firstName: string,
  middleName?: string,
  lastName: string,
  title?: string,
}

const defaultDoctorForm: CreateDoctor = {
  firstName: '',
  middleName: '',
  lastName: '',
  title: '',
};

export const createDoctorFormValidator = () => {
  const schema = yup.object({
    firstName: yup.string().required(),
    middleName: yup.string(),
    lastName: yup.string().required(),
    title: yup.string(),
  });

  return createForm<yup.InferType<typeof schema>>({
    initialValues: defaultDoctorForm,
    onSubmit: (form) => console.log('form', form),
    onError: (error) => console.log('error', error),
    extend: [validator({ schema })]
  });
};