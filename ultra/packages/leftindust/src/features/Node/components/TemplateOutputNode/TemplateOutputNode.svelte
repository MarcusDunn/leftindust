<script lang="ts">
  import Select from '@/features/Input/components/Select/Select.svelte';
  
  import type { Editor,  InputSocket, InputSockets } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';

  import { getInputType, getTemplateSocketType, templateCalculationSockets } from '@/features/Template';
  import { _ } from '@/language';
  import { TemplateCalculations } from '@/features/Template/store';
  import { SurveyTemplateInputType } from '@/api/server';
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
    
  let prevType = $TemplateCalculations[store.index].inputType;

  $: calculation = $TemplateCalculations[store.index];

  const reevaluateConnections = () => {
    if (calculation?.inputType !== prevType) {
      $connection = undefined;

      prevType = calculation?.inputType;
    }
  };
    
  $: value = editor.outputs?.Value?.value;

  $: calculation?.inputType, (() => {
    if (value && Value) $value = $Value;
  })();

  $: calculation, (() => {
    if (calculation?.inputType) {
      // TS has brain damage
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      const type = getTemplateSocketType(calculation.inputType!);
  
      if (type) {
        const socket = templateCalculationSockets[type];
    
        if (socket) {
          inputs.Value.type = type;
          inputs.Value.disabled = !value;
          inputs.Value.color = socket.color;
        }
      }
    }
  })();

  $: $TemplateCalculations, reevaluateConnections();
</script>

{#if calculation?.inputType}
  <div style="min-width: 430px">
    <Select
      title={$_('generics.type')}
      placeholder={calculation.inputType ? getInputType(calculation.inputType) : ''}
      options={[
        {
          text: $_('generics.text'),
          value: SurveyTemplateInputType.Text,
        },
        {
          text: $_('generics.number'),
          value: SurveyTemplateInputType.Number,
        },
        {
          text: $_('generics.date'),
          value: SurveyTemplateInputType.Date,
        },
        {
          text: $_('generics.paragraph'),
          value: SurveyTemplateInputType.Paragraph,
        },
        {
          text: $_('generics.singleSelect'),
          value: SurveyTemplateInputType.SingleSelect,
        },
        {
          text: $_('generics.multiSelect'),
          value: SurveyTemplateInputType.MultiSelect,
        },
        {
          text: $_('generics.upload'),
          value: SurveyTemplateInputType.Upload,
        },
        {
          text: $_('generics.title'),
          value: SurveyTemplateInputType.Title,
        },
      ]}
      bind:value={calculation.inputType}
      name={calculation.inputType}
    />
    <p />
    <Input style="width: 100%">
      <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
      <input
        type="text"
        bind:value={calculation.label}
        placeholder={$_('examples.calculation')}
      />
    </Input>
    <br />
  </div>
{/if}