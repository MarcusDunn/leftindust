import type { SocketBlueprint } from 'function-junctions/types';

export type DateSocket = SocketBlueprint<Date>;

const socket: DateSocket = {
  type: 'date',
  defaultValue: new Date(),
  color: '#009688',
};

export default socket;
