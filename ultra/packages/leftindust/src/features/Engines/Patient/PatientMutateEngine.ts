import { client, PatientEditMutationDocument, type Patient, type PatientInput } from "@/api/server";
import { sendTrigger } from "@/features/Triggers";

const patientMutateEngine = async (patient: PatientInput, edit = false): Promise<boolean> => {
  const result = await client
  .mutation<Record<'addPatient', Patient>>(PatientEditMutationDocument, {
    variables: {
      patient,
    },
  })
  .toPromise();

  if (result.data?.addPatient?.pid?.id) {
    sendTrigger('refreshPatients');
    return true;
  }

  if (result.error) {
    console.error(result.error);
  }

  return false;
};

export default patientMutateEngine;
