<script lang="ts">
  import  { templateForm, type TemplateInput } from '../..';
  import { TemplateInputUniqueIndex } from '../../store';
  import Input from '@/features/Input/Input.svelte';
  import TemplateInputs from '../TemplateInputs/TemplateInputs.svelte';

  import { _ } from 'svelte-i18n';

  export let title: string;
  export let subtitle: string | undefined = undefined;
  export let inputs: TemplateInput[] | undefined = undefined;

  export let index: number | undefined = undefined;

  const error = templateForm().errors;
  export let errors: typeof error;
</script>

<Input
  title={$_('generics.title')}
  clear
  style="width: 100%"
  error={typeof index === 'number' ? $errors.sections?.[index]?.title : $errors?.title}
>
  <input
    type="text"
    name={typeof index === 'number' ? `sections.${index}.title` : 'title'}
    placeholder={typeof index === 'number' ?
      $_('generics.sectionIndexed', { values: { number: index + 1 } })
      : 'Eg. Blood Sample'}
    bind:value={title}
  />
</Input>
<br />
<Input
  title={$_('generics.description')}
  clear
  error={typeof index === 'number' ? $errors.sections?.[index]?.subtitle : $errors?.subtitle}
>
  <input
    type="text"
    name={typeof index === 'number' ? `sections.${index}.subtitle` : 'subtitle'}
    placeholder="Additional Text"
    bind:value={subtitle}
  />
</Input>

{#if inputs}
  <br />
  <br />
  <TemplateInputs
    bind:inputs={inputs}
    bind:globalIndex={$TemplateInputUniqueIndex}
    {errors}
    sectionIndex={index}
  />
{/if}