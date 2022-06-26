<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { Editor, OutputSocket, OutputSockets } from 'function-junctions/types';
  import Input from '@/features/Input/Input.svelte';
  import { _ } from '@/language';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import { templateCalculationSockets, TemplateInputType } from '@/features/Templates';
  import { TemplateInputItems } from '@/features/Templates/store';
  
  export let editor: Editor;
  
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

  const { value: Value } = outputs.Value;
  
  let value: Writable<unknown> | undefined;
    
  const getSocketType = () => {
    let type: 'text' | 'number' | 'date' | 'array';
    const inputType = $TemplateInputItems.sections[store.sectionIndex].inputs[store.index].type;

    switch (inputType) {
      case TemplateInputType.Text:
      case TemplateInputType.Title:
      case TemplateInputType.Paragraph:
        type = 'text';
        break;
      case TemplateInputType.Number:
        type = 'number';
        break;
      case TemplateInputType.Date:
        type = 'date';
        break;
      case TemplateInputType.SingleSelect:
      case TemplateInputType.MultiSelect:
        type = 'array';
        break;
    }

    // TS being weird
    // @ts-expect-error
    return type;
  };

  $: $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type, (() => {
    value = editor.inputs?.[store.id]?.value;
  })();

  $: if (value) $Value = $value;

  $:  $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type, (() => {
    const type = getSocketType();

    if (type) {
      const socket = templateCalculationSockets[type];
  
      if (socket) {
        outputs.Value.type = type;
        outputs.Value.disabled = !value;
        outputs.Value.color = socket.color;
      }
    }
  })();

  $: $TemplateInputItems.sections[store.sectionIndex].inputs[store.index]?.type, getSocketType();
</script>

<div style="min-width: 430px">
  <Select
    title={$_('generics.type')}
    placeholder={$_('examples.text')}
    options={[
      {
        text: $_('generics.text'),
        value: TemplateInputType.Text,
      },
      {
        text: $_('generics.number'),
        value: TemplateInputType.Number,
      },
      {
        text: $_('generics.date'),
        value: TemplateInputType.Date,
      },
      {
        text: $_('generics.paragraph'),
        value: TemplateInputType.Paragraph,
      },
      {
        text: $_('generics.singleSelect'),
        value: TemplateInputType.SingleSelect,
      },
      {
        text: $_('generics.multiSelect'),
        value: TemplateInputType.MultiSelect,
      },
      {
        text: $_('generics.upload'),
        value: TemplateInputType.Upload,
      },
      {
        text: $_('generics.title'),
        value: TemplateInputType.Title,
      },
    ]}
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
