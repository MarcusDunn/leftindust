import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import LogNode from './LogNode.svelte';

import numberSocket, { type NumberSocket } from '@/features/Socket/components/NumberSocket';

const defaultBaseSocket: NumberSocket = {
  ...numberSocket,
  defaultValue: 10,
}

const node: NodeBlueprint<
  { BASE: SocketBlueprint<number>, VALUE: SocketBlueprint<number> },
  { Number: SocketBlueprint<number> }
> = {
  inputs: { BASE: defaultBaseSocket, VALUE: numberSocket },
  outputs: { Number: numberSocket },
  component: LogNode,
  color: 'linear-gradient(#ff5776, #ff2d55)',
};

export default node;
