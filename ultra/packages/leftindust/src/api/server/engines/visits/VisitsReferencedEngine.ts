import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Data } from '../..';
import type { DocumentNode } from 'graphql';

import type { PatientVisitsQueryResult } from '../../requests/queries';
import type { Patient } from '../../schema/leftindust.schema';
import type { BasicVisitFragment } from '../../requests/fragments';
import type { MappedEvent } from '../../modules/CalendarModule';

import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';

import { client } from '../..';

import patientVisitsQuery from '@framework/requests/queries/patientVisitsQuery.graphql';

export type VisitsReferencedEngineRequest = PatientVisitsQueryResult;

export type VisitsReferencedEngine = {
  request: ReadableQuery<VisitsReferencedEngineRequest>;
  visits: Writable<(BasicVisitFragment & {
    event: MappedEvent;
  })[]>;
};

export type VisitsReferencedEngineVariables = {
  selectable: Data;
};

export type VisitsReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const VisitsReferencedEngine = (variables: VisitsReferencedEngineVariables): VisitsReferencedEngine => {
  const { selectable } = variables;

  const visits = writable<(BasicVisitFragment & {
    event: MappedEvent;
  })[]>([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: VisitsReferencedEngineRequestVariables;

  switch (selectable.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: selectable.id }],
      };
      key = 'patients';
      query = patientVisitsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${selectable.type}" was passed into the 'referenced' visits engine`,
      );
  }

  const request = client.query<VisitsReferencedEngineRequest, VisitsReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    visits.set(data?.[key]?.[0]?.visits.map((visit) => ({
      ...visit,
      event: mapEvents([visit.event])[0],
    })) || []);
  });

  return {
    request,
    visits,
  };
};

export default VisitsReferencedEngine;
