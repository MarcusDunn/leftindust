import { account } from '../Account/store';
import type { Data } from '@/api/server';

import { get } from 'svelte/store';

export const pin = (pinned: boolean, pinnable: Data, reference: Data): void => {
  if (reference.type) {
    const acc = get(account);
    const store = acc.database.layout.pinned[reference.type][reference.id] ??= [];

    acc.database.layout.pinned[reference.type][reference.id] = pinned
      ? [...store, pinnable]
      : store.filter((item) => item.id !== pinnable.id);

    account.set(acc);
  }
};

export const pinned = (pinnable: Data, reference: Data): boolean => {
  const acc = get(account);
  return reference.type ? acc.database.layout.pinned[reference.type][reference.id]?.some((item) => item.id === pinnable.id) : false;
};
