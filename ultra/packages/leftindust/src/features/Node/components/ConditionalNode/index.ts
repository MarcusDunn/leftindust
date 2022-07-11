import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import ConditionalNode from './ConditionalNode.svelte';

import booleanSocket from '@/features/Socket/components/BooleanSocket';
import numberSocket from '@/features/Socket/components/NumberSocket';

const node: NodeBlueprint<
  { LHS: SocketBlueprint<number>, RHS: SocketBlueprint<number> },
  { Boolean: SocketBlueprint<boolean> }
> = {
  inputs: { LHS: numberSocket, RHS: numberSocket },
  outputs: { Boolean: booleanSocket },
  component: ConditionalNode,
  color: 'linear-gradient(#ff5776, #ff2d55)',
};

export default node;