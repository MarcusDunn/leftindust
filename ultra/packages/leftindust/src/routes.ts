import type { Route } from '@/features/View';

import LeftindustLoginPage from './features/Account/LeftindustLoginPage.svelte';

import ProjectsPage from './features/Apps/AppsPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: ProjectsPage,
  },
  {
    path: '/account/login/',
    component: LeftindustLoginPage,
  },
  {
    path: '/settings/',
    component: SettingsPage,
  },
];

export default routes;
