import { writable } from 'svelte/store';

export enum AppViews {
  Dashboard = 'view-dashboard',
  Calendar = 'view-calendar',
  Clients = 'view-clients',
  Forms = 'view-forms',
  Users = 'view-users',
  Wizard = 'view-wizard',
  Popup = 'view-popup',
}

export enum AppPopups {
  Default = 'popup-default',
  Wizard = 'popup-wizard',
}

export enum AppViewRoutes {
  Dashboard = '/dashboard/',
  Calendar = '/calendar/',
  Clients = '/clients/',
  Forms = '/forms/',
  Users = '/users/',
}

export const APP_ACTIVE_TAB = writable<AppViews>(AppViews.Dashboard);


export const MASTER_DETAIL_BREAKPOINT = 1300;