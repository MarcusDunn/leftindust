import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import DateNode from './DateNode.svelte';
import dateSocket from '@/features/Socket/components/DateSocket';

const node: NodeBlueprint<never, {
  Value: SocketBlueprint<number>,
}> = {
  outputs: {
    Value: dateSocket,
  },
  component: DateNode,
  className: 'node-transparent',
};

export default node;