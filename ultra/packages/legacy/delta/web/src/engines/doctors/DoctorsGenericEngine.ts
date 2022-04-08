import type { DoctorsQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import type { DoctorExampleInput, RangeInput } from '../../schemas/leftindust.schema';
import type { Writable } from 'svelte/store';

import { getTrigger } from '../../modules/TriggerModule';
import { writable } from 'svelte/store';

import { client } from '../../server';

import doctorsQuery from '../../requests/queries/doctorsQuery.graphql';

export type DoctorsGenericEngineVariables = {
  example?: DoctorExampleInput;
  range?: RangeInput;
};

export type DoctorsGenericEngine = {
  request: ReadableQuery<DoctorsQueryResult>;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
  doctors: Writable<DoctorsQueryResult['doctors']>;
};

export const DoctorsGenericEngineDefaultExample = (query: string): DoctorExampleInput => ({
  strict: false,
  firstName: {
    startsWith: query,
    endsWith: query,
    contains: query,
    eq: query,
    strict: false,
  },
  lastName: {
    startsWith: query,
    endsWith: query,
    contains: query,
    eq: query,
    strict: false,
  },
});

const DoctorsGenericEngine = (
  variables: DoctorsGenericEngineVariables,
): DoctorsGenericEngine => {
  const request = client.query<DoctorsQueryResult, DoctorsGenericEngineVariables>(
    doctorsQuery,
    {
      variables,
      // pollInterval: 2000,
    },
  );

  const doctors: DoctorsGenericEngine['doctors'] = writable([]);

  const fetchMore: DoctorsGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: DoctorsQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: DoctorsQueryResult }): DoctorsQueryResult => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new doctors with the old doctors
          return {
            ...previousResult,
            doctors: [...previousResult.doctors, ...fetchMoreResult.doctors],
          };
        },
      })
      .then(() => {
        console.log('Fetched more doctors');
      });

    return allowInfinite;
  };

  request.subscribe(({ data }) => {
    if (data?.doctors) doctors.set(data.doctors);
  });

  getTrigger('refreshDoctors').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    fetchMore,
    doctors,
  };
};

export default DoctorsGenericEngine;
