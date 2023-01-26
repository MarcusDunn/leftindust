import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import ConvertDistNode from './ConvertDistNode.svelte';

import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<{
  Input: SocketBlueprint<number>
},
  {
    Output: SocketBlueprint<number>
  }
  > = {
    inputs: {
      Input: numberSocket,
    },
    outputs: {
      Output: numberSocket,
    },
    component: ConvertDistNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;