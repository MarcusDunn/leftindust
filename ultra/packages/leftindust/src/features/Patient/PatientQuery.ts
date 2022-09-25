import type { FixedLengthArray } from '../../../types';
import { client, PatientQueryDocument, type Patient, type PatientQueryQuery, type PatientQueryQueryVariables } from '@/api/server';

export type PatientQueryEngineParams = {
  pids: FixedLengthArray<[Patient['pid']]>;
};

export const queryPatient = async (queryParams: PatientQueryEngineParams): Promise<PatientQueryQuery[]> => {
  const result = await client
    .query<PatientQueryQuery[], PatientQueryEngineParams>(PatientQueryDocument, queryParams)
    .toPromise();
  
  const data = result.data;
  if(result.error) throw new Error(`Failed to query patient id(s) - ${queryParams.pids.join(', ')}: ${result.error.message}`);
  else if(!data) throw new Error(`Failed to query patient id(s) - ${queryParams.pids.join(', ')}: No data returned`);
  return data;
}