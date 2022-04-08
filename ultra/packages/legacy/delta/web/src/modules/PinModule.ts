import { ACCOUNT } from '../store';
import type { Selectable } from './SelectModule';

import { get } from 'svelte/store';

export const pin = (pinned: boolean, pinnable: Selectable, reference: Selectable): void => {
  if (reference.type) {
    const account = get(ACCOUNT);
    const store = account.database.layout.pinned[reference.type][reference.id] ??= [];

    if (pinned) {
      account.database.layout.pinned[reference.type][reference.id] = [...store, pinnable];
    } else {
      account.database.layout.pinned[reference.type][reference.id] = store.filter((item) => item.id !== pinnable.id);
    }

    ACCOUNT.set(account);
  }

};

export const pinned = (pinnable: Selectable, reference: Selectable): boolean => {
  const account = get(ACCOUNT);
  return reference.type ? account.database.layout.pinned[reference.type][reference.id]?.some((item) => item.id === pinnable.id) : false;
};
