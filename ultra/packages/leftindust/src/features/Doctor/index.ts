import { validator } from '@felte/validator-yup';
import type { SubmitContext } from '@felte/common';
import { createForm } from 'felte';
import * as yup from 'yup';
import getNativeAPI from '@/api/bridge';
import { get } from 'svelte/store';
import { _ } from '@/language';

const { Dialog } = getNativeAPI();

const language = get(_);

/**
 * Creates a doctor form schema validator
 */
const createDoctorFormSchema = yup.object({
  firstName: yup.string().required(),
  middleName: yup.string(),
  lastName: yup.string().required(),
  title: yup.string(),
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
};

/**
 * Creates a doctor form on submit
 */
export const createDoctorForm = (did?: string) => createForm<DoctorFormSchema>({
  initialValues: defaultDoctorForm,
  onSubmit: (doctor) => {
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
        console.log(doctor);
        // await addDoctor(patient);
      }
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
  extend: [validator({ schema: createDoctorFormSchema })],
});