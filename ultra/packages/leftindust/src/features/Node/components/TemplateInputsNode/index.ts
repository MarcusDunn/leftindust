import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateInputsNode from './TemplateInputsNode.svelte';

const node: NodeBlueprint<never, {
  Values: SocketBlueprint<unknown>,
  Indexes: SocketBlueprint<number>,
}> = {
  outputs: {
    Values: {
      type: '',
    },
    Indexes: {
      type: 'number_array',
    },
  },
  component: TemplateInputsNode,
  className: 'node-transparent',
};

export default node;