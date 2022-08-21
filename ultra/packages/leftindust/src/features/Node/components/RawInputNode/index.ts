import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import RawInputNode from './RawInputNode.svelte';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Value: {
      type: '',
    },
  },
  component: RawInputNode,
  className: 'node-transparent',
};

export default node;