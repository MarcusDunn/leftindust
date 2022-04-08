import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { VisitQueryResult } from '../../requests/queries';
import type { Visit } from '../../schemas/leftindust.schema';
import type { MappedEvent } from '../../modules/CalendarModule';

import { client } from '../../server';

import visitQuery from '../../requests/queries/visitQuery.graphql';
import { writable } from 'svelte/store';
import { mapEvents } from '../../modules/CalendarModule';
import { getTrigger } from '../../modules/TriggerModule';

export type VisitEngine = {
  request: ReadableQuery<VisitQueryResult>;
  visit: Writable<VisitQueryResult['visits'][0] & {
    event: MappedEvent;
  }>;
};

export type VisitEngineVariables = {
  vids: [Visit['vid']];
};

const VisitEngine = (variables: VisitEngineVariables): VisitEngine => {
  const visit: VisitEngine['visit'] = writable();

  const request = client.query<VisitQueryResult, VisitEngineVariables>(visitQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    if (data?.visits[0])
      visit.set({
        ...data?.visits[0],
        event: mapEvents([data.visits[0]['event']])[0],
      });
  });

  getTrigger('refreshVisits').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    visit,
  };
};

export default VisitEngine;
