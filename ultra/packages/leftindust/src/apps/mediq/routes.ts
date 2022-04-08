import type { SvelteComponentDev } from 'svelte/internal';

import DashboardPage from '@/features/Dashboard/DashboardPage.svelte';
import ClientsPage from '@/features/Clients/ClientsPage.svelte';

import TemplatesPage from '@/features/Templates/TemplatesPage.svelte';

import PatientPage from '@/features/Patient/PatientPage.svelte';
import PeoplePage from '@/features/People/PeoplePage.svelte';

import TemplatePreviewPage from '@/features/Templates/TemplatePreviewPage.svelte';
import TemplateWizard from '@/features/Templates/TemplateWizard.svelte';

import MedIQLoginPage from '@/features/Account/MedIQLoginPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';

import LifecycleErrorPage from '@/features/Errors/LifecycleErrorPage.svelte';

export type Path =
  | '/dashboard/'
  | '/clients/'
  | '/patient/:data/'
  | '/people/:data/'
  | '/templates/'
  | '/wizard/template/'
  | '/template/preview/'
  | '/account/login/'
  | '/settings/'
  | '/errors/lifecycle/'
  | '(.*)';

export interface Route {
  path: Path;
  component: typeof SvelteComponentDev;
  routes?: Array<Route>;
  detailRoutes?: Array<Route>;

  [key: string]: unknown;
}

const routes: Route[] = [
  {
    path: '/dashboard/',
    component: DashboardPage,
  },
  {
    path: '/clients/',
    component: ClientsPage,
    master: true,
  },
  {
    path: '/patient/:data/',
    component: PatientPage,
  },
  {
    path: '/people/:data/',
    component: PeoplePage,
  },
  {
    path: '/templates/',
    component: TemplatesPage,
  },
  {
    path: '/wizard/template/',
    component: TemplateWizard,
  },
  {
    path: '/template/preview/',
    component: TemplatePreviewPage,
  },
  {
    path: '/account/login/',
    component: MedIQLoginPage,
  },
  {
    path: '/settings/',
    component: SettingsPage,
  },
  {
    path: '/errors/lifecycle/',
    component: LifecycleErrorPage,
  },
];

export default routes;
