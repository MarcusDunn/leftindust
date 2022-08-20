<script lang="ts">
  import type { SurveyTemplateInput } from '@/api/server';
  import type { RecordValues } from '../..';
  import RecordInput from '../RecordInput/RecordInput.svelte';

  export let inputs: SurveyTemplateInput[];
  export let values: RecordValues['inputs'];

  export let errors: Record<string, string>;
</script>

{#each inputs as input, index}
  <RecordInput
    {input}
    id={input.calculationId}
    {errors}
    bind:value={values[index].value}
  />
{/each}