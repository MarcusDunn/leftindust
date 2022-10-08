import { writable } from 'svelte/store';
import type { PartialDoctorFragment, Data } from '@/api/server';

export const doctorsSelectedAttachments = writable<Data[]>([]);
export const doctorsSelectedAttachmentsFragments = writable<Record<string, PartialDoctorFragment>>({});