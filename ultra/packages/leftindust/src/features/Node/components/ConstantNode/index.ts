import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import ConstantNode from './ConstantNode.svelte';
import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Value: numberSocket,
  },
  component: ConstantNode,
  className: 'node-transparent',
};

export default node;