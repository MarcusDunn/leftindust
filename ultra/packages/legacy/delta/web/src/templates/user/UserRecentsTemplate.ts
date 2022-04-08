import type { Person } from '../../requests';

export type UserRecentsTemplate = Record<NonNullable<Person>, string[]>;

const USER_RECENTS_TEMPLATE: UserRecentsTemplate = {
  Patient: [],
  Doctor: [],
  User: [],
};

export default USER_RECENTS_TEMPLATE;
