import type { SocketBlueprint } from 'function-junctions/types';

export type BooleanSocket = SocketBlueprint<boolean>;

const socket: BooleanSocket = {
  type: 'boolean',
  defaultValue: false,
};

export default socket;
