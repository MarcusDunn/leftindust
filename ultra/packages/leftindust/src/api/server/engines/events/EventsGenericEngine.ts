import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { MappedEvent } from '../../modules/CalendarModule';

import type { EventsQueryResult } from '../../requests/queries';
import type { TimeRangeInput } from '../../schema/leftindust.schema';
import type { BasicEventFragment } from '../../requests/fragments';

import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';

import { client } from '../../server';

import eventsQuery from '../../requests/queries/eventsQuery.graphql';
import { getTrigger } from '../../modules/TriggerModule';
import type { WatchQueryOptions } from '@apollo/client';


export type EventsGenericEngine = {
  request: ReadableQuery<EventsQueryResult>;
  events: Writable<MappedEvent<BasicEventFragment>[]>;
};

export type EventsSpecificEngineVariables = {
  range: TimeRangeInput;
};

const EventsGenericEngine = (
  variables: EventsSpecificEngineVariables,
  options?: Partial<WatchQueryOptions<EventsSpecificEngineVariables>>,
): EventsGenericEngine => {
  const events = writable<MappedEvent<BasicEventFragment>[]>([]);

  const request = client.query<EventsQueryResult, EventsSpecificEngineVariables>(eventsQuery, {
    ...options,
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

export default EventsGenericEngine;
