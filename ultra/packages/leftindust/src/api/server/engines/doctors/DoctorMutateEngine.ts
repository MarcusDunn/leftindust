import { client } from '../..';
import type { Doctor, DoctorEditInput, DoctorInput } from '../../schema/leftindust.schema';
import doctorMutation from '../../requests/mutations/doctorMutation.graphql';
import doctorEditMutation from '../../requests/mutations/doctorEditMutation.graphql';
import { sendTrigger } from '@/features/Triggers';

const DoctorMutateEngine = async (doctor: DoctorInput | DoctorEditInput, edit = false): Promise<boolean> => {
  const result = await client.mutate<Record<'addDoctor' | 'editDoctor', Doctor>>(edit ? doctorEditMutation : doctorMutation, {
    variables: {
      doctor,
    },
  });

  if (result.data?.addDoctor?.did?.id || result.data?.editDoctor?.did?.id) {
    sendTrigger('refreshDoctors');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default DoctorMutateEngine;
