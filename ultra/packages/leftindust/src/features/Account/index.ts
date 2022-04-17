import type {
  Data,
  Person,
  Document,
  UserQueryResult,
} from '@/api/server';
import type { WidgetType } from '../Widgets';

import { account, signInStatus } from './store';
import { client, realtime, auth } from '@/api/server';
import { ref, set, onValue, off } from "firebase/database";
import { Layout } from '../App';

import type { User } from 'firebase/auth';
import { signOut as firebaseSignOut, setPersistence, browserSessionPersistence, signInWithEmailAndPassword } from 'firebase/auth';

import deepmerge from 'deepmerge';

import { _ } from '@/language';

import getNativeAPI from '@/api/bridge';

import userQuery from '@/api/server/requests/queries/userQuery.graphql';
import { get } from 'svelte/store';
import type { DataType } from '@/api/server/requests';

export type AccountRecentsTemplate = Record<NonNullable<Person>, string[]>;

export type AccountLayoutTemplate = {
  pinned: {
    [K in NonNullable<DataType>]: Record<string, Data[]>;
  };
  grid: {
    [K in WidgetType]: {
      [K in NonNullable<DataType>]: Record<string, Record<string, Data>>;
    }
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

export type Account = UserQueryResult['user'] & {
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
  Patient: [],
  Doctor: [],
  User: [],
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

export const accountLayoutTemplate: AccountLayoutTemplate = ((): AccountLayoutTemplate => {
  const data: { [K in NonNullable<Person | Document>]: Record<string, any> } = {
    Patient: {},
    Doctor: {},
    User: {},
    Event: {},
    Visit: {},
    Record: {},
    FirebaseInfo: {},
    EmergencyContact: {},
    IcdLinearizationEntity: {},
    IcdSimpleEntity: {},
    GraphQLFormTemplate: {},
    AssignedSurvey: {},
  };

  return {
    pinned: { ...data },
    grid: {
      bundle: { ...data },
      cluster: { ...data },
      comparable: { ...data },
      stack: { ...data },
      card: { ...data },
      attachment: { ...data },
      attribute: { ...data },
    },
  };
})();

const accountDatabaseTemplate: AccountDatabaseTemplate = {
  version: 1,
  recents: accountRecentsTemplate,
  settings: accountSettingsTemplate,
  layout: accountLayoutTemplate,
};

export const signIn = (fb: { user: User; database: AccountDatabaseTemplate }): void => {
  const { user, database } = fb;
  const request = client.query<UserQueryResult, { uid: string }>(userQuery, {
    variables: {
      uid: user.uid,
    },
    fetchPolicy: 'no-cache',
  });

  request.subscribe(({ loading, data, error }) => {
    const showSignInError = (message = language('errors.connectionError')) => {
      signInStatus.update((prev) => ({
        ...prev,
        user,
        error: message,
      }));
    };

    if (navigator.onLine) {
      if (!loading) {
        if (data) {
          account.update(() => ({
            ...data.user,
            database: deepmerge(accountDatabaseTemplate, database),
          }));
        } else if (error) {
          showSignInError();
        }
      }
    } else {
      showSignInError(language('errors.offline'));
    }
  });
};

export const signOut = (): void => {
  void client.resetStore().then(() => {
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
  });
};

export const getFirebaseUserDatabaseAndSignIn = (user: User): void => {
  const database = ref(realtime, `users/${user.uid}`);

  onValue(database, (snapshot) => {
    const data: AccountDatabaseTemplate = snapshot.val();
    if (data) {
      // Update settings with latest templates if versions mismatch
      if (data.version < accountDatabaseTemplate.version) {
        set(database, {
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
        off(database);
      }
    } else {
      // Load default settings
      set(database, { ...accountDatabaseTemplate })
        .then(() => {
          console.log('Default user settings have been loaded');
        });
    }
  });

  signInStatus.set({
    signedIn: true,
  });
};

export const authenticateFirebaseUser = (input: {
  email: string;
  password: string;
}): Promise<boolean> =>
  new Promise((resolve, reject) => {
    const { email, password } = input;

    if (email && password) {
      setPersistence(auth, browserSessionPersistence)
        .then(() =>
          signInWithEmailAndPassword(auth, email.trim(), password)
            .then((user) => {
              if (user?.user) {
                // Firebase auth success
                resolve(true);
                getFirebaseUserDatabaseAndSignIn(user.user);
              }
            })
            .catch(() => {
              // Incorrect email or password
              void Dialog.alert({
                message: language('buttons.signIn'),
                detail: language('errors.loginIncorrectFields'),
                buttons: [language('buttons.tryAgain'), language('buttons.ok')],
                defaultId: 0,
              }).then(() => {
                resolve(false);
              });
            }),
        )
        .catch((error) => {
          // Handle Errors here.
          void Dialog.alert({
            message: language('buttons.signIn'),
            detail: `${language('errors.internalError')} ${error.message}`,
            buttons: [language('buttons.ok')],
            defaultId: 0,
          }).then(() => {
            reject(error);
          });
        });
    } else {
      // Missing fields
      void Dialog.alert({
        message: language('buttons.signIn'),
        detail: language('errors.loginEmptyFields'),
        buttons: [language('buttons.ok')],
        defaultId: 0,
      }).then(() => {
        resolve(false);
      });
    }
  });
