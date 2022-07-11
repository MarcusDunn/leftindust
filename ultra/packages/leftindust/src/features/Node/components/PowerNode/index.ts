import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import PowerNode from './PowerNode.svelte';

import numberSocket, { type NumberSocket } from '@/features/Socket/components/NumberSocket';
import { DEFAULT_ROOT_DEGREE } from '../..';

const defaultPowerSocket: NumberSocket = {
  ...numberSocket,
  defaultValue: DEFAULT_ROOT_DEGREE,
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
