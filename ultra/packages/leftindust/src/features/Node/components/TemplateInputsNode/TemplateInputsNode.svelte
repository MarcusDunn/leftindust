<script lang="ts">
  import { TemplateInputItems } from '@/features/Templates/store';

  import { getTemplateSocketType, templateCalculationSockets, templateInputSelectOptions, TemplateInputType, type TemplateCalculationSockets } from '@/features/Templates';
  import type { Editor, OutputSocket, OutputSockets } from 'function-junctions/types';
  import { _ } from '@/language';
  import { ListItem } from 'framework7-svelte';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import Input from '@/features/Input/Input.svelte';
  import { get } from 'svelte/store';

  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown[]>;
  }>;

  export let editor: Editor;
  
  const { value: Value } = outputs.Value;

  let prevType = TemplateInputType.Text;

  export let store: {
    type: TemplateInputType;
    ids: string[];
  } = {
    type: TemplateInputType.Text,
    ids: [],
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
  ).filter((input) => input.type === store.type);

  $: $Value = store.ids.map(
    (id) => editor.inputs?.[id]?.value ? get(editor.inputs[id].value) : undefined,
  ).filter((value) => value !== undefined);

  $: {
    const type = `${getTemplateSocketType(store.type)}_array` as keyof TemplateCalculationSockets;
    
    const socket = templateCalculationSockets[type];

    if (prevType !== store.type) store.ids = [];
  
    if (socket) {
      outputs.Value.type = type;
      outputs.Value.disabled = !Value;
      outputs.Value.color = socket.color;
    }

    prevType = store.type;
  }
</script>

<div style="min-width: 430px">
  <Select
    title={$_('generics.type')}
    placeholder={$_('examples.text')}
    options={templateInputSelectOptions}
    bind:value={store.type}
  />
  <p />
  <Input title="Inputs" disabled={inputs.length < 1}>
    {#key inputs}
      <ListItem
        class="input-select"
        smartSelect
        smartSelectParams={{
          openIn: 'popover',
          closeOnSelect: true,
        }}
        title=""
        slot="content"
      >
        <select placeholder="Eg. Input A, Input B" name="inputs" multiple bind:value={store.ids}>
          {#each inputs as input}
            <option value={input.id}>{input.label}</option>
          {/each}
        </select>
      </ListItem>
    {/key}
  </Input>
</div>