<script lang="ts">
  import { get, type Writable } from 'svelte/store';
  import type { Editor, Node, OutputSocket, OutputSockets } from 'function-junctions/types';
  import Input from '@/features/Input/Input.svelte';
  import { _ } from '@/language';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import { getTemplateSocketType, templateCalculationSockets, templateInputSelectOptions, TemplateInputType } from '@/features/Templates';
  import { TemplateInputItems } from '@/features/Templates/store';
  
  export let editor: Editor;
  export let id: string;
  
  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown>;
  }>;

  export let store: {
    sectionIndex: number;
    index: number;
    id: string;
  } = {
    sectionIndex: 0,
    index: 0,
    id: '',
  };

  const { current: nodes } = editor.nodes;

  const { value: Value } = outputs.Value;
  
  let prevType = $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type;

  const reevaluateConnections = () => {
    if ($TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type !== prevType) {
      editor.nodes.current.update(() => Object.keys($nodes).reduce((newNodes: Record<string, Node>, key) => {
        const oldNode = $nodes[key];
        const inputs = oldNode.inputs;
  
        if (inputs) {
          Object.keys(inputs).forEach((inputKey) => {
            const connection = get(inputs[inputKey].connection);
  
            if (connection?.connectedSocketId && connection?.connectedNodeId === id) {
              inputs[inputKey].connection.update(() => undefined);
            }
          });
        }
  
        newNodes[key] = oldNode;
  
        return newNodes;
      }, {}));
      
      prevType = $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type;
    }
  };

  $: if (editor.inputs?.[store.id]?.value) $Value = get(editor.inputs?.[store.id]?.value);

  $: $TemplateInputItems, (() => {
    const type = getTemplateSocketType($TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type);

    if (type) {
      const socket = templateCalculationSockets[type];
  
      if (socket) {
        outputs.Value.type = type;
        outputs.Value.disabled = !$Value;
        outputs.Value.color = socket.color;
      }
    }
  })();

  $: $TemplateInputItems, reevaluateConnections();
</script>

{#if $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]}
  <div style="min-width: 430px">
    <Select
      title={$_('generics.type')}
      placeholder={$_('examples.text')}
      options={templateInputSelectOptions}
      bind:value={$TemplateInputItems.sections[store.sectionIndex].inputs[store.index].type}
    />
    <p />
    <Input style="width: 100%">
      <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
      <input
        type="text"
        bind:value={$TemplateInputItems.sections[store.sectionIndex].inputs[store.index].label}
        placeholder={$_('examples.totalPlateletCount')}
      />
    </Input>
  </div>
{/if}