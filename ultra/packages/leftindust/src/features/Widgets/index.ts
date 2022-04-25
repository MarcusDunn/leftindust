import type { Data } from '@/api/server';
import type { ResolversTypes } from '@/api/server';
import type { SvelteComponentDev } from 'svelte/internal';
import type { Writable } from 'svelte/store';

import PatientCard from '@/features/Patient/components/PatientCard/PatientCard.svelte';
import DoctorCard from '@/features/Doctor/components/DoctorCard/DoctorCard.svelte';
import UserCard from '@/features/User/components/UserCard/UserCard.svelte';
import IcdCard from '@/features/Icd/components/IcdCard/IcdCard.svelte';

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
};

export type CardProps<T = keyof Partial<ResolversTypes>, R = keyof Partial<ResolversTypes>> = DraggableWidgetProps<T> & {
  reference?: Data<R>;
  attachments?: Writable<Data<T>[]>;
  quicklook: boolean;
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
  bundle: {},
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
      type: ['User', 'FirebaseInfo'],
      component: UserCard,
      category: [WidgetCategory.Document],
    },
    icd: {
      type: ['IcdSimpleEntity', 'IcdLinearizationEntity'],
      component: IcdCard,
      category: [WidgetCategory.Record],
    },
  },
  cluster: {},
  comparable: {},
  stack: {},
};

export default Widgets;
