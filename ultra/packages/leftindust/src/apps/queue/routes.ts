import type { Route } from '@/features/View';

import QueueLoginPage from '@/features/Account/QueueLoginPage.svelte';

import ProjectsPage from '@/features/Apps/AppsPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: ProjectsPage,
  },
  {
    path: '/account/login/',
    component: QueueLoginPage,
  },
  {
    path: '/settings/',
    component: SettingsPage,
  },
];

export default routes;
