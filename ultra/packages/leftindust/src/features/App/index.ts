import type { SvelteComponentDev } from 'svelte/internal';
import type { Framework7Parameters } from 'framework7/types';

import Framework7 from 'framework7/lite-bundle';
import Framework7Svelte from 'framework7-svelte';
import { setupI18n } from '@/language';
import { isLoading } from 'svelte-i18n';
import type { Route } from '../View';

export enum AppViews {
  Dashboard = 'view-dashboard',
  Calendar = 'view-calendar',
  Clients = 'view-clients',
  Analytics = 'view-analytics',
  Users = 'view-users',
  Templates = 'view-templates',
  Wizard = 'view-wizard',
  Popup = 'view-popup',
  Hub = 'view-hub'
}

export enum AppRootRoutes {
  Home = '/',
  Dashboard = '/dashboard/',
  Calendar = '/calendar/',
  Clients = '/clients/',
  Templates = '/templates/',
  Analytics = '/template/analytics/',
  Users = '/users/',
  LifeCycleError = '/errors/lifecycle/'
}

export enum AppPopups {
  Default = 'popup-default',
  Error = 'popup-error',
  Wizard = 'popup-wizard',
}

export enum Layout {
  Bundled = 'bundled',
  Stacked = 'stacked',
}

export const initMain = (
  app: typeof SvelteComponentDev,
  f7params: Framework7Parameters,
  routesImporter: () => Promise<{ default: Route[]}>,
) => {
  // https://framework7.io/svelte/init-app
  // @ts-expect-error
  Framework7.use(Framework7Svelte);

  // The zoom property is deprecated
  // @ts-expect-error
  document.getElementsByTagName('body')[0].style.zoom = '90%';

  void setupI18n().then(() => {
    void routesImporter().then((routes) => {
      isLoading.subscribe((loading) => {
        if (!loading) new app({
          target: document.body,
          props: {
            f7params: {
              ...f7params,
              routes: routes.default,
            },
          },
        });
      });
    });
  });


};