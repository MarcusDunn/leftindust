import type { Route } from '@/features/View';

import LeftindustLoginPage from '@/features/Account/LeftindustLoginPage.svelte';

import IntakePage from '@/features/Intake/IntakePage.svelte';
import RecordWizard from '@/features/Record/RecordWizard.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: IntakePage,
  },
  {
    path: '/wizard/',
    component: RecordWizard,
  },
  {
    path: '/account/login/',
    component: LeftindustLoginPage,
  },
];

export default routes;
