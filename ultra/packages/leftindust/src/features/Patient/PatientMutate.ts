import { client, PatientEditMutationDocument, PatientMutationDocument } from '@/api/server';
import type {
  PatientInput,
  PatientEditInput,
  PatientMutationMutation,
  PatientEditMutationMutation,
} from '@/api/server';

/**
 * Adds a patient
 * @param patient The patient to add
 * @returns The added patient
 */
export const mutateAddPatient = async (patient: PatientInput): Promise<PatientMutationMutation> => {
  const result = await client.mutation(PatientMutationDocument, { patient }).toPromise();
  
  const data = result.data;
  const userName = `${patient.nameInfo.firstName} ${patient.nameInfo.lastName}`;
  if(result.error) throw new Error(`Failed to add patient ${userName}: ${result.error.message}`);
  else if(!data) throw new Error(`Failed to add patient ${userName}: No data returned`);
  return data;
}

/**
 * Edits a patient
 * @param patient The patient to edit
 * @returns The edited patient
 */
export const mutateEditPatient = async (patient: PatientEditInput): Promise<PatientEditMutationMutation> => {
  const result = await client.mutation(PatientEditMutationDocument, { patient }).toPromise();

  const data = result.data;
  if(result.error) throw new Error(`Failed to edit patient id ${patient.pid}: ${result.error.message}`);
  else if(!data) throw new Error(`Failed to edit patient id ${patient.pid}: No data returned`);
  return data;
}