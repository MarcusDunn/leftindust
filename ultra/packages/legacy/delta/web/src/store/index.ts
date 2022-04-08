import type { Account, SignIn } from '../types';

import { writable } from 'svelte/store';

export const ACCOUNT = writable<Account>();
export const SIGN_IN = writable<SignIn>({
  signedIn: true,
});

export const VIEW_PREVIOUS = writable<string>();
export const WIZARD_ACTIVE = writable<boolean>(false);