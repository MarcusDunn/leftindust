<script lang="ts">
  import type { SurveyTemplateInput } from '@/api/server';
  import type { Writable } from 'svelte/store';
  import type { RecordValues } from '../..';
  import RecordInput from '../RecordInput/RecordInput.svelte';

  export let inputs: SurveyTemplateInput[];
  export let values: RecordValues['inputs'];

  export let errors: Writable<Record<string, string>>;
  export let data: Omit<Writable<Record<string, unknown>>, 'subscribe'> & {
    subscribe(subscriber: (values: {
      [x: string]: any;
    }) => any): () => void;
  } & Record<string, any>;
</script>

{#each inputs as input, index}
  <RecordInput
    {input}
    {data}
    id={input.calculationId}
    {errors}
    bind:value={values[index].value}
  />
{/each}