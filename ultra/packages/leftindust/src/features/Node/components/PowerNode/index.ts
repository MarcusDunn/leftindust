import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import PowerNode from './PowerNode.svelte';

import numberSocket, { type NumberSocket } from '@/features/Socket/components/NumberSocket';

const defaultPowerSocket: NumberSocket = {
  ...numberSocket,
  defaultValue: 2,
}

const node: NodeBlueprint<
  { BASE: SocketBlueprint<number>, POWER: SocketBlueprint<number> },
  { Number: SocketBlueprint<number> }
> = {
  inputs: { BASE: numberSocket, POWER: defaultPowerSocket },
  outputs: { Number: numberSocket },
  component: PowerNode,
  color: 'linear-gradient(#ff5776, #ff2d55)',
};

export default node;
