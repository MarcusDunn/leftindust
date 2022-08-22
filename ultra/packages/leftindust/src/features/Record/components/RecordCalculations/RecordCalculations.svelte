<script lang="ts">
  import { writable, type Writable } from 'svelte/store';
  import type { SurveyTemplateCalculation, SurveyTemplateSection } from '@/api/server';
  import type { RecordValues } from '../..';
  import RecordCalculation from '../RecordCalculation/RecordCalculation.svelte';

  export let values: RecordValues[];

  export let calculations: SurveyTemplateCalculation[];
  export let sections: SurveyTemplateSection[];

  let inputs: Record<string, { type: string; value: Writable<unknown> }> = {};

  sections.forEach(({ inputs: templateInputs }, sectionIndex) => {
    templateInputs.forEach((input, inputIndex) => {
      console.log(input);
      inputs[input.calculationId] = {
        type: input.type,
        value: writable(values[sectionIndex].inputs[inputIndex].value),
      };
    });
  });
</script>

{#each calculations as calculation}
  {#if !calculation.showOnComplete}
    <div style="margin-bottom: 10px">
      <RecordCalculation
        {calculation}
        {inputs}
      />
    </div>
  {/if}
{/each}