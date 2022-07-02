import { writable } from 'svelte/store';
import type { MenuNode } from '../..';

export const temporarySelectedNode = writable<MenuNode[]>([]);