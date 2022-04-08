import { writable } from 'svelte/store';
import type { History } from '.';

export const history = writable<History>({});
export const currentHistoryIndexes = writable<Record<string, number>>({});

export const forwardHistoryEventCalled = writable(false);