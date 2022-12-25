import type { Data } from '@/api/server';
import type { ResolversTypes } from '@/api/server';
import type { SvelteComponentDev } from 'svelte/internal';
import type { Writable } from 'svelte/store';

import DoctorsBundle from '@/features/Doctors/components/DoctorsBundle/DoctorsBundle.svelte';

import PatientCard from '@/features/Patient/components/PatientCard/PatientCard.svelte';
import DoctorCard from '@/features/Doctor/components/DoctorCard/DoctorCard.svelte';
import UserCard from '@/features/User/components/UserCard/UserCard.svelte';
import TemplateCard from '@/features/Template/components/TemplateCard/TemplateCard.svelte';
// import IcdCard from '@/features/Icd/components/IcdCard/IcdCard.svelte';
import PatientsBundle from '../Patients/components/PatientsBundle/PatientsBundle.svelte';
import RecordsBundle from '../Records/components/RecordsBundle/RecordsBundle.svelte';
import RecordCard from '../Record/components/RecordCard/RecordCard.svelte';

export enum WidgetType {
  Attachment = 'attachment',
  Attribute = 'attribute',
  Bundle = 'bundle',
  Card = 'card',
  Cluster = 'cluster',
  Comparable = 'comparable',
  Stack = 'stack',
}

export enum WidgetCategory {
  Document = 'Document',
  Record = 'Record',
  Contact = 'Contact'
}

export type Widget = {
  type?: (keyof Partial<ResolversTypes>)[];
  category?: WidgetCategory[];
  component: typeof SvelteComponentDev;
  properties?: Record<string, unknown>;
};

export type DraggableWidgetProps<T = keyof Partial<ResolversTypes>> = {
  dragger: () => void | undefined;
  properties: Record<string, unknown>;
  data: Data<T>;
  quicklook: boolean;
};

export type CardProps<T = keyof Partial<ResolversTypes>, R = keyof Partial<ResolversTypes>> = DraggableWidgetProps<T> & {
  reference?: Data<R>;
  attachments?: Writable<Data<T>[]>;
};

export type BundleProps<T = keyof Partial<ResolversTypes>> = DraggableWidgetProps<T>;
export type StackProps<T = keyof Partial<ResolversTypes>> = DraggableWidgetProps<T>;

export type AttachmentProps = {
  attachments: Writable<string[]>;
  multiple: boolean;
  submit: () => void;
  back: boolean;
};

export type Widgets = {
  [K in WidgetType]: Record<string, Widget>;
};

const Widgets: Widgets = {
  attachment: {},
  attribute: {},
  bundle: {
    doctors: {
      type: ['Patient'],
      component: DoctorsBundle,
      category: [WidgetCategory.Contact],
    },
    patients: {
      type: ['Doctor'],
      component: PatientsBundle,
      category: [WidgetCategory.Contact],
    },
    record: {
      type: ['CompleteSurvey'],
      component: RecordsBundle,
      category: [WidgetCategory.Record],
    },
  },
  card: {
    patient: {
      type: ['Patient'],
      component: PatientCard,
      category: [WidgetCategory.Document],
    },
    doctor: {
      type: ['Doctor'],
      component: DoctorCard,
      category: [WidgetCategory.Document],
    },
    user: {
      type: ['MediqUser'],
      component: UserCard,
      category: [WidgetCategory.Document],
    },
    template: {
      type: ['SurveyTemplate'],
      component: TemplateCard,
      category: [WidgetCategory.Document],
    },
    record: {
      type: ['CompleteSurvey'],
      component: RecordCard,
      category: [WidgetCategory.Record],
    },
    /*
    icd: {
      type: ['IcdSimpleEntity', 'IcdLinearizationEntity'],
      component: IcdCard,
      category: [WidgetCategory.Record],
    },
    */
  },
  cluster: {},
  comparable: {},
  stack: {},
};

export default Widgets;
