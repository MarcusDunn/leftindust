import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import RawOutputNode from './RawOutputNode.svelte';

const node: NodeBlueprint<{
  Value: SocketBlueprint<unknown>,
}> = {
  inputs: {
    Value: {
      type: '',
    },
  },
  component: RawOutputNode,
  className: 'node-transparent',
  cloneable: false,
  deletable: false,
};

export default node;