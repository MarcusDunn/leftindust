import type { Comparable } from '../requests';
import type { Framework7Icon } from '../modules/UIModule';

import IcdAttachment from './components/attachment/IcdAttachment.svelte';
import PatientAttachment from './components/attachment/PatientAttachment.svelte';
import DoctorAttachment from './components/attachment/DoctorAttachment.svelte';
import EventAttachment from './components/attachment/EventAttachment.svelte';
import SurveyAttachment from './components/attachment/SurveyAttachment.svelte';
import UserAttachment from './components/attachment/UserAttachment.svelte';

import CalendarCluster from './components/cluster/CalendarCluster.svelte';

import InsuranceBundle from './components/bundle/InsuranceBundle.svelte';
import EventsBundle from './components/bundle/EventsBundle.svelte';
import VisitsBundle from './components/bundle/VisitsBundle.svelte';
import ImagingBundle from './components/bundle/ImagingBundle.svelte';
import DoctorsBundle from './components/bundle/DoctorsBundle.svelte';
import PatientsBundle from './components/bundle/PatientsBundle.svelte';
import EmergencyContactsBundle from './components/bundle/EmergencyContactsBundle.svelte';
import SurveysBundle from './components/bundle/SurveysBundle.svelte';

import EventsStack from './components/stack/EventsStack.svelte';
import InsuranceStack from './components/stack/InsuranceStack.svelte';

import EventWidget from './components/widget/EventWidget.svelte';
import PatientWidget from './components/widget/PatientWidget.svelte';
import DoctorWidget from './components/widget/DoctorWidget.svelte';
import IcdWidget from './components/widget/IcdWidget.svelte';
import SurveyWidget from './components/widget/SurveyWidget.svelte';
import VisitWidget from './components/widget/VisitWidget.svelte';
import UserWidget from './components/widget/UserWidget.svelte';

import InsuranceProperties from './properties/InsuranceProperties';
import IcdProperties from './properties/IcdProperties';
import EventsProperties from './properties/EventsProperties';
import VisitsProperties from './properties/VisitsProperties';
import DoctorsProperties from './properties/DoctorsProperties';
import ImagingProperties from './properties/ImagingProperties';
import EmergencyContactsProperties from './properties/EmergencyContactsProperties';
import PatientsProperties from './properties/PatientsProperties';
import SurveysProperties from './properties/SurveysProperties';
import UsersProperties from './properties/UsersProperties';

export type PluginComparables = Comparable | 'Pinned';
export enum PluginCategories {
  Document = 'Documents',
  Record = 'Records',
  Contact = 'Contacts'
}

export enum PluginTypes {
  Attachment = 'attachments',
  Attribute = 'attributes',
  Bundle = 'bundles',
  Cluster = 'clusters',
  Comparable = 'comparables',
  Stack = 'stacks',
  Widget = 'widgets',
}

export type Plugin = {
  comparables?: PluginComparables[];
  categories?: PluginCategories[];
  plugin: unknown;
  properties?: Record<string, unknown> & {
    title: string;
    description?: string;
    color: string;
    icon: Framework7Icon;
  };
};

export type Plugins = {
  [K in PluginTypes]: Record<string, Plugin>;
};

const Plugins: Plugins = {
  attachments: {
    icd: {
      plugin: IcdAttachment,
      comparables: ['IcdSimpleEntity', 'IcdLinearizationEntity'],
      properties: IcdProperties,
    },
    patient: {
      plugin: PatientAttachment,
      comparables: ['Patient'],
      properties: PatientsProperties,
    },
    doctor: {
      plugin: DoctorAttachment,
      comparables: ['Doctor'],
      properties: DoctorsProperties,
    },
    event: {
      plugin: EventAttachment,
      comparables: ['Event'],
      properties: EventsProperties,
    },
    survey: {
      plugin: SurveyAttachment,
      comparables: ['GraphQLFormTemplate'],
      properties: SurveysProperties,
    },
    user: {
      plugin: UserAttachment,
      comparables: ['User'],
      properties: UsersProperties,
    },
  },
  attributes: {},
  bundles: {
    insurance: {
      plugin: InsuranceBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Document],
      properties: InsuranceProperties,
    },
    events: {
      plugin: EventsBundle,
      comparables: ['Patient', 'Doctor'],
      categories: [PluginCategories.Document],
      properties: EventsProperties,
    },
    visits: {
      plugin: VisitsBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Document],
      properties: VisitsProperties,
    },
    imaging: {
      plugin: ImagingBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Record],
      properties: ImagingProperties,
    },
    doctors: {
      plugin: DoctorsBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Contact],
      properties: DoctorsProperties,
    },
    patients: {
      plugin: PatientsBundle,
      comparables: ['Doctor'],
      categories: [PluginCategories.Contact],
      properties: PatientsProperties,
    },
    emergencyContacts: {
      plugin: EmergencyContactsBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Contact],
      properties: EmergencyContactsProperties,
    },
    surveys: {
      plugin: SurveysBundle,
      comparables: ['Patient'],
      categories: [PluginCategories.Document],
      properties: SurveysProperties,
    },
  },
  clusters: {
    calendar: {
      plugin: CalendarCluster,
    },
  },
  comparables: {},
  stacks: {
    insurance: {
      plugin: InsuranceStack,
      comparables: ['Patient'],
      categories: [PluginCategories.Document],
      properties: InsuranceProperties,
    },
    events: {
      plugin: EventsStack,
      comparables: ['Patient', 'Doctor'],
      categories: [PluginCategories.Document],
      properties: EventsProperties,
    },
  },
  widgets: {
    patient: {
      plugin: PatientWidget,
      comparables: ['Patient'],
      categories: [PluginCategories.Document],
      properties: PatientsProperties,
    },
    doctor: {
      plugin: DoctorWidget,
      comparables: ['Doctor'],
      categories: [PluginCategories.Document],
      properties: DoctorsProperties,
    },
    event: {
      plugin: EventWidget,
      comparables: ['Event'],
      categories: [PluginCategories.Document],
      properties: EventsProperties,
    },
    icd: {
      plugin: IcdWidget,
      comparables: ['IcdSimpleEntity', 'IcdLinearizationEntity'],
      categories: [PluginCategories.Record],
      properties: IcdProperties,
    },
    survey: {
      plugin: SurveyWidget,
      comparables: ['GraphQLFormTemplate'],
      categories: [PluginCategories.Document],
      properties: SurveysProperties,
    },
    visit: {
      plugin: VisitWidget,
      comparables: ['Visit'],
      categories: [PluginCategories.Document],
      properties: VisitsProperties,
    },
    user: {
      plugin: UserWidget,
      comparables: ['User', 'FirebaseInfo'],
      categories: [PluginCategories.Document],
      properties: UsersProperties,
    },
  },
};

export default Plugins;
