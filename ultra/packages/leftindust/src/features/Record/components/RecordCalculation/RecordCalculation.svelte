<style lang="scss" global>
  @import './RecordCalculation.scss';
</style>

<script lang="ts">
  import type { SurveyTemplateCalculation } from '@/api/server';
  import { templateCalculationNodes } from '@/features/Template';
  import { Preloader } from 'framework7-svelte';
  import { Compute } from 'function-junctions';
  import type { EditorState } from 'function-junctions/types';
  import { writable, type Writable } from 'svelte/store';
  import RecordCalculationTags from './RecordCalculationTags.svelte';

  export let calculation: SurveyTemplateCalculation;
  export let inputs: Record<string, { type: string; value: Writable<unknown> }> | undefined = undefined;

  let state: EditorState = JSON.parse(calculation.calculation ?? '');

  let outputs = {
    Value: {
      type: '',
      value: writable(),
    },
  };

  const { value } = outputs.Value;
</script>

{#if inputs}
  <Compute
    nodes={templateCalculationNodes}
    {state}
    {inputs}
    {outputs}
  />
{/if}

<div class="record-record_calculation" on:click>
  <div class="record-record_calculation_text">
    <div class="record-record_calculation_type">{calculation.inputType}</div>
    <div class="record-record_calculation_title">{calculation.label}</div>
    {#if state.nodes}
      <RecordCalculationTags nodes={state.nodes} />
    {/if}
  </div>
  {#if inputs}
    <div class="flex-grow" />
    <div class="record-record_calculation_value">
      {#if typeof $value === 'undefined'}
        <Preloader />
      {:else if typeof $value === 'number'}
        <div class="record-record_calculation_number">{Math.round($value)}</div>
      {/if}
    </div>
  {/if}
</div>