import type { Patient, PatientInput } from '../../schema/leftindust.schema';
import { client } from '@/api/server';
import patientMutation from '../../requests/mutations/patientMutation.graphql';
import patientEditMutation from '../../requests/mutations/patientEditMutation.graphql';
import { sendTrigger } from '@/features/Triggers';

const PatientMutateEngine = async (patient: PatientInput, edit = false): Promise<boolean> => {
  const result = await client.mutate<Record<'addPatient' | 'updatePatient', Patient>>(edit
    ? patientEditMutation : patientMutation, {
    variables: {
      patient,
    },
  });

  if (result.data?.addPatient?.pid?.id || result.data?.updatePatient?.pid?.id) {
    sendTrigger('refreshPatients');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default PatientMutateEngine;
