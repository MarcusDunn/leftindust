import { writable } from 'svelte/store';
import type { DoctorsFragment, Data } from '@/api/server';

export const doctorsSelectedAttachments = writable<Data[]>([]);
export const doctorsSelectedAttachmentsFragments = writable<Record<string, DoctorsFragment>>({});