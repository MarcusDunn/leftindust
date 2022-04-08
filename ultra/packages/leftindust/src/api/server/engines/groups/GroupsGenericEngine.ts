import type { GroupsQueryResult } from '../../requests/queries';
import type { ReadableQuery } from 'svelte-apollo-client';

import type { RangeInput } from '../../schema/leftindust.schema';
import type { Writable } from 'svelte/store';

import { getTrigger } from '@/features/Triggers';
import { writable } from 'svelte/store';

import { client } from '../..';

import groupsQuery from '../../requests/queries/groupsQuery.graphql';

export type GroupsGenericEngineVariables = {
  range?: RangeInput;
};

export type GroupsGenericEngine = {
  request: ReadableQuery<GroupsQueryResult>;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
  groups: Writable<GroupsQueryResult['groups']>;
};

const GroupsGenericEngine = (
  variables: GroupsGenericEngineVariables,
): GroupsGenericEngine => {
  const request = client.query<GroupsQueryResult, GroupsGenericEngineVariables>(
    groupsQuery,
    {
      variables,
    },
  );

  const groups: GroupsGenericEngine['groups'] = writable([]);

  const fetchMore: GroupsGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: GroupsQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: GroupsQueryResult }): GroupsQueryResult => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new Groups with the old Groups
          return {
            ...previousResult,
            groups: [...previousResult.groups, ...fetchMoreResult.groups],
          };
        },
      })
      .then(() => {
        console.log('Fetched more users');
      });

    return allowInfinite;
  };

  request.subscribe(({ data }) => {
    if (data?.groups) groups.set(data.groups);
  });

  getTrigger('refreshUsers').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    fetchMore,
    groups,
  };
};

export default GroupsGenericEngine;
