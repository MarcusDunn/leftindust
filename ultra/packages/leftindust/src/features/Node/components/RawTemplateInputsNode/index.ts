import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import RawTemplateInputsNode from './RawTemplateInputsNode.svelte';

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
  component: RawTemplateInputsNode,
  className: 'node-transparent',
};

export default node;