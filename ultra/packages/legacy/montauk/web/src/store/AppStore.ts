import { writable } from 'svelte/store';

export enum AppViews {
  Forms = 'view-forms',
  Wizard = 'view-wizard',
  Popup = 'view-popup',
}

export enum AppPopups {
  Default = 'popup-default',
}

export enum AppViewRoutes {
  Forms = '/forms/',
}

export const APP_ACTIVE_TAB = writable<AppViews>(AppViews.Forms);
