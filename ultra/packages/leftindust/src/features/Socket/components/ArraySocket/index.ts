import type { SocketBlueprint } from 'function-junctions/types';

export type ArraySocket = SocketBlueprint<unknown[]>;

const socket: ArraySocket = {
  type: 'array',
  defaultValue: [],
  color: '#673ab7',
};

export default socket;
