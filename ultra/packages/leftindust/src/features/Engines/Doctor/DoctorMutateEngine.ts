import { client, DoctorEditMutationDocument, DoctorMutationDocument, type Doctor, type DoctorEditInput, type DoctorInput } from '@/api/server';
import { sendTrigger } from '@/features/Triggers';

const doctorMutateEngine = async (doctor: DoctorInput | DoctorEditInput, edit = false): Promise<boolean> => {
  const result = await client
    .mutation<Record<'addDoctor' | 'editDoctor', Doctor>>(edit ? DoctorEditMutationDocument : DoctorMutationDocument, {
      variables: {
        doctor,
      },
    })
    .toPromise();

  if (result.data?.addDoctor?.did?.id || result.data?.editDoctor?.did?.id) {
    sendTrigger('refreshDoctors');
    return true;
  } else {
    console.error(result.error);
    return false;
  }
};

export default doctorMutateEngine;