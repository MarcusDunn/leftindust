import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TrigNode from './TrigNode.svelte';

import numberSocket from '@/features/Socket/components/NumberSocket';

const trigNode: NodeBlueprint<
  { OPERAND: SocketBlueprint<number> },
  { Number: SocketBlueprint<number> }
> = {
  inputs: { OPERAND: numberSocket },
  outputs: { Number: numberSocket },
  component: TrigNode,
  color: 'linear-gradient(#ff5776, #ff2d55)',
};

export default trigNode;
