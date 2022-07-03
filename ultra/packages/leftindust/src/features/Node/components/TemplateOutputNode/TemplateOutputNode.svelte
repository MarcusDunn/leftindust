<script lang="ts">
  import Select from '@/features/Input/components/Select/Select.svelte';
  
  import type { Editor,  InputSocket, InputSockets } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';

  import { getTemplateSocketType, templateCalculationSockets, TemplateInputType } from '@/features/Templates';
  import { _ } from '@/language';
  import { TemplateCalculations } from '@/features/Templates/store';
  import Input from '@/features/Input/Input.svelte';

  export let editor: Editor;
  
  export let inputs: InputSockets<{
    Value: InputSocket<unknown>;
  }>;

  const { value: Value, connection } = inputs.Value;
  let value: Writable<unknown> | undefined;
    
  export let store: {
    index: number;
  } = {
    index: 0,
  };
    
  let prevType = $TemplateCalculations[store.index].type;

  const reevaluateConnections = () => {
    if ($TemplateCalculations[store.index].type !== prevType) {
      $connection = undefined;

      prevType = $TemplateCalculations[store.index].type;
    }
  };
    
  $: value = editor.outputs?.Value?.value;

  $: $TemplateCalculations[store.index].type, (() => {
    if (value && Value) $value = $Value;
  })();

  $: $TemplateCalculations[store.index], (() => {
    const type = getTemplateSocketType($TemplateCalculations[store.index].type);

    if (type) {
      const socket = templateCalculationSockets[type];
  
      if (socket) {
        inputs.Value.type = type;
        inputs.Value.disabled = !value;
        inputs.Value.color = socket.color;
      }
    }
  })();

  $: $TemplateCalculations, reevaluateConnections();
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
    bind:value={$TemplateCalculations[store.index].type}
  />
  <p />
  <Input style="width: 100%">
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={$TemplateCalculations[store.index].label}
      placeholder={$_('examples.calculation')}
    />
  </Input>
  <br />
</div>