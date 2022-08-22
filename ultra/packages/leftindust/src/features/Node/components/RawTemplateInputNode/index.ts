import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import RawTemplateInputNode from './RawTemplateInputNode.svelte';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Value: {
      type: '',
    },
  },
  component: RawTemplateInputNode,
  className: 'node-transparent',
};

export default node;