import { writable } from 'svelte/store';
import type { Doctor } from '@/api/server';

export const doctorCreate = writable<Record<string, Doctor>>({});

// export const doctorsSelectedAttachments = writable<Data[]>([]);
// export const doctorsSelectedAttachmentsFragments = writable<Record<string, DoctorFragment>>({});