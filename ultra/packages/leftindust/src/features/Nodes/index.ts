import type { NodeBlueprint } from 'function-junctions/types';
import type { Color, Framework7Icon } from '../UI';

export type MenuNode = {
  title: string;
  description?: string;
  blueprint: NodeBlueprint;
}

export type MenuNodeSection = {
  title: string;
  nodes: MenuNode[];
  description: string;
  icon: Framework7Icon;
  color?: Color;
};

export type MenuNodes = MenuNodeSection[];
