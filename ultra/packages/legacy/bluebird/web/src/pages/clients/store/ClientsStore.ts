import type { Selectable } from '@framework/modules/SelectModule';
import type {
  BasicDoctorFragment,
  BasicIcdFragment,
  BasicPatientFragment,
} from '@framework/requests/fragments';
import { writable } from 'svelte/store';

export enum ClientsTab {
  Patients = 'Patients',
  Doctors = 'Doctors',
}

export enum ClientTabs {
  Overview = 'Overview',
  Documents = 'Documents',
  Records = 'Records',
  Contacts = 'Contacts'
}

export const ClientsSelectedTab = writable<ClientsTab>(ClientsTab.Patients);
export const ClientsSelected = writable<Selectable[]>([]);

export const PatientsSelectedAttachments = writable<Selectable[]>([]);
export const DoctorsSelectedAttachments = writable<Selectable[]>([]);

export const PatientsSelectedAttachmentsFragments = writable<Record<string, BasicIcdFragment | BasicDoctorFragment>>({});
export const DoctorsSelectedAttachmentsFragments = writable<Record<string, BasicPatientFragment>>({});

export const ClientsSearchQuery = writable('');
