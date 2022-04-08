import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import type { IntrospectionQueryResult } from '../../requests/queries';

import { writable } from 'svelte/store';
import { client } from '../..';

import introspectionQuery from '../../requests/queries/introspectionQuery.graphql';

export type IntrospectionEngine = {
  request: ReadableQuery<IntrospectionQueryResult>;
  result: Writable<IntrospectionQueryResult['__type']['enumValues'] | undefined>;
}

export type IntrospectionEngineVariables = {
  name: 'AddressType' | 'Country' | 'Ethnicity';
}

const IntrospectionEngine = (variables: IntrospectionEngineVariables) => {
  const result: IntrospectionEngine['result'] = writable();

  const request = client.query<IntrospectionQueryResult, IntrospectionEngineVariables>(introspectionQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    if (data?.__type?.enumValues)
      result.set(data?.__type?.enumValues);
  });

  return {
    request,
    result,
  };
};

export default IntrospectionEngine;