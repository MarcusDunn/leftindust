<script lang="ts">
  import type { CreateCompleteSurveySection, SurveyTemplateSection } from '@/api/server';
  import RecordSection from '../RecordSection/RecordSection.svelte';
  import type { RecordForm, RecordValues } from '../..';

  export let sections: SurveyTemplateSection[];
  export let values: RecordValues[];

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