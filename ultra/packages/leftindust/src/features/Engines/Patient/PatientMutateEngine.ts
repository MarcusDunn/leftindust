import { client, PatientEditMutationDocument, PatientMutationDocument, type Patient, type PatientInput } from "@/api/server";
import { sendTrigger } from "@/features/Triggers";

const patientMutateEngine = async (patient: PatientInput, edit = false): Promise<boolean> => {
  const result = await client
  .mutation<Record<'addPatient' | 'editPatient', Patient>>(edit ? PatientEditMutationDocument : PatientMutationDocument, {
    variables: {
      patient,
    },
  })
  .toPromise();

  if (result.data?.addPatient?.pid?.id || result.data?.editPatient?.pid?.id) {
    sendTrigger('refreshPatients');
    return true;
  }

  if (result.error) {
    console.error(result.error);
  }

  return false;
};

export default patientMutateEngine;
