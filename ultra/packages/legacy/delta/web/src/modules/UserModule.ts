import type { UserQueryResult } from '../requests/queries';
import type { UserDatabaseTemplate } from '../templates/user/UserDatabaseTemplate';

import { ACCOUNT, SIGN_IN } from '../store';
import { client, realtime } from '../server';

import firebase from 'firebase/app';
import deepmerge from 'deepmerge';

import language from '../languages';

import USER_DATABASE_TEMPLATE from '../templates/user/UserDatabaseTemplate';

import getNativeAPI from '../bridge';

import UserQuery from '../requests/queries/userQuery.graphql';
import { get } from 'svelte/store';

const { Dialog } = getNativeAPI();

export const signIn = (fb: { user: firebase.User; database: UserDatabaseTemplate }): void => {
  const { user, database } = fb;
  const request = client.query<UserQueryResult, { uid: string }>(UserQuery, {
    variables: {
      uid: user.uid,
    },
    fetchPolicy: 'no-cache',
  });

  request.subscribe(({ loading, data, error }) => {
    const showSignInError = (message = language().errors.internalLoginError.text) => {
      SIGN_IN.update((prev) => ({
        ...prev,
        user,
        error: message,
      }));
    };

    if (navigator.onLine) {
      if (!loading) {
        if (data) {
          ACCOUNT.update(() => ({
            ...data.user,
            database: deepmerge(USER_DATABASE_TEMPLATE, database),
          }));
        }
      } else if (error) {
        showSignInError();
      }
    } else {
      showSignInError(language().errors.offline.text);
    }
  });
};

export const signOut = (): void => {
  void client.resetStore().then(() => {
    void firebase
      .auth()
      .signOut()
      .then(() => {
        SIGN_IN.update(() => ({
          signedIn: false,
        }));

        ACCOUNT.update(() => ({
          ...get(ACCOUNT),
          isRegistered: false,
        }));

        // Clear state 100ms after the application has unloaded views: without it, all
        // reactive hooks referencing "ACCOUNT" will trigger and throw errors
        setTimeout(() => {
          // @ts-expect-error
          ACCOUNT.set(undefined);
        }, 100);
      });
  });
};

export const getFirebaseUserDatabaseAndSignIn = (user: firebase.User): void => {
  const database = realtime.ref(`users/${user.uid}`);

  database.on('value', (snapshot) => {
    const data: UserDatabaseTemplate = snapshot.val();
    if (data) {
      // Update settings with latest templates if versions mismatch
      if (data.version < USER_DATABASE_TEMPLATE.version) {
        void database
          .set({
            ...USER_DATABASE_TEMPLATE,
            ...Object.keys(data)
              .filter((key) => key !== 'version')
              .reduce(
                (result, key) => ({
                  ...result,
                  [key]: data[key as keyof UserDatabaseTemplate],
                }),
                {},
              ),
          })
          .then(() => {
            console.log(`User database updated to version ${USER_DATABASE_TEMPLATE.version}`);
          });
      } else {
        // Sign-in user and authenticate with leftindust servers
        signIn({ user, database: data });
        database.off();
      }
    } else {
      // Load default settings
      void database
        .set({
          ...USER_DATABASE_TEMPLATE,
        })
        .then(() => {
          console.log('Default user settings have been loaded');
        });
    }
  });

  SIGN_IN.set({
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
      firebase
        .auth()
        .setPersistence(firebase.auth.Auth.Persistence.LOCAL)
        .then(() =>
          firebase
            .auth()
            .signInWithEmailAndPassword(email.trim(), password)
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
                message: language().user.signIn.title.text,
                detail: language().errors.loginIncorrectFields.text,
                buttons: [language().buttons.tryAgain.text, language().buttons.forgotPassword.text],
                defaultId: 0,
              }).then(() => {
                resolve(false);
              });
            }),
        )
        .catch((error) => {
          // Handle Errors here.
          void Dialog.alert({
            message: language().user.signIn.title.text,
            detail: `${language().errors.internalError.text}: ${error.message}`,
            buttons: [language().buttons.ok.text],
            defaultId: 0,
          }).then(() => {
            reject(error);
          });
        });
    } else {
      // Missing fields
      void Dialog.alert({
        message: language().user.signIn.title.text,
        detail: language().errors.loginEmptyFields.text,
        buttons: [language().buttons.ok.text],
        defaultId: 0,
      }).then(() => {
        resolve(false);
      });
    }
  });
