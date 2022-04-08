import { writable } from 'svelte/store';

export const wizardOpen = writable(false);
export const wizardViewPrevious = writable<string>();