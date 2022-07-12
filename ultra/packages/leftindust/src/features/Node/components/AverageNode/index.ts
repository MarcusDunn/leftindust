import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import AverageNode from './AverageNode.svelte';

import numberArraySocket from '@/features/Socket/components/NumberArraySocket';
import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<{
  Numbers: SocketBlueprint<number[]>
},
  {
    Number: SocketBlueprint<number>
  }
  > = {
    inputs: {
      Numbers: numberArraySocket,
    },
    outputs: {
      Number: numberSocket,
    },
    component: AverageNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;