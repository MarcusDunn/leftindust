import { ACCOUNT } from '../store';
import { get } from 'svelte/store';
import type { Person } from '../requests';

export const updateRecents = (key: NonNullable<Person>, value: string): void => {
  const account = get(ACCOUNT);

  if (!account.database.recents[key].includes(value)) {
    const recent = [value].concat(account.database.recents[key].slice(0, 2));

    ACCOUNT.set({
      ...account,
      database: {
        ...account.database,
        recents: {
          ...account.database.recents,
          [key]: recent,
        },
      },
    });
  }
};