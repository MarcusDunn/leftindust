import type { SmartSelect } from 'framework7/types';

import type { PatientsQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type {
  PatientExampleInput,
  RangeInput,
  SortableField,
} from '../../schemas/leftindust.schema';

import { client } from '../../server';
import { getTrigger } from '../../modules/TriggerModule';

import patientsQuery from '../../requests/queries/patientsQuery.graphql';
import { writable } from 'svelte/store';

export type PatientsGenericEngineVariables = {
  sortBy?: SortableField;
  example?: PatientExampleInput;
  range?: RangeInput;
};

export type PatientsGenericEngine = {
  request: ReadableQuery<PatientsQueryResult>;
  patients: Writable<PatientsQueryResult['patients']>;
  sortBy: (smartSelect: SmartSelect.SmartSelect) => void;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
};

export const PatientsGenericEngineDefaultExample = (query: string): PatientExampleInput => ({
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
  insuranceNumber: {
    eq: query,
    strict: true,
  },
});

const PatientsGenericEngine = (
  variables: PatientsGenericEngineVariables,
  fetchPolicy?: 'no-cache',
): PatientsGenericEngine => {
  const request = client.query<PatientsQueryResult, PatientsGenericEngineVariables>(
    patientsQuery,
    {
      variables,
      // pollInterval: 2000,
      fetchPolicy,
    },
  );

  const patients: PatientsGenericEngine['patients'] = writable([]);

  const sortBy: PatientsGenericEngine['sortBy'] = (smartSelect) => {
    void request
      .refetch({
        ...variables,
        sortBy: <SortableField>smartSelect.getValue(),
      })
      .then(() => {
        console.log('Patients sorted successfully');
      });
  };

  const fetchMore: PatientsGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: PatientsQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: PatientsQueryResult }) => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new patients with the old patients
          return {
            ...previousResult,
            patients: [...previousResult.patients, ...fetchMoreResult.patients],
          };
        },
      })
      .then(() => {
        console.log('Fetched more patients');
      });

    return allowInfinite;
  };

  request.subscribe(({ data }) => {
    if (data?.patients) patients.set(data.patients);
  });

  getTrigger('refreshPatients').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    patients,
    sortBy,
    fetchMore,
  };
};

export default PatientsGenericEngine;
