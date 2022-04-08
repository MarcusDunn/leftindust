import { writable } from 'svelte/store';
import type { BasicDoctorFragment, BasicIcdFragment, Data } from '@/api/server';

export const patientsSelectedAttachments = writable<Data[]>([]);
export const patientsSelectedAttachmentsFragments = writable<Record<string, BasicIcdFragment | BasicDoctorFragment>>({});