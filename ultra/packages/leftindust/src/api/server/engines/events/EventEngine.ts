import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { EventQueryResult } from '../../requests/queries';
import type { Event } from '../../schema/leftindust.schema';
import type { MappedEvent } from '../../modules/CalendarModule';

import { client } from '../../server';

import eventQuery from '../../requests/queries/eventQuery.graphql';
import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';
import { getTrigger } from '../../modules/TriggerModule';

export type EventEngine = {
  request: ReadableQuery<EventQueryResult>;
  event: Writable<MappedEvent<EventQueryResult['events'][0]>>;
};

export type EventEngineVariables = {
  eids: [Event['eid']];
};

const EventEngine = (variables: EventEngineVariables): EventEngine => {
  const event = writable<MappedEvent<EventQueryResult['events'][0]>>();

  const request = client.query<EventQueryResult, EventEngineVariables>(eventQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    if (data?.events[0])
      event.set(mapEvents<EventQueryResult['events'][0]>([data.events[0]])[0]);
  });

  getTrigger('refreshEvents').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    event,
  };
};

export default EventEngine;
