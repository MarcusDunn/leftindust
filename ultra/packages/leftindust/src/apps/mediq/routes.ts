import type { Route } from '@/features/View';

import DashboardPage from '@/features/Dashboard/DashboardPage.svelte';
import CalendarPage from '@/features/Calendar/CalendarPage.svelte';
import ClientsPage from '@/features/Clients/ClientsPage.svelte';

import TemplatesPage from '@/features/Templates/TemplatesPage.svelte';
import TemplatePage from '@/features/Template/TemplatePage.svelte';

import SetupPage from '@/features/Setup/SetupPage.svelte';

import PatientPage from '@/features/Patient/PatientPage.svelte';
import DoctorPage from '@/features/Doctor/DoctorPage.svelte';
import TemplateAssignPage from '@/features/Assign/TemplateAssignPage.svelte';

import TemplateWizard from '@/features/Template/TemplateWizard.svelte';
import RecordWizard from '@/features/Record/RecordWizard.svelte';

import MedIQLoginPage from '@/features/Account/MedIQLoginPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';

import LifecycleErrorPage from '@/features/Errors/LifecycleErrorPage.svelte';
import TemplatePreviewPage from '@/features/Template/TemplatePreviewPage.svelte';
import PatientWizard from '@/features/Patient/PatientWizard.svelte';

export type Path =
  | '/dashboard/'
  | '/clients/'
  | '/patient/:data/'
  | '/doctor/:data/'
  | '/people/:data/'
  | '/calendar/'
  | '/templates/'
  | '/setup/'
  | '/assign/template/'
  | '/template/:data/'
  | '/wizard/template/'
  | '/wizard/record/'
  | '/wizard/record/preview/'
  | '/wizard/template/preview/'
  | '/wizard/patient/'
  | '/wizard/doctor/'
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
    path: '/calendar/',
    component: CalendarPage,
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
    path: '/templates/',
    component: TemplatesPage,
  },
  {
    path: '/assign/template/',
    component: TemplateAssignPage,
  },
  {
    path: '/template/:data/',
    component: TemplatePage,
  },
  {
    path: '/setup/',
    component: SetupPage,
  },
  {
    path: '/wizard/record/',
    component: RecordWizard,
  },
  {
    path: '/wizard/patient/',
    component: PatientWizard,
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
