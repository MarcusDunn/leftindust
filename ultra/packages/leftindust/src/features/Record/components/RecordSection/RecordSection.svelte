<script lang="ts">
  import { Block } from 'framework7-svelte';
  import type { SurveyTemplateSection } from '@/api/server';
  import RecordInputs from '../RecordInputs/RecordInputs.svelte';
  import type { RecordForm, RecordValues } from '../..';

  export let section: SurveyTemplateSection;
  export let values: RecordValues['inputs'] | undefined;

  export let form: RecordForm;

  const { form: felteForm } = form;

  const { form: consumableForm, errors, data } = felteForm;
</script>

<form bind:this={form.ref} use:consumableForm>
  <Block class="no-margin-top" style="margin-bottom: 100px;padding: 0 25px;padding-top: 5px;">
    <h2>{section.title}</h2>
    <p>{section.subtitle ?? ''}</p>
    <br />
    {#if values}
      <RecordInputs
        bind:inputs={section.inputs}
        {data}
        {errors}
        bind:values
      />
    {/if}
  </Block>
</form>