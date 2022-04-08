import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { DoctorsQueryResult } from '../../requests/queries';
import type { Doctor } from '../../schemas/leftindust.schema';

import { writable } from 'svelte/store';

import { client } from '../../server';
import { getTrigger } from '../../modules/TriggerModule';

import doctorsQuery from '../../requests/queries/doctorsQuery.graphql';

export type DoctorsSpecificEngine = {
  request: ReadableQuery<DoctorsQueryResult>;
  doctors: Writable<DoctorsQueryResult['doctors']>;
};

export type DoctorsSpecificEngineVariables = {
  dids: Doctor['did'][];
};

const DoctorsSpecificEngine = (variables: DoctorsSpecificEngineVariables): DoctorsSpecificEngine => {
  const doctors = writable<DoctorsQueryResult['doctors']>([]);

  const request = client.query<DoctorsQueryResult, DoctorsSpecificEngineVariables>(doctorsQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    doctors.set(data?.doctors ?? []);
  });

  getTrigger('refreshDoctors').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    doctors,
  };
};

export default DoctorsSpecificEngine;
