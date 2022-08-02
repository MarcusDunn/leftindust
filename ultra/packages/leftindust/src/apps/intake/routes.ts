import type { Route } from '@/features/View';

import LeftindustLoginPage from '@/features/Account/LeftindustLoginPage.svelte';

import IntakePage from '@/features/Intake/IntakePage.svelte';
import IntakeWizard from '@/features/Intake/IntakeWizard.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: IntakePage,
  },
  {
    path: '/wizard/',
    component: IntakeWizard,
  },
  {
    path: '/account/login/',
    component: LeftindustLoginPage,
  },
];

export default routes;
