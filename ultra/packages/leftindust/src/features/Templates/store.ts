import type { Template } from '.';
import { writable } from 'svelte/store';

export const TemplateInputItems = writable<Template>({
  title: '',
  sections: [{
    title: '',
    inputs: [],
    id: 0,
  }],
});

export const TemplateIndex = writable(0);

export const TemplateSelectedTab = writable<'input' | 'output'>('input');

export const TemplateInputUniqueIndex = writable(0);