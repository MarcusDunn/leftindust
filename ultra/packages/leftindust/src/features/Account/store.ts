import type { Account, SignInStatus } from '.';

import { writable } from 'svelte/store';

export const account = writable<Account>();
export const signInStatus = writable<SignInStatus>({
  signedIn: true,
});