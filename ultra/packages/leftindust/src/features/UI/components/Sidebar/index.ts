import type { Color, Framework7Icon } from '../..';

export type SidebarItem = {
  title: string;
  icon: Framework7Icon;
  active: boolean;
  color?: Color;
  onClick?: () => void;
}

export type SidebarTitle = {
  title: string;
}