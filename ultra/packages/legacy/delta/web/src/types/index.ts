import type firebase from 'firebase';
import type { UserQueryResult } from '../requests/queries';
import type { UserDatabaseTemplate } from '../templates/user/UserDatabaseTemplate';

export enum Layouts {
  Bundled = 'bundled',
  Stacked = 'stacked',
}

export type SignIn = {
  user?: firebase.User;
  error?: string;
  signedIn: boolean;
};

export type Account = UserQueryResult['user'] & {
  database: UserDatabaseTemplate;
};
