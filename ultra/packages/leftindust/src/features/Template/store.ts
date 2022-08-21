import type { TemplateCalculationWithInstance, Template as TemplateType } from '.';
import { writable } from 'svelte/store';

export const Template = writable<TemplateType>({
  title: '',
  subtitle: undefined,
  sections: [{
    title: '',
    subtitle: undefined,
    inputs: [],
    id: 0,
  }],
});

export const TemplateSelectedTab = writable<'input' | 'output'>('input');
export const TemplateIndex = writable(0);

export const TemplateNodesModalOpen = writable(false);

export const TemplateCalculations = writable<TemplateCalculationWithInstance[]>([]);