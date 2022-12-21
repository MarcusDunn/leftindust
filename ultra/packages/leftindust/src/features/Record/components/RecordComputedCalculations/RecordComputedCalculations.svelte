<script lang="ts">
  import { writable, type Writable } from 'svelte/store';
  import type { SurveyTemplateCalculation, SurveyTemplateSection } from '@/api/server';
  import type { RecordValues } from '../..';
  import RecordCalculation from '../RecordCalculation/RecordCalculation.svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';
  import { templateCalculationNodes } from '@/features/Template';

  export let values: RecordValues;

  export let calculations: SurveyTemplateCalculation[];
  export let sections: SurveyTemplateSection[];

  let inputs: Record<string, { type: string; value: Writable<unknown> }> = {};

  sections.forEach(({ inputs: templateInputs }, sectionIndex) => {
    templateInputs.forEach((input, inputIndex) => {
      inputs[input.calculationId] = {
        type: input.type,
        value: writable(values[sectionIndex].inputs[inputIndex].value),
      };
    });
  });

  const mappedCalculations = calculations.map((calculation) => ({
    ...calculation,
    calculationModalOpen: false,
    editor: undefined,
  })) ?? [];
</script>

{#each mappedCalculations as calculation}
  {#if !calculation.showOnComplete}
    <NodesModal
      nodes={templateCalculationNodes}
      state={JSON.parse(calculation.calculation ?? '{}')}
      bind:open={calculation.calculationModalOpen}
      bind:editor={calculation.editor}
      editable={false}
      {inputs}
    />
    <div style="margin-bottom: 10px">
      <RecordCalculation
        {calculation}
        {inputs}
        on:click={() => calculation.calculationModalOpen = true}
      />
    </div>
  {/if}
{/each}