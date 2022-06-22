import type { Template } from '.';
import type { NodeState } from 'function-junctions/types';
import { DefaultTemplate } from '.';
import { writable } from 'svelte/store';

export const TemplateInputItems = writable<Template>(JSON.parse(JSON.stringify(DefaultTemplate)));

export const TemplateSelectedTab = writable<'input' | 'output'>('input');
export const TemplateIndex = writable(0);

export const TemplateInputUniqueIndex = writable(0);

export const TemplateNodesModalOpen = writable(false);

export const TemplateDefaultNodes = writable<Record<string, NodeState>>({});