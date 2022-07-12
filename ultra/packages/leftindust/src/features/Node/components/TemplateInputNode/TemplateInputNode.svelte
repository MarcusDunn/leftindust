<script lang="ts">
  import { TemplateInputItems } from '@/features/Templates/store';

  import { getTemplateSocketType, templateCalculationSockets, templateInputSelectOptions, TemplateInputType, type TemplateCalculationSockets, type TemplateInput } from '@/features/Templates';
  import type { Editor, OutputSocket, OutputSockets, Node } from 'function-junctions/types';
  import { _ } from '@/language';
  import { BlockFooter, Button, Link, ListItem } from 'framework7-svelte';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import Input from '@/features/Input/Input.svelte';
  import { get } from 'svelte/store';

  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown>;
  }>;

  export let editor: Editor;
  export let id: string;
  
  const { value: Value } = outputs.Value;
  const { current: nodes } = editor.nodes;

  let rerenderSmartSelect = false;
  let smartSelectOpen = false;
  
  export let store: {
    id: number | undefined;
  } = {
    id: undefined,
  };

  let input: TemplateInput & {
    sectionIndex: number;
    index: number;
  } | undefined;

  let prevType: TemplateInputType;

  outputs.Value.disabled = !$Value;

  const changeInput = () => {
    input = inputs.find((input) => input.id === store?.id);
  };

  const reevaluateConnections = () => {
    if (input) {
      if ($TemplateInputItems.sections[input.sectionIndex].inputs[input.index]?.type !== prevType) {
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

        prevType = $TemplateInputItems.sections[input.sectionIndex].inputs[input.index]?.type;
      }
    }
  };

  $: inputs = $TemplateInputItems.sections.flatMap((section, index) =>
    section.inputs.map((input, inputIndex) => ({
      ...input,
      sectionIndex: index,
      index: inputIndex,
      label: `${input.label}${
        $TemplateInputItems.sections.length > 1
          // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
          ? ` (${$_('generics.sectionIndexed', { values: { number: index + 1 } })})`
          : ''
      }`,
    })),
  );

  $: store.id, changeInput();

  $: if (typeof store.id !== 'undefined' && editor.inputs?.[store.id]?.value) $Value = get(editor.inputs[store.id].value);

  $: if (input) {
    const type = getTemplateSocketType(input.type);

    const socket = templateCalculationSockets[type];
    
    if (socket) {
      outputs.Value.type = type;
      outputs.Value.disabled = !$Value;
      outputs.Value.color = socket.color;
    }
  }

  $: if (input && !prevType) prevType = $TemplateInputItems.sections[input.sectionIndex].inputs[input.index].type;

  $: inputs, reevaluateConnections();
  
  // Rerendering bug with key to preserve smartSelect instance when open
  $: inputs, (() => {
    if (!smartSelectOpen) rerenderSmartSelect =  !rerenderSmartSelect;
  })();
</script>

{#if input}
  <div style="min-width: 430px">
    <Select
      title={$_('generics.type')}
      placeholder={$_('examples.text')}
      options={templateInputSelectOptions}
      bind:value={$TemplateInputItems.sections[input.sectionIndex].inputs[input.index].type}
    />
    <p />
    <Input title="Input" disabled={inputs.length < 1}>
      {#key rerenderSmartSelect}
        <ListItem
          class="input-select"
          smartSelect
          smartSelectParams={{
            openIn: 'popover',
            closeOnSelect: true,
            on: {
              open: () => (smartSelectOpen = true),
              close: () => (smartSelectOpen = false),
            },
          }}
          title=""
          slot="content"
        >
          <select name="input" bind:value={store.id}>
            {#each inputs as i}
              <option value={i.id}>{i.label}</option>
            {/each}
          </select>
        </ListItem>
      {/key}
    </Input>
  </div>
{:else}
  <div class="text-align-center">
    <h4>Select an Input</h4>
    <BlockFooter>Select an input from your form to link to your calculation</BlockFooter>
    <Link
      smartSelect
      smartSelectParams={{
        openIn: 'popover',
        closeOnSelect: true,
        on: {
          open: () => (smartSelectOpen = true),
          close: () => (smartSelectOpen = false),
        },
      }}
    >
      <Button
        round
        outline
        color="deeppurple"
        text="Select Input"
      >
        <select name="input" bind:value={store.id}>
          <option value="" selected disabled>Eg. Input A</option>
          {#each inputs as i}
            <option value={i.id}>{i.label}</option>
          {/each}
        </select>
      </Button>
    </Link>
  </div>
{/if}