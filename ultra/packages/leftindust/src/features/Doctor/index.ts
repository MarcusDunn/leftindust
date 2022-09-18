import { validator } from '@felte/validator-yup';
import type { SubmitContext } from '@felte/common';
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

const createDoctorFormSchema = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  title: yup.string(),
});

type CreateDoctorFormSchema = yup.InferType<typeof createDoctorFormSchema>;
type CreateDoctorFormOnSubmit<Data extends Record<string, unknown>> = (
  values: Data,
  context: SubmitContext<Data>
) => Promise<unknown> | unknown;

export const createDoctorForm = (onSubmit: CreateDoctorFormOnSubmit<CreateDoctorFormSchema>) => {
  return createForm<CreateDoctorFormSchema>({
    initialValues: defaultDoctorForm,
    onSubmit,
    onError: (error) => console.log(error),
    extend: [validator({ schema: createDoctorFormSchema })]
  });
};