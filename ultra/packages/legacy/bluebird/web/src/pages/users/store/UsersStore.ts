import type { Selectable } from '@framework/modules/SelectModule';
import { writable } from 'svelte/store';

export enum UsersTab {
  Pending = 'Pending',
  Registered = 'Registered',
}

export const UsersSelectedTab = writable<UsersTab>(UsersTab.Registered);
export const UsersSelected = writable<Selectable[]>([]);

export const UsersSearchQuery = writable('');
