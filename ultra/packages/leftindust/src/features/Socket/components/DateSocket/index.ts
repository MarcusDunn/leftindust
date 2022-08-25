import type { SocketBlueprint } from 'function-junctions/types';

export type DateSocket = SocketBlueprint<number>;

const socket: DateSocket = {
  type: 'date',
  defaultValue: new Date().getTime(),
  color: 'rgb(0, 199, 190)',
};

export default socket;
