import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateInputsNode from './TemplateInputsNode.svelte';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Value: {
      type: '',
    },
  },
  component: TemplateInputsNode,
  className: 'node-transparent',
  cloneable: false,
  deletable: false,
};

export default node;