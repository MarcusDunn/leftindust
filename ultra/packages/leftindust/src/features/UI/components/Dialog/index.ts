import type { Color, Framework7Icon } from '../..';
import { dialogs } from './store';

export type DialogParams = {
  title: string;
  text?: string;
  icon?: Framework7Icon;
  color?: Color;
  vertical?: boolean;
  link?: {
    label: string;
    onClick?: () => void;
  };
  buttons: {
    label: string;
    primary?: boolean;
    onClick?: () => void;
  }[];
}

export const openDialog = (params: DialogParams) => {
  dialogs.update((prevDialogs) => [...prevDialogs, params]);
};