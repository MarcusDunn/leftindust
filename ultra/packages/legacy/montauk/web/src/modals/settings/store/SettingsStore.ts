import { writable } from 'svelte/store';

export enum SettingsTabs {
  Account = 'account',
  Options = 'options',
  Theme = 'theme',
}

export const SelectedSettingsTab = writable<SettingsTabs>(SettingsTabs.Account);
