<script lang="ts">
  import { Template } from '@/features/Template/store';

  import { getTemplateSocketType, templateCalculationSockets, templateInputSelectOptions, type TemplateCalculationSockets } from '@/features/Template';
  import type { Editor, OutputSocket, OutputSockets } from 'function-junctions/types';
  import { _ } from '@/language';
  import { ListItem } from 'framework7-svelte';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import Input from '@/features/Input/Input.svelte';
  import { SurveyTemplateInputType } from '@/api/server';
  import { get } from 'svelte/store';

  export let outputs: OutputSockets<{
    Values: OutputSocket<unknown[]>;
    Indexes: OutputSocket<number[]>;
  }>;

  export let editor: Editor;
  
  const { value: Values } = outputs.Values;
  const { value: Indexes } = outputs.Indexes;

  let rerenderSmartSelect = false;
  let smartSelectOpen = false;
  
  export let store: {
    type: SurveyTemplateInputType;
    ids: number[];
    options: Record<string, string[] | undefined>;
  } = {
    type: SurveyTemplateInputType.Text,
    ids: [],
    options: {},
  };

  let prevType = store.type;

  $: inputs = $Template.sections.flatMap((section, index) =>
    section.inputs.map((input, inputIndex) => {
      if (
        (input.type === SurveyTemplateInputType.SingleSelect
          || input.type === SurveyTemplateInputType.MultiSelect)
          && store.ids.includes(input.id)
      ) {
        store.options[input.id] = input.options;
      } else {
        if (store.options[input.id]) delete store.options[input.id];
      }

      return {
        ...input,
        sectionIndex: index,
        index: inputIndex,
        label: `${input.label}${
          $Template.sections.length > 1
            // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
            ? ` (${$_('generics.sectionIndexed', { values: { number: index + 1 } })})`
            : ''
        }`,
      };
    }),
  ).filter((input) => input.type === store.type);

  $: $Values = store.ids.map(
    (id) => editor.inputs?.[id]?.value ? get(editor.inputs[id].value) : undefined,
  ).filter((value) => value !== undefined);

  $: {
    const type = `${getTemplateSocketType(store.type)}_array` as keyof TemplateCalculationSockets;
    
    const socket = templateCalculationSockets[type];

    if (prevType !== store.type) store.ids = [];
  
    if (socket) {
      outputs.Values.type = type;
      outputs.Values.disabled = !Values;
      outputs.Values.color = socket.color;
    }

    prevType = store.type;
  }

  $: {
    if (store.type === SurveyTemplateInputType.SingleSelect) {
      const indexes: number[] = store.ids.map((id) => {
        const index = inputs.findIndex((input) => input.id === id);
        const value = editor.inputs?.[id]?.value ? get(editor.inputs[id].value) as string[] : undefined;

        if (index === -1 || !value) return undefined;

        return inputs[index].options.findIndex((option) => option === value[0]) + 1;
      }).filter((value): value is number => value !== undefined);

      $Indexes = indexes;
      outputs.Indexes.disabled = false;
    } else {
      $Indexes = [];
      outputs.Indexes.disabled = true;
    }
  }

  // Rerendering bug with key to preserve smartSelect instance when open
  $: inputs, (() => {
    if (!smartSelectOpen) rerenderSmartSelect =  !rerenderSmartSelect;
  })();
</script>

<div style="min-width: 430px; max-width: 430px">
  <Select
    title={$_('generics.type')}
    placeholder={$_('examples.text')}
    options={templateInputSelectOptions}
    bind:value={store.type}
  />
  <p />
  <Input title="Inputs" disabled={inputs.length < 1}>
    {#key rerenderSmartSelect}
      <ListItem
        class="input-select"
        style="width: 100%"
        smartSelect
        smartSelectParams={{
          openIn: 'popover',
          closeOnSelect: true,
          on: {
            open: () => (smartSelectOpen = true),
            close: () => (smartSelectOpen = false),
          },
        }}
        title={// TODO: Localization
          store.ids.length == 0 ? (inputs.length < 1 ? 'No inputs available.' : 'No inputs selected.') : ''}
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