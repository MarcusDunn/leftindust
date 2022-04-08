import type { Path } from '@framework/modules/NavigationModule';

import DashboardPage from '@/pages/dashboard/DashboardPage.svelte';

import CalendarPage from '@/pages/calendar/CalendarPage.svelte';
import ClientsPage from '@/pages/clients/ClientsPage.svelte';
import FormsPage from '@/pages/forms/FormsPage.svelte';
import UsersPage from '@/pages/users/UsersPage.svelte';

import PatientPage from '@/pages/clients/pages/patient/PatientPage.svelte';
import DoctorPage from '@/pages/clients/pages/doctor/DoctorPage.svelte';
import PeoplePage from '@/pages/clients/pages/people/PeoplePage.svelte';
import EventPage from '@/pages/clients/pages/event/EventPage.svelte';
import VisitPage from '@/pages/clients/pages/visit/VisitPage.svelte';
import EventsPage from '@/pages/clients/pages/events/EventsPage.svelte';
import VisitsPage from '@/pages/clients/pages/visits/VisitsPage.svelte';
import UserPage from '@/pages/users/pages/user/UserPage.svelte';

import SurveyTemplateWizardPreviewPage
  from '@/pages/forms/wizards/template/SurveyTemplateWizardPreviewPage.svelte';

import SettingsPage from '@/modals/settings/SettingsPage.svelte';

import LoginPage from '@/modals/user/LoginPage.svelte';

// import NotFoundPage from '@/pages/errors/NotFoundPage.svelte';

import PatientWizard from '@/pages/clients/wizards/patient/PatientWizard.svelte';
import DoctorWizard from '@/pages/clients/wizards/doctor/DoctorWizard.svelte';
import VisitWizard from '@/pages/clients/pages/patient/wizards/visit/VisitWizard.svelte';
import EventWizard from '@/pages/calendar/wizards/event/EventWizard.svelte';
import SurveyTemplateWizard from '@/pages/forms/wizards/template/SurveyTemplateWizard.svelte';
import SurveyWizard from '@/pages/forms/wizards/survey/SurveyWizard.svelte';
import UserWizard from '@/pages/users/pages/user/wizards/UserWizard.svelte';

import SetupPage from '@/modals/setup/SetupPage.svelte';

import BlankPage from '@/pages/BlankPage.svelte';

export interface Route {
  path: Path;
  component: any;
  routes?: Array<Route>;
  detailRoutes?: Array<Route>;

  [key: string]: any;
}

export type SelectableRouteParams = {
  selectable: string;
  selectables: string;
};

const ROUTES: Route[] = [
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
    path: '/forms/',
    component: FormsPage,
  },
  {
    path: '/users/',
    component: UsersPage,
    master: true,
  },
  {
    path: '/user/:selectable/',
    component: UserPage,
  },
  {
    path: '/event/:selectable/',
    component: EventPage,
  },
  {
    path: '/visit/:selectable/',
    component: VisitPage,
  },
  {
    path: '/doctor/:selectable/',
    component: DoctorPage,
  },
  {
    path: '/patient/:selectable/',
    component: PatientPage,
  },
  {
    path: '/events/:selectable/',
    component: EventsPage,
  },
  {
    path: '/visits/:selectable/',
    component: VisitsPage,
  },
  {
    path: '/wizard/patient/',
    component: PatientWizard,
  },
  {
    path: '/wizard/doctor/',
    component: DoctorWizard,
  },
  {
    path: '/wizard/visit/',
    component: VisitWizard,
  },
  {
    path: '/wizard/event/',
    component: EventWizard,
  },
  {
    path: '/wizard/survey/',
    component: SurveyWizard,
  },
  {
    path: '/wizard/user/',
    component: UserWizard,
  },
  {
    path: '/wizard/survey/template/',
    component: SurveyTemplateWizard,
  },
  {
    path: '/wizard/survey/preview/',
    component: SurveyTemplateWizardPreviewPage,
  },
  {
    path: '/people/:selectables/',
    component: PeoplePage,
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
    path: '/setup/',
    component: SetupPage,
  },
  {
    path: '/blank/',
    component: BlankPage,
  },
];

export default ROUTES;
