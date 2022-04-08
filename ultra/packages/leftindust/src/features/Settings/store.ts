import { writable } from 'svelte/store';
import { SettingsTabs } from '.';

export const selectedSettingsTab = writable<SettingsTabs>(SettingsTabs.Account);