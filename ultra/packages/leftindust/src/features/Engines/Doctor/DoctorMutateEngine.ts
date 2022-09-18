import { client, DoctorMutationDocument, type Doctor, type DoctorEditInput, type DoctorInput } from "@/api/server";
import { sendTrigger } from "@/features/Triggers";

const doctorMutateEngine = async (doctor: DoctorInput): Promise<boolean> => {
  const result = await client
    .mutation<Record<'addDoctor', Doctor>>(DoctorMutationDocument, {
      variables: {
        doctor,
      },
    })
    .toPromise();

  if (result.data?.addDoctor?.did?.id) {
    sendTrigger('refreshDoctors');
    return true;
  }
  
  if (result.error) {
    console.error(result.error);
  }

  return false;
};

export default doctorMutateEngine;