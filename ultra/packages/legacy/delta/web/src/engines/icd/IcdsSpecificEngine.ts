import type { IcdQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import { client } from '../../server';
import { writable } from 'svelte/store';

import icdQuery from '../../requests/queries/icdQuery.graphql';
import type { BasicIcdFragment } from '../../requests/fragments';

export type IcdsSpecificEngineResult = IcdQueryResult;

export type IcdsSpecificEngine = {
  request: ReadableQuery<IcdsSpecificEngineResult>;
  icds: Writable<BasicIcdFragment[]>;
};

export type IcdsSpecificEngineVariables = {
  icdCode: string;
};

const IcdsSpecificEngine = (variables: IcdsSpecificEngineVariables): IcdsSpecificEngine => {
  const icds = writable<BasicIcdFragment[]>([]);
  const request = client.query<IcdsSpecificEngineResult, IcdsSpecificEngineVariables>(icdQuery, {
    variables,
    fetchPolicy: 'no-cache',
  });

  request.subscribe(({ data }) => {
    if (data?.icd)
      icds.set([data.icd] ?? []);
  });

  return {
    request,
    icds,
  };
};

export default IcdsSpecificEngine;
