import { account } from '@/features/Account/store';
import { get } from 'svelte/store';
import type { ResolversTypes } from '@/api/server';

const MAX_RECENTS = 5;

export type TimestampedRecents = Record<number, string>;
// timestamp -> value
type TimestampMap = Map<keyof TimestampedRecents, TimestampedRecents[keyof TimestampedRecents]>;
// value -> timestamp
type ValueMap = Map<TimestampedRecents[keyof TimestampedRecents], keyof TimestampedRecents>;

const toTimestampedMap = (recents: TimestampedRecents): TimestampMap => {
  return new Map(Object.entries(recents).map(([time, value]) => [parseInt(time), value]));
};

const toValueMap = (recents: TimestampedRecents): ValueMap => {
  return new Map(Object.entries(recents).map(([time, value]) => [value, parseInt(time)]));
};

export const getTimestampedValues = (recents: TimestampedRecents): string[] => {
  return Object.values(recents);
};

export const sortRecents = <T>(recents: T[], timestampMap: TimestampedRecents, mapToValue: (value: T) => string): T[] => {
  const valueMap = toValueMap(timestampMap);
  return recents.sort((a, b) => (valueMap.get(mapToValue(b)) ?? 0) - (valueMap.get(mapToValue(a)) ?? 0));
};

export const updateRecents = (key: Partial<keyof ResolversTypes>, value: string): void => {
  const data = get(account);
  const recents: TimestampedRecents = data.database.recents[key] ?? {};
  const timestampMap: Map<number, string> = toTimestampedMap(recents);
  const timestamps: number[] = [...timestampMap.keys()];

  // If the value is the most recent value, do nothing
  const newestValue = timestampMap.get(Math.max(...timestamps));
  if (newestValue === value) return;

  const updateTimestamp = toValueMap(recents).get(value);
  // If the value is already in the recents, remove it (it will be added again later with a newer timestamp)
  if(updateTimestamp) timestampMap.delete(updateTimestamp);
  // If the length will be exceeded, remove the oldest value
  else if((timestamps.length + 1) > MAX_RECENTS) timestampMap.delete(Math.min(...timestamps));
  
  // unary (+) cast to a number
  timestampMap.set((+new Date()), value);
  // convert updated map to timestamped record
  const newRecents: TimestampedRecents = [...timestampMap.entries()]
    .reduce((obj, [time, val]) => ({ [time]: val, ...obj }), {});

  account.update((prevAccount) => ({
    ...prevAccount,
    database: {
      ...prevAccount.database,
      recents: {
        ...prevAccount.database.recents,
        [key]: newRecents,
      },
    },
  }));
};
