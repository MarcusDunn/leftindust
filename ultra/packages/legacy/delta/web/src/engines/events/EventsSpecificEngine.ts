import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { MappedEvent } from '../../modules/CalendarModule';

import type { EventsQueryResult } from '../../requests/queries';
import type { BasicEventFragment } from '../../requests/fragments';
import type { Event } from '../../schemas/leftindust.schema';

import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';

import { client } from '../../server';

import eventsQuery from '../../requests/queries/eventsQuery.graphql';
import { getTrigger } from '../../modules/TriggerModule';


export type EventsSpecificEngine = {
  request: ReadableQuery<EventsQueryResult>;
  events: Writable<MappedEvent<BasicEventFragment>[]>;
};

export type EventsSpecificEngineVariables = {
  events: [Event['eid']];
};

const EventsSpecificEngine = (variables: EventsSpecificEngineVariables): EventsSpecificEngine => {
  const events = writable<MappedEvent<BasicEventFragment>[]>([]);

  const request = client.query<EventsQueryResult, EventsSpecificEngineVariables>(eventsQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    events.set(mapEvents(data?.events || []));
  });

  getTrigger('refreshEvents').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    events,
  };
};

export default EventsSpecificEngine;
