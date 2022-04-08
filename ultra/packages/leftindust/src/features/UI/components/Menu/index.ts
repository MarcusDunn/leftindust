import type { Framework7Icon } from '../..';

export type MenuItem = {
  title: string;
  text?: string;
  icon?: Framework7Icon;
  onClick?: () => void;
};
