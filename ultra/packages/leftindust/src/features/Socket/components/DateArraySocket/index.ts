import type { SocketBlueprint } from 'function-junctions/types';

export type DateArraySocket = SocketBlueprint<number[]>;

const socket: DateArraySocket = {
  type: 'date_array',
  defaultValue: [],
  color: '#009688',
};

export default socket;
