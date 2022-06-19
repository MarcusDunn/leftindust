import {
  client,
  database,
  resolversArray,
  resolversRecord,
  UserQueryDocument,
  type Data,
  type ResolversTypes,
  type UserFragment,
} from '@/api/server';
import type { WidgetType } from '../Widgets';

import { get } from 'svelte/store';
import { account, signInStatus } from './store';
import { auth } from '@/api/server';
import {
  ref,
  set,
  onValue,
  off,
} from 'firebase/database';
import { Layout } from '../App';

import type { User } from 'firebase/auth';
import {
  signOut as firebaseSignOut,
  setPersistence,
  browserSessionPersistence,
  signInWithEmailAndPassword,
} from 'firebase/auth';

import * as yup from 'yup';
import { createForm } from 'felte';
import { validator } from '@felte/validator-yup';

import deepmerge from 'deepmerge';

import { _ } from '@/language';

import getNativeAPI from '@/api/bridge';
import type { TimestampedRecents } from '../Recents';

export type AccountRecentsTemplate = Record<keyof ResolversTypes, TimestampedRecents>;

export type AccountLayoutTemplate = {
  pinned: {
    [K in keyof ResolversTypes]: Record<string, Data[]>;
  };
  grid: {
    [K in WidgetType]: {
      [K in keyof ResolversTypes]: Record<string, Record<string, Data>>;
    };
  }
};

export interface AccountSettings {
  setup: boolean;
  options: {
    layout: Layout;
    dateFormat: string;
    showToolbarLabels: boolean;
    views: {
      clients: {
        patientContactInformationExpanded: boolean;
      };
    };
  };
  theme: {
    transparency: boolean;
    mode: 'auto' | 'dark' | 'light';
  };
}

export type AccountDatabaseTemplate = {
  version: number;
  recents: AccountRecentsTemplate;
  settings: AccountSettings;
  layout: AccountLayoutTemplate;
};

export type Account = UserFragment & {
  database: AccountDatabaseTemplate;
};

export type SignInStatus = {
  user?: User;
  error?: string;
  signedIn: boolean;
};

const { Dialog } = getNativeAPI();
const language = get(_);

export const accountRecentsTemplate: AccountRecentsTemplate = {
  ...resolversArray,
};

export const accountSettingsTemplate: Readonly<AccountSettings> = {
  setup: false,
  options: {
    layout: Layout.Bundled,
    dateFormat: 'dd/mm/yyyy',
    showToolbarLabels: true,
    views: {
      clients: {
        patientContactInformationExpanded: false,
      },
    },
  },
  theme: {
    transparency: true,
    mode: 'auto',
  },
};

export const accountLayoutTemplate: AccountLayoutTemplate ={
  pinned: { ...resolversRecord },
  grid: {
    bundle: { ...resolversRecord },
    cluster: { ...resolversRecord },
    comparable: { ...resolversRecord },
    stack: { ...resolversRecord },
    card: { ...resolversRecord },
    attachment: { ...resolversRecord },
    attribute: { ...resolversRecord },
  },
};

const accountDatabaseTemplate: AccountDatabaseTemplate = {
  version: 1,
  recents: accountRecentsTemplate,
  settings: accountSettingsTemplate,
  layout: accountLayoutTemplate,
};

export const signIn = (fb: { user: User; database: AccountDatabaseTemplate }): void => {
  const { user, database } = fb;

  client.query(UserQueryDocument, {
    uid: user.uid,
  })
    .toPromise()
    .then(({ data, error }) => {
      const showSignInError = (message = language('errors.connectionError')) => {
        signInStatus.update((prev) => ({
          ...prev,
          user,
          error: message,
        }));
      };

      if (navigator.onLine) {
        if (data) {
          account.update(() => ({
            ...data.user,
            database: deepmerge(accountDatabaseTemplate, database),
          }));
        } else if (error) {
          showSignInError();
        }
      } else {
        showSignInError(language('errors.offline'));
      }
    })
    .catch(() => console.log('Error'));
};

export const signOut = (): void => {
  void firebaseSignOut(auth)
    .then(() => {
      signInStatus.update(() => ({
        signedIn: false,
      }));

      account.update(() => ({
        ...get(account),
        isRegistered: false,
      }));

      // Clear state 100ms after the application has unloaded views: without it, all
      // reactive hooks referencing "account" will trigger and throw errors
      setTimeout(() => {
        // @ts-expect-error
        account.set(undefined);
      }, 100);
    });
};

export const getFirebaseUserDatabaseAndSignIn = (user: User): void => {
  const realtime = ref(database, `users/${user.uid}`);

  onValue(realtime, (snapshot) => {
    const data: AccountDatabaseTemplate = snapshot.val();
    if (data) {
      // Update settings with latest templates if versions mismatch
      if (data.version < accountDatabaseTemplate.version) {
        void set(realtime, {
          ...accountDatabaseTemplate,
          ...Object.keys(data)
            .filter((key) => key !== 'version')
            .reduce(
              (result, key) => ({
                ...result,
                [key]: data[key as keyof AccountDatabaseTemplate],
              }),
              {},
            ),
        })
          .then(() => {
            console.log(`User database updated to version ${accountDatabaseTemplate.version}`);
          });
      } else {
        // Sign-in user and authenticate with leftindust servers
        signIn({ user, database: data });
        off(realtime);
      }
    } else {
      // Load default settings
      void set(realtime, { ...accountDatabaseTemplate })
        .then(() => {
          console.log('Default user settings have been loaded');
        });
    }
  });

  signInStatus.set({
    signedIn: true,
  });
};

export const loginForm = () => {
  const schema = yup.object({
    email: yup.string().required(),
    password: yup.string().required(),
  });

  return createForm<yup.InferType<typeof schema>>({
    onSubmit: async ({ email, password }) => {
      try {
        await setPersistence(auth, browserSessionPersistence);
      } catch (error) {
        throw new Error(`${language('errors.internalError')}: ${error}`);
      }

      try {
        const user = await signInWithEmailAndPassword(auth, email.trim(), password);
        getFirebaseUserDatabaseAndSignIn(user.user);
      } catch (_) {
        throw new Error(language('errors.loginIncorrectFields'));
      }
    },
    onError: (error) => {
      void Dialog.alert({
        message: language('generics.signIn'),
        detail: (error as Error).message,
        buttons: [language('generics.ok')],
        defaultId: 0,
      });
    },
    extend: [
      validator({ schema }),
    ],
  });
};