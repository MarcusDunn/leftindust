import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import RawTemplateOutputNode from './RawTemplateOutputNode.svelte';

const node: NodeBlueprint<{
  Value: SocketBlueprint<unknown>,
}> = {
  inputs: {
    Value: {
      type: '',
    },
  },
  component: RawTemplateOutputNode,
  className: 'node-transparent',
  cloneable: false,
  deletable: false,
};

export default node;