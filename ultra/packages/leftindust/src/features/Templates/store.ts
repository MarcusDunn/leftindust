import type { Template } from '.';
import { DefaultTemplate } from '.';
import { writable } from 'svelte/store';

export const TemplateInputItems = writable<Template>(JSON.parse(JSON.stringify(DefaultTemplate)));

export const TemplateSelectedTab = writable<'input' | 'output'>('input');
export const TemplateIndex = writable(0);

export const TemplateInputUniqueIndex = writable(0);