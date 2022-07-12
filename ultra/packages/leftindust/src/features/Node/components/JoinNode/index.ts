import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import JoinNode from './JoinNode.svelte';

import textSocket, { type TextSocket } from '@/features/Socket/components/TextSocket';
import { DEFAULT_DELIMITER } from '../..';

const defaultDelimiterSocket: TextSocket = {
  ...textSocket,
  defaultValue: DEFAULT_DELIMITER,
}

const node: NodeBlueprint<{
  LHS: SocketBlueprint<string>,
  RHS: SocketBlueprint<string>,
  DELIMITER: SocketBlueprint<string>
}, {
  Text: SocketBlueprint<string>
}> = {
    inputs: {
      LHS: textSocket,
      RHS: textSocket,
      DELIMITER: defaultDelimiterSocket,
    },
    outputs: { Text: textSocket },
    component: JoinNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;