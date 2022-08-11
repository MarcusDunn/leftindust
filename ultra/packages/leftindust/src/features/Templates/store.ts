import type { CreateSurveyTemplate } from '@/api/server';
import type { TemplateCalculationWithInstance } from '.';
import { defaultTemplate } from '.';
import { writable } from 'svelte/store';

export const Template = writable<CreateSurveyTemplate>(JSON.parse(JSON.stringify(defaultTemplate)));

export const TemplateSelectedTab = writable<'input' | 'output'>('input');
export const TemplateIndex = writable(0);

export const TemplateNodesModalOpen = writable(false);

export const TemplateCalculations = writable<TemplateCalculationWithInstance[]>([]);