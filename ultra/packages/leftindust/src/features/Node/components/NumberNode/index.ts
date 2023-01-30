import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import NumberNode from './NumberNode.svelte';
import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<never, {
  Number: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Number: numberSocket,
  },
  component: NumberNode,
  className: 'node-transparent',
};

export default node;