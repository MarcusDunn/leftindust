import { account } from '@/features/Account/store';
import { get } from 'svelte/store';
import type { ResolversTypes } from '@/api/server';

export const updateRecents = (key: Partial<keyof ResolversTypes>, value: string): void => {
  const data = get(account);

  if (!data.database.recents[key]?.includes(value)) {
    const recent = [value].concat(data.database.recents[key]?.slice(0, 2) ?? '');

    account.set({
      ...data,
      database: {
        ...data.database,
        recents: {
          ...data.database.recents,
          [key]: recent,
        },
      },
    });
  }
};