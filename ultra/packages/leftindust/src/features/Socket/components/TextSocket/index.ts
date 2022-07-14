import type { SocketBlueprint } from 'function-junctions/types';

export type TextSocket = SocketBlueprint<string>;

const socket: TextSocket = {
  type: 'text',
  defaultValue: '',
  color: '#ff9500',
};

export default socket;
