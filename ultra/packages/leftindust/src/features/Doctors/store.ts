import { writable } from 'svelte/store';
import type { PartialDoctorFragment, Data, DoctorFragment } from '@/api/server';

export const doctorsSelectedAttachments = writable<Data[]>([]);
export const doctorsSelectedAttachmentsFragments = writable<Record<string, PartialDoctorFragment>>({});
export const selectedDoctor = writable<DoctorFragment | undefined>();
export const isDoctorSelected = writable<boolean>(false);