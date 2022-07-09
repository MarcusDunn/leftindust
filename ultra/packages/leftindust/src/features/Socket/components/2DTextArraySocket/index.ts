import type { SocketBlueprint } from 'function-junctions/types';

export type TwoDimensionalTextArraySocket = SocketBlueprint<string[]>;

const socket: TwoDimensionalTextArraySocket = {
  type: 'text_array_array',
  defaultValue: [],
};

export default socket;
