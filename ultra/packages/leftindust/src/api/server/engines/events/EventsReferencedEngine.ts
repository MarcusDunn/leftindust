import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { MappedEvent } from '../../modules/CalendarModule';
import type { BasicEventFragment } from '../../requests/fragments';

import { mapEvents } from '../../modules/CalendarModule';

import type { Selectable } from '../../modules/SelectModule';
import type { DocumentNode } from 'graphql';

import type { DoctorEventsQueryResult, PatientEventsQueryResult } from '../../requests/queries';
import type { Doctor, Patient } from '../../schema/leftindust.schema';

import { writable } from 'svelte/store';

import { client } from '../../server';

import patientEventsQuery from '../../requests/queries/patientEventsQuery.graphql';
import doctorEventsQuery from '../../requests/queries/doctorEventsQuery.graphql';
import { getTrigger } from '../../modules/TriggerModule';

export type EventsReferencedEngineRequest = PatientEventsQueryResult & DoctorEventsQueryResult;

export type EventsReferencedEngine = {
  request: ReadableQuery<EventsReferencedEngineRequest>;
  events: Writable<MappedEvent<BasicEventFragment>[]>;
};

export type EventsReferencedEngineVariables = {
  selectable: Selectable<'Patient' | 'Doctor'>;
};

export type EventsReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
  dids?: [Doctor['did']];
};

const EventsReferencedEngine = (variables: EventsReferencedEngineVariables): EventsReferencedEngine => {
  const { selectable } = variables;

  const events = writable<MappedEvent<BasicEventFragment>[]>([]);

  // Query refers to the GQL request to select depending on the user type
  // The key is the object key ('patients' or 'doctors') that will be used to index the result
  // requestVariables are a mapped version of the request variables
  let query: DocumentNode;
  let key: 'patients' | 'doctors';
  let requestVariables: EventsReferencedEngineRequestVariables;

  switch (selectable.type) {
    case 'Doctor':
      requestVariables = {
        dids: [{ id: selectable.id }],
      };
      key = 'doctors';
      query = doctorEventsQuery;
      break;
    case 'Patient':
      requestVariables = {
        pids: [{ id: selectable.id }],
      };
      key = 'patients';
      query = patientEventsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${selectable.type}" was passed into the 'referenced' events engine`,
      );
  }

  const request = client.query<EventsReferencedEngineRequest, EventsReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    events.set(mapEvents(data?.[key]?.[0]?.events || []));
  });

  getTrigger('refreshEvents').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    events,
  };
};

export default EventsReferencedEngine;
