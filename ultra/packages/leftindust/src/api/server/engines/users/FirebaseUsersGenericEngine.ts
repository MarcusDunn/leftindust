import type { FirebaseUsersQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import type { RangeInput } from '../../schema/leftindust.schema';
import type { Writable } from 'svelte/store';

import { getTrigger } from '@/features/Triggers';
import { writable } from 'svelte/store';

import { client } from '../..';

import firebaseUsersQuery from '../../requests/queries/firebaseUsersQuery.graphql';

export type FirebaseUsersGenericEngineVariables = {
  range?: RangeInput;
  filterRegistered?: boolean;
};

export type FirebaseUsersGenericEngine = {
  request: ReadableQuery<FirebaseUsersQueryResult>;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
  users: Writable<FirebaseUsersQueryResult['firebaseUsers']>;
};

const FirebaseUsersGenericEngine = (
  variables: FirebaseUsersGenericEngineVariables,
): FirebaseUsersGenericEngine => {
  const request = client.query<FirebaseUsersQueryResult, FirebaseUsersGenericEngineVariables>(
    firebaseUsersQuery,
    {
      variables,
    },
  );

  const users: FirebaseUsersGenericEngine['users'] = writable([]);

  const fetchMore: FirebaseUsersGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: FirebaseUsersQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: FirebaseUsersQueryResult }): FirebaseUsersQueryResult => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new FirebaseUsers with the old FirebaseUsers
          return {
            ...previousResult,
            firebaseUsers: [...previousResult.firebaseUsers, ...fetchMoreResult.firebaseUsers],
          };
        },
      })
      .then(() => {
        console.log('Fetched more users');
      });

    return allowInfinite;
  };

  request.subscribe(({ data }) => {
    if (data?.firebaseUsers) users.set(data.firebaseUsers);
  });

  getTrigger('refreshUsers').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    fetchMore,
    users,
  };
};

export default FirebaseUsersGenericEngine;
