import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Data } from '../..';
import type { DocumentNode } from 'graphql';

import type { DoctorPatientsQueryResult } from '../../requests/queries';
import type { Doctor } from '../../schema/leftindust.schema';

import { client } from '../..';
import { getTrigger } from '@/features/Triggers';

import doctorPatientsQuery from '@framework/requests/queries/doctorPatientsQuery.graphql';
import { writable } from 'svelte/store';

export type PatientsReferencedEngineRequest = DoctorPatientsQueryResult;

export type PatientsReferencedEngine = {
  request: ReadableQuery<PatientsReferencedEngineRequest>;
  patients: Writable<PatientsReferencedEngineRequest['doctors'][0]['patients']>;
};

export type PatientsReferencedEngineVariables = {
  data: Data;
};

export type PatientsReferencedEngineRequestVariables = {
  dids?: Doctor['did'][];
};

const PatientsReferencedEngine = (variables: PatientsReferencedEngineVariables): PatientsReferencedEngine => {
  const { data } = variables;

  const patients = writable<PatientsReferencedEngineRequest['doctors'][0]['patients']>([]);

  let query: DocumentNode;
  let key: 'doctors';
  let requestVariables: PatientsReferencedEngineRequestVariables;

  switch (data.type) {
    case 'Doctor':
      requestVariables = {
        dids: [{ id: data.id }],
      };
      key = 'doctors';
      query = doctorPatientsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${data.type}" was passed into the 'referenced' patients engine`,
      );
  }

  const request = client.query<PatientsReferencedEngineRequest, PatientsReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    patients.set(data?.[key]?.[0]?.patients || []);
  });

  getTrigger('refreshPatients').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    patients,
  };
};

export default PatientsReferencedEngine;
