import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateInputNode from './TemplateInputNode.svelte';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Value: {
      type: '',
    },
  },
  component: TemplateInputNode,
  className: 'node-transparent',
  cloneable: false,
  deletable: false,
};

export default node;