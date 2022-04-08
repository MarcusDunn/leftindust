import type { UserSettings } from './UserSettingsTemplate';
import type { UserLayoutTemplate } from './UserLayoutTemplate';
import type { UserRecentsTemplate } from './UserRecentsTemplate';

import USER_SETTINGS_TEMPLATE from './UserSettingsTemplate';
import USER_LAYOUT_TEMPLATE from './UserLayoutTemplate';
import USER_RECENTS_TEMPLATE from './UserRecentsTemplate';

export type UserDatabaseTemplate = {
  version: number;
  recents: UserRecentsTemplate;
  settings: UserSettings;
  layout: UserLayoutTemplate;
};

const USER_DATABASE_TEMPLATE: UserDatabaseTemplate = {
  version: 1,
  recents: USER_RECENTS_TEMPLATE,
  settings: USER_SETTINGS_TEMPLATE,
  layout: USER_LAYOUT_TEMPLATE,
};

export default USER_DATABASE_TEMPLATE;
