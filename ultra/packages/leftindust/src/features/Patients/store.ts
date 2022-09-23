import { writable } from 'svelte/store';
import type { PartialDoctorFragment, Data } from '@/api/server';

export const patientsSelectedAttachments = writable<Data[]>([]);
export const patientsSelectedAttachmentsFragments = writable<Record<string, PartialDoctorFragment>>({});