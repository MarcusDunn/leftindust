import { validator } from '@felte/validator-yup';
import type { SubmitContext } from '@felte/common';
import { createForm } from 'felte';
import * as yup from 'yup';

/**
 * Type of the parameters to create a doctor
 */
export type CreateDoctor = {
  firstName: string,
  middleName?: string,
  lastName: string,
  title?: string,
}

/**
 * Default fields for a doctor form
 */
const defaultDoctorForm: CreateDoctor = {
  firstName: '',
  middleName: '',
  lastName: '',
  title: '',
};

/**
 * Creates a doctor form schema validator
 */
const createDoctorFormSchema = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  title: yup.string(),
});

type CreateDoctorFormSchema = yup.InferType<typeof createDoctorFormSchema>;

/**
 * On submit callback for form submit
 */
type CreateDoctorFormOnSubmit<Data extends Record<string, unknown>> = (
  values: Data,
  context: SubmitContext<Data>
) => Promise<unknown> | unknown;

/**
 * Creates a doctor form on submit
 * @param onSubmit callback for submitting the form
 */
export const createDoctorForm = (onSubmit: CreateDoctorFormOnSubmit<CreateDoctorFormSchema>) => {
  return createForm<CreateDoctorFormSchema>({
    initialValues: defaultDoctorForm,
    onSubmit,
    onError: (error) => console.log(error),
    extend: [validator({ schema: createDoctorFormSchema })]
  });
};