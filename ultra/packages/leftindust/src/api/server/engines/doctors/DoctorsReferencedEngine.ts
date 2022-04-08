import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Data } from '../..';
import type { DocumentNode } from 'graphql';

import type { PatientDoctorsQueryResult } from '../../requests/queries';
import type { Patient } from '../../schema/leftindust.schema';

import { client } from '../..';
import { getTrigger } from '@/features/Triggers';

import patientDoctorsQuery from '@framework/requests/queries/patientDoctorsQuery.graphql';
import { writable } from 'svelte/store';

export type DoctorsReferencedEngineRequest = PatientDoctorsQueryResult;

export type DoctorsReferencedEngine = {
  request: ReadableQuery<DoctorsReferencedEngineRequest>;
  doctors: Writable<DoctorsReferencedEngineRequest['patients'][0]['doctors']>;
};

export type DoctorsReferencedEngineVariables = {
  data: Data;
};

export type DoctorsReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const DoctorsReferencedEngine = (variables: DoctorsReferencedEngineVariables): DoctorsReferencedEngine => {
  const { data } = variables;

  const doctors = writable<DoctorsReferencedEngineRequest['patients'][0]['doctors']>([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: DoctorsReferencedEngineRequestVariables;

  switch (data.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: data.id }],
      };
      key = 'patients';
      query = patientDoctorsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${data.type}" was passed into the 'referenced' doctors engine`,
      );
  }

  const request = client.query<DoctorsReferencedEngineRequest, DoctorsReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    doctors.set(data?.[key]?.[0]?.doctors || []);
  });

  getTrigger('refreshDoctors').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    doctors,
  };
};

export default DoctorsReferencedEngine;
