import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TextNode from './TextNode.svelte';
import textSocket from '@/features/Socket/components/TextSocket';

const node: NodeBlueprint<never, {
  Text: SocketBlueprint<unknown>,
}> = {
  outputs: {
    Text: textSocket,
  },
  component: TextNode,
  className: 'node-transparent',
};

export default node;