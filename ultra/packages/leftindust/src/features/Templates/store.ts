import type { Template, TemplateCalculationWithInstance } from '.';
import type { NodeState } from 'function-junctions/types';
import { defaultTemplate } from '.';
import { writable } from 'svelte/store';

export const TemplateInputItems = writable<Template>(JSON.parse(JSON.stringify(defaultTemplate)));

export const TemplateSelectedTab = writable<'input' | 'output'>('input');
export const TemplateIndex = writable(0);

export const TemplateInputUniqueIndex = writable(0);

export const TemplateNodesModalOpen = writable(false);

export const TemplateDefaultCalculation = writable<Record<string, NodeState>>({});
export const TemplateCalculations = writable<TemplateCalculationWithInstance[]>([]);