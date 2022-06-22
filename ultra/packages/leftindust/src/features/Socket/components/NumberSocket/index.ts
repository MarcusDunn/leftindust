import type { SocketBlueprint } from 'function-junctions/types';

export type NumberSocket = SocketBlueprint<number>;

const socket: NumberSocket = {
  type: 'number',
  defaultValue: 0,
};

export default socket;
