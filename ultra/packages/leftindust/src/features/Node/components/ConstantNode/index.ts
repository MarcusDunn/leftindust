import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import ConstantNode from './ConstantNode.svelte';
import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<never, {
  Constant: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Constant: numberSocket,
  },
  component: ConstantNode,
  className: 'node-transparent',
};

export default node;