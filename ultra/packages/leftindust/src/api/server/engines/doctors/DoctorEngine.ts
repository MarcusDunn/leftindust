import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { DoctorQueryResult } from '../../requests/queries';
import type { Doctor } from '../../schema/leftindust.schema';
import { client } from '../..';
import { writable } from 'svelte/store';

import { getTrigger } from '@/features/Triggers';

import doctorQuery from '../../requests/queries/doctorQuery.graphql';

export type DoctorEngine = {
  request: ReadableQuery<DoctorQueryResult>;
  doctor: Writable<DoctorQueryResult['doctors'][0]>;
};

export type DoctorEngineVariables = {
  dids: [Doctor['did']];
};

const DoctorEngine = (variables: DoctorEngineVariables): DoctorEngine => {
  const doctor: DoctorEngine['doctor'] = writable();

  const request = client.query<DoctorQueryResult, DoctorEngineVariables>(doctorQuery, {
    variables,
  });

  request.subscribe(({ data }) => {
    if (data?.doctors?.[0])
      doctor.set(data.doctors[0]);
  });

  getTrigger('refreshDoctors').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    doctor,
  };
};

export default DoctorEngine;
