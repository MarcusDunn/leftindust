import type { UserQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import type { Writable } from 'svelte/store';

import { getTrigger } from '@/features/Triggers';
import { writable } from 'svelte/store';

import { client } from '../..';

import userQuery from '../../requests/queries/userQuery.graphql';

export type UserEngineVariables = {
  uid: string;
};

export type UserEngine = {
  request: ReadableQuery<UserQueryResult>;
  user: Writable<UserQueryResult['user']>;
};

const UserEngine = (variables: UserEngineVariables): UserEngine => {
  const request = client.query<UserQueryResult, UserEngineVariables>(userQuery, {
    variables,
    fetchPolicy: 'no-cache',
  });

  const user: UserEngine['user'] = writable();

  request.subscribe(({ data }) => {
    if (data?.user) user.set(data.user);
  });

  getTrigger('refreshUsers').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    user,
  };
};

export default UserEngine;
