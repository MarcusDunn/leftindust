import type { Color, Framework7Icon } from '@/features/UI';

export type AppLayoutSidebarTitle = {
  title: string;
};

export type AppLayoutItem = {
  title: string;
  url: string;
  id: string;
  icon: Framework7Icon;
  active: boolean;
  color?: Color;
  master?: boolean;
  onClick?: () => void;
}