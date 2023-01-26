import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import LogNode from './LogNode.svelte';

import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<{
  Arg: SocketBlueprint<number>
  Base: SocketBlueprint<number>
},
  {
    Output: SocketBlueprint<number>
  }
  > = {
    inputs: {
      Arg: numberSocket,
      Base: numberSocket,
    },
    outputs: {
      Output: numberSocket,
    },
    component: LogNode,
    color: 'linear-gradient(#ff5776, #ff2d55)',
  };

export default node;