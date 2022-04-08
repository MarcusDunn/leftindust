import type { Writable } from 'svelte/store';
import { writable } from 'svelte/store';

type Triggers = { [key: string]: Writable<boolean>; };

const triggers: Triggers = {};

export const registerTrigger = (key: string): void => {
  if (triggers[key]) return;
  triggers[key] = writable(true);
};

export const getTrigger = (key: string): Writable<boolean> => {
  if (typeof triggers[key] === 'undefined') registerTrigger(key);
  return triggers[key];
};

export const sendTrigger = (key: string): void => {
  if (typeof triggers[key] !== 'undefined') {
    triggers[key].update((trigger) => !trigger);
  } else {
    registerTrigger(key);
  }
};
