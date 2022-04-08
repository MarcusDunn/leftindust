import type { Path } from '@framework/modules/NavigationModule';

import FormsPage from '@/pages/forms/FormsPage.svelte';

import SettingsPage from '@/modals/settings/SettingsPage.svelte';

import LoginPage from '@/modals/user/LoginPage.svelte';

import NotFoundPage from '@/pages/errors/NotFoundPage.svelte';

import SurveyWizard from '@/pages/forms/wizards/SurveyWizard.svelte';


export interface Route {
  path: Path;
  component: any;
  routes?: Array<Route>;
  detailRoutes?: Array<Route>;

  [key: string]: any;
}

const ROUTES: Route[] = [
  {
    path: '/forms/',
    component: FormsPage,
  },
  {
    path: '/wizard/survey/template/',
    component: SurveyWizard,
  },
  {
    path: '/settings/',
    component: SettingsPage,
  },
  {
    path: '/login/',
    component: LoginPage,
  },
  {
    path: '(.*)',
    component: NotFoundPage,
  },
];

export default ROUTES;
