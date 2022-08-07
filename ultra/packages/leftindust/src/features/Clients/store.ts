import type {
  PartialDoctorFragment,
  IcdFragment,
  PartialPatientFragment,
  Data,
} from '@/api/server';

import { writable } from 'svelte/store';

import { ClientsTab } from '.';

export const clientsSelectedTab = writable<ClientsTab>(ClientsTab.Patients);
export const clientsSelected = writable<Data[]>([]);
export const clientsSearchQuery = writable('');

export const patientsSelectedAttachments = writable<Data[]>([]);
export const doctorsSelectedAttachments = writable<Data[]>([]);

export const patientsSelectedAttachmentsFragments = writable<Record<string, IcdFragment | PartialDoctorFragment>>({});
export const doctorsSelectedAttachmentsFragments = writable<Record<string, PartialPatientFragment>>({});
