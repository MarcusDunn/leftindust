import { writable } from 'svelte/store';
import type { PartialDoctorFragment, IcdFragment, Data } from '@/api/server';

export const patientsSelectedAttachments = writable<Data[]>([]);
export const patientsSelectedAttachmentsFragments = writable<Record<string, IcdFragment | PartialDoctorFragment>>({});