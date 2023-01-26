import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import MathNode from './MathNode.svelte';

import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<{
  LHS: SocketBlueprint<number>
  RHS: SocketBlueprint<number>
},
  {
    Output: SocketBlueprint<number>
  }
  > = {
    inputs: {
      LHS: numberSocket,
      RHS: numberSocket,
    },
    outputs: {
      Output: numberSocket,
    },
    component: MathNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;