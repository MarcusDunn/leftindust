import type { NodeBlueprint } from 'function-junctions/types';
import AverageNode from '../Node/components/AverageNode';
import DateNode from '../Node/components/DateNode';
import MathNode from '../Node/components/MathNode';
import NumberNode from '../Node/components/NumberNode';
import RawTemplateInputNode from '../Node/components/RawTemplateInputNode';
import RawTemplateInputsNode from '../Node/components/RawTemplateInputsNode';
import RawTemplateOutputNode from '../Node/components/RawTemplateOutputNode';
import TextNode from '../Node/components/TextNode';
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

export const calculationNodes: Record<string, NodeBlueprint> = {
  output: RawTemplateOutputNode,
  Group: RawTemplateInputsNode,
  Input: RawTemplateInputNode,
  Math: MathNode,
  Number: NumberNode,
  Text: TextNode,
  Date: DateNode,
  Average: AverageNode,
};