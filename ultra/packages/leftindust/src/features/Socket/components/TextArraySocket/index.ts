import type { SocketBlueprint } from 'function-junctions/types';

export type TextArraySocket = SocketBlueprint<string[]>;

const socket: TextArraySocket = {
  type: 'text_array',
  defaultValue: [],
  color: '#ff6b22',
};

export default socket;
