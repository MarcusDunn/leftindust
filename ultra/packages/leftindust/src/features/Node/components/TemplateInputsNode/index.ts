import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateInputsNode from './TemplateInputsNode.svelte';

const node: NodeBlueprint<never, {
  Values: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Values: {
      type: '',
    },
  },
  component: TemplateInputsNode,
  className: 'node-transparent',
};

export default node;