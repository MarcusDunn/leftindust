import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { MappedEvent } from '../../modules/CalendarModule';

import type { VisitsQueryResult } from '../../requests/queries';
import type { BasicVisitFragment } from '../../requests/fragments';
import type { Visit } from '../../schema/leftindust.schema';

import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';

import { client } from '../../server';

import basicVisitsQuery from '../../requests/queries/visitsQuery.graphql';
import { getTrigger } from '../../modules/TriggerModule';


export type VisitsSpecificEngine = {
  request: ReadableQuery<VisitsQueryResult>;
  visits: Writable<(BasicVisitFragment & {
    event: MappedEvent;
  })[]>;
};

export type VisitsSpecificEngineVariables = {
  vids: [Visit['vid']];
};

const VisitsSpecificEngine = (variables: VisitsSpecificEngineVariables): VisitsSpecificEngine => {
  const visits = writable<(BasicVisitFragment & {
    event: MappedEvent;
  })[]>([]);

  const request = client.query<VisitsQueryResult, VisitsSpecificEngineVariables>(basicVisitsQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    visits.set(data?.visits.map((visit) => ({
      ...visit,
      event: mapEvents([visit.event])[0],
    })) ?? []);
  });

  getTrigger('refreshEvents').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    visits,
  };
};

export default VisitsSpecificEngine;
