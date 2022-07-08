import type { NodeBlueprint, SocketBlueprint } from 'function-junctions/types';
import TemplateOutputNode from './TemplateOutputNode.svelte';

const node: NodeBlueprint<{
  Value: SocketBlueprint<unknown>,
}> = {
  inputs: {
    Value: {
      type: '',
    },
  },
  component: TemplateOutputNode,
  className: 'node-transparent',
  cloneable: false,
  deletable: false,
};

export default node;