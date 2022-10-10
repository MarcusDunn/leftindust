import { writable } from 'svelte/store';
import type { DialogParams } from '.';

export const dialogBackdropOpen = writable(false);
export const dialogs = writable<DialogParams[]>([]);