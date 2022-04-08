import type { UsersQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import type { RangeInput } from '../../schemas/leftindust.schema';
import type { Writable } from 'svelte/store';

import { getTrigger } from '../../modules/TriggerModule';
import { writable } from 'svelte/store';

import { client } from '../../server';

import usersQuery from '../../requests/queries/usersQuery.graphql';

export type UsersGenericEngineVariables = {
  range?: RangeInput;
};

export type UsersGenericEngine = {
  request: ReadableQuery<UsersQueryResult>;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
  users: Writable<UsersQueryResult['users']>;
};

const UsersGenericEngine = (
  variables: UsersGenericEngineVariables,
): UsersGenericEngine => {
  const request = client.query<UsersQueryResult, UsersGenericEngineVariables>(
    usersQuery,
    {
      variables,
    },
  );

  const users: UsersGenericEngine['users'] = writable([]);

  const fetchMore: UsersGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: UsersQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: UsersQueryResult }): UsersQueryResult => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new Users with the old Users
          return {
            ...previousResult,
            users: [...previousResult.users, ...fetchMoreResult.users],
          };
        },
      })
      .then(() => {
        console.log('Fetched more users');
      });

    return allowInfinite;
  };

  request.subscribe(({ data }) => {
    if (data?.users) users.set(data.users);
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

export default UsersGenericEngine;
