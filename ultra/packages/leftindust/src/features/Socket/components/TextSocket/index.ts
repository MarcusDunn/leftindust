import type { SocketBlueprint } from 'function-junctions/types';

export type TextSocket = SocketBlueprint<number>;

const socket: TextSocket = {
  type: 'text',
  defaultValue: 0,
  color: '#ff9500',
};

export default socket;
