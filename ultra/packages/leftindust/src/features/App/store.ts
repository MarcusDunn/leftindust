import { writable } from 'svelte/store';
import { AppViews } from '.';

export const appActiveTab = writable<AppViews>(AppViews.Clients);