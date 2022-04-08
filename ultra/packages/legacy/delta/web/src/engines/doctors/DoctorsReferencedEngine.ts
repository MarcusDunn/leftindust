import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Selectable } from '../../modules/SelectModule';
import type { DocumentNode } from 'graphql';

import type { PatientDoctorsQueryResult } from '../../requests/queries';
import type { Patient } from '../../schemas/leftindust.schema';

import { client } from '../../server';
import { getTrigger } from '../../modules/TriggerModule';

import patientDoctorsQuery from '@framework/requests/queries/patientDoctorsQuery.graphql';
import { writable } from 'svelte/store';

export type DoctorsReferencedEngineRequest = PatientDoctorsQueryResult;

export type DoctorsReferencedEngine = {
  request: ReadableQuery<DoctorsReferencedEngineRequest>;
  doctors: Writable<DoctorsReferencedEngineRequest['patients'][0]['doctors']>;
};

export type DoctorsReferencedEngineVariables = {
  selectable: Selectable;
};

export type DoctorsReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const DoctorsReferencedEngine = (variables: DoctorsReferencedEngineVariables): DoctorsReferencedEngine => {
  const { selectable } = variables;

  const doctors = writable<DoctorsReferencedEngineRequest['patients'][0]['doctors']>([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: DoctorsReferencedEngineRequestVariables;

  switch (selectable.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: selectable.id }],
      };
      key = 'patients';
      query = patientDoctorsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${selectable.type}" was passed into the 'referenced' doctors engine`,
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
