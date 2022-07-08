import type { SocketBlueprint } from 'function-junctions/types';

export type DateSocket = SocketBlueprint<Date>;

const socket: DateSocket = {
  type: 'date',
  defaultValue: new Date(),
  color: 'rgb(0, 199, 190)',
};

export default socket;
