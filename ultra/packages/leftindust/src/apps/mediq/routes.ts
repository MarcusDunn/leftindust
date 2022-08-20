import DashboardPage from '@/features/Dashboard/DashboardPage.svelte';
import ClientsPage from '@/features/Clients/ClientsPage.svelte';

import TemplatesPage from '@/features/Templates/TemplatesPage.svelte';
import TemplatePage from '@/features/Template/TemplatePage.svelte';

import PatientPage from '@/features/Patient/PatientPage.svelte';
import DoctorPage from '@/features/Doctor/DoctorPage.svelte';
import PeoplePage from '@/features/People/PeoplePage.svelte';

import TemplatePreviewPage from '@/features/Template/TemplatePreviewPage.svelte';
import TemplateWizard from '@/features/Template/TemplateWizard.svelte';

import MedIQLoginPage from '@/features/Account/MedIQLoginPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';

import LifecycleErrorPage from '@/features/Errors/LifecycleErrorPage.svelte';
import type { Route } from '@/features/View';

export type Path =
  | '/dashboard/'
  | '/clients/'
  | '/patient/:data/'
  | '/doctor/:data/'
  | '/people/:data/'
  | '/templates/'
  | '/template/:data/'
  | '/wizard/template/'
  | '/wizard/template/preview/'
  | '/account/login/'
  | '/settings/'
  | '/errors/lifecycle/'
  | '(.*)';

const routes: Route<Path>[] = [
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
    path: '/doctor/:data/',
    component: DoctorPage,
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
    path: '/template/:data/',
    component: TemplatePage,
  },
  {
    path: '/wizard/template/',
    component: TemplateWizard,
  },
  {
    path: '/wizard/template/preview/',
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
