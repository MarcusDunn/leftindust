import type { TemplateInput } from '.';
import { writable } from 'svelte/store';

export const TemplateInputItems = writable<{
  title: string;
  subtitle?: string;
  items: TemplateInput[];
}>({
  title: '',
  items: [],
});

export const TemplateSelectedTab = writable<'input' | 'output'>('input');