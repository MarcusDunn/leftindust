import type { Route } from '@/features/View';

import LeftindustLoginPage from '@/features/Account/LeftindustLoginPage.svelte';

import ProjectsPage from '@/features/Apps/AppsPage.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: ProjectsPage,
  },
  {
    path: '/account/login/',
    component: LeftindustLoginPage,
  },
];

export default routes;
