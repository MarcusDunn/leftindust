import type { ReadableQuery } from 'svelte-apollo-client';

import type { PatientQueryResult } from '../../requests/queries';
import type { Patient } from '../../schemas/leftindust.schema';
import type { FixedLengthArray } from '../../types/helpers';
import type { Writable } from 'svelte/store';

import { client } from '../../server';
import { getTrigger } from '../../modules/TriggerModule';

import patientQuery from '../../requests/queries/patientQuery.graphql';
import { writable } from 'svelte/store';

export type PatientEngine = {
  request: ReadableQuery<PatientQueryResult>;
  patient: Writable<PatientQueryResult['patients'][0]>;
};

export type PatientEngineVariables = {
  pids: FixedLengthArray<[Patient['pid']]>;
};

const PatientEngine = (variables: PatientEngineVariables): PatientEngine => {
  const patient: PatientEngine['patient'] = writable();

  const request = client.query<PatientQueryResult, PatientEngineVariables>(patientQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    if (data?.patients?.[0])
      patient.set(data.patients[0]);
  });

  getTrigger('refreshPatients').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    patient,
  };
};

export default PatientEngine;
