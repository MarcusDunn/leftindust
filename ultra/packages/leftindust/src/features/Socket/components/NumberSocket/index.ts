import type { SocketBlueprint } from 'function-junctions/types';

export type NumberSocket = SocketBlueprint<number>;

const socket: NumberSocket = {
  type: 'number',
  defaultValue: 0,
  color: '#2196f3',
};

export default socket;
