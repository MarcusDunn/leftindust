import type { IcdSearchQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import { client } from '../..';

import icdSearchQuery from '../../requests/queries/icdSearchQuery.graphql';

export type IcdSearchEngineResult = IcdSearchQueryResult;

export type IcdSearchEngine = {
  request: ReadableQuery<IcdSearchEngineResult>;
};

export type IcdSearchEngineVariables = {
  query: string;
};

const IcdSearchEngine = (variables: IcdSearchEngineVariables): IcdSearchEngine => {
  const request = client.query<IcdSearchEngineResult, IcdSearchEngineVariables>(icdSearchQuery, {
    variables,
    fetchPolicy: 'no-cache',
  });

  return {
    request,
  };
};

export default IcdSearchEngine;
