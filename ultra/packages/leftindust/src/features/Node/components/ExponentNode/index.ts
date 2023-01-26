import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import ExponentNode from './ExponentNode.svelte';

import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<{
  Base: SocketBlueprint<number>
  Power: SocketBlueprint<number>
},
  {
    Output: SocketBlueprint<number>
  }
  > = {
    inputs: {
      Base: numberSocket,
      Power: numberSocket,
    },
    outputs: {
      Output: numberSocket,
    },
    component: ExponentNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;