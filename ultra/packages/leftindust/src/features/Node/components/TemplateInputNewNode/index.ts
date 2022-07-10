import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateInputNode from './TemplateInputNewNode.svelte';

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
};

export default node;