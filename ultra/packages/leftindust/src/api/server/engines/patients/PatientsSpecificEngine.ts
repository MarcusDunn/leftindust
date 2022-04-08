import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { PatientsQueryResult } from '../../requests/queries';
import type { Patient } from '../../schema/leftindust.schema';

import { writable } from 'svelte/store';

import { client } from '../..';
import { getTrigger } from '@/features/Triggers';

import patientsQuery from '../../requests/queries/patientsQuery.graphql';

export type PatientsSpecificEngine = {
  request: ReadableQuery<PatientsQueryResult>;
  patients: Writable<PatientsQueryResult['patients']>;
};

export type PatientsSpecificEngineVariables = {
  pids: Patient['pid'][];
};

const PatientsSpecificEngine = (variables: PatientsSpecificEngineVariables): PatientsSpecificEngine => {
  const patients = writable<PatientsQueryResult['patients']>([]);

  const request = client.query<PatientsQueryResult, PatientsSpecificEngineVariables>(patientsQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    patients.set(data?.patients ?? []);
  });

  getTrigger('refreshPatients').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    patients,
  };
};

export default PatientsSpecificEngine;
