<style lang="scss" global>
  @import './RecordCalculation.scss';
</style>

<script lang="ts">
  import type { SurveyTemplateCalculation } from '@/api/server';
  import { calculationNodes } from '@/features/Nodes';
  import { Preloader } from 'framework7-svelte';
  import { Compute } from 'function-junctions';
  import type { EditorState } from 'function-junctions/types';
  import { writable, type Writable } from 'svelte/store';

  export let calculation: SurveyTemplateCalculation;
  export let inputs: Record<string, { type: string; value: Writable<unknown> }>;

  let state: EditorState = JSON.parse(calculation.calculation ?? '');

  let outputs = {
    Value: {
      type: '',
      value: writable(),
    },
  };

  const { value } = outputs.Value;
</script>

<Compute
  nodes={calculationNodes}
  {state}
  {inputs}
  {outputs}
/>

<div class="record-record_calculation">
  <div class="record-record_calculation_text">
    <div class="record-record_calculation_type">{calculation.inputType}</div>
    <div class="record-record_calculation_title">{calculation.label}</div>
  </div>
  <div class="flex-grow" />
  <div class="record-record_calculation_value">
    {#if typeof $value === 'undefined'}
      <Preloader />
    {:else if typeof $value === 'number'}
      <div class="record-record_calculation_number">{Math.round($value)}</div>
    {/if}
  </div>
</div>