import type { SocketBlueprint } from 'function-junctions/types';

export type TextSocket = SocketBlueprint<number>;

const socket: TextSocket = {
  type: 'text',
  defaultValue: 0,
};

export default socket;
