<script lang="ts">
  import type { SurveyTemplateCalculation, SurveyTemplateSection } from '@/api/server';
  import RecordSection from '../RecordSection/RecordSection.svelte';
  import type { RecordForm, RecordValues } from '../..';
  import RecordComplete from '../RecordComplete/RecordComplete.svelte';

  export let sections: SurveyTemplateSection[];
  export let calculations: SurveyTemplateCalculation[];
  
  export let values: RecordValues[];

  export let complete: boolean;

  export let forms: RecordForm[];
  
  export let currentSectionIndex: number;
</script>

{#each sections as section, index}
  <div style={`display: ${index === currentSectionIndex ? 'block' : 'none'}`}>
    <RecordSection
      {section}
      form={forms[index]}
      bind:values={values[index].inputs}
    />
  </div>
{/each}
{#if complete}
  <div>
    <RecordComplete
      {sections}
      {calculations}
      bind:values
    />
  </div>
{/if}