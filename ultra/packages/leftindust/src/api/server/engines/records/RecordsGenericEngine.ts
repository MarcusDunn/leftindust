import type { ReadableQuery } from 'svelte-apollo-client';
import { writable, Writable } from 'svelte/store';

import type { Record } from '../../schema/leftindust.schema';
import type { RecordsQueryResult } from '../../requests/queries';

import { client } from '../..';

import basicRecordsQuery from '../../requests/queries/recordsQuery.graphql';

export type MappedRecord<T> = (RecordsQueryResult['getRecords'][0] & {
  parsed: T;
})[];

export type RecordsGenericEngine<T> = {
  request: ReadableQuery<RecordsQueryResult>;
  records: Writable<MappedRecord<T>>;
};

export type RecordsGenericEngineVariables = {
  pid: [Record['patient']['pid']];
};

const RecordsGenericEngine = <T>(variables: RecordsGenericEngineVariables): RecordsGenericEngine<T> => {
  const request = client.query<RecordsQueryResult, RecordsGenericEngineVariables>(basicRecordsQuery, {
    variables,
  });

  const records = writable<MappedRecord<T>>([]);

  request.subscribe(({ data }) => {
    records.set(data?.getRecords.map((record) => ({
      ...record,
      parsed: JSON.parse(record.jsonBlob),
    })) || []);
  });

  return {
    request,
    records,
  };
};

export default RecordsGenericEngine;
