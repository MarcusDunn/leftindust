import type { SvelteComponentDev } from 'svelte/internal';
import type { Color, Framework7Icon } from '../..';
import { dialogs } from './store';

export type DialogParams = {
  title: string;
  text?: string;
  icon?: Framework7Icon;
  color?: Color;
  vertical?: boolean;
  component?: {
    params?: Record<string, unknown>;
    component: typeof SvelteComponentDev;
  };
  link?: {
    label: string;
    onClick?: () => void;
  };
  buttons?: {
    label: string;
    primary?: boolean;
    onClick?: () => void;
  }[];
}

export const openDialog = (params: DialogParams) => {
  console.log('ok');
  dialogs.update((prevDialogs) => [...prevDialogs, params]);
};