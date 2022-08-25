import type { SocketBlueprint } from 'function-junctions/types';

export type NumberArraySocket = SocketBlueprint<number[]>;

const socket: NumberArraySocket = {
  type: 'number_array',
  defaultValue: [],
};

export default socket;
