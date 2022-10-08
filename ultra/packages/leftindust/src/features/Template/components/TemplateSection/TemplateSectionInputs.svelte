<script lang="ts">
  import type { templateForm, Template as TemplateType } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import TemplateInputs from '../TemplateInputs/TemplateInputs.svelte';

  import { _ } from 'svelte-i18n';

  export let title: string;
  export let subtitle: string | null | undefined = undefined;
  export let inputs: TemplateType['sections'][number]['inputs'] | undefined = undefined;

  export let globalIndex: number | undefined = undefined;

  export let index: number | undefined = undefined;

  export let errors: ReturnType<typeof templateForm>['errors'];
  export let data: ReturnType<typeof templateForm>['data'];

  $: if (typeof index !== 'number') $data.sections[0].title = title;
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

{#if inputs && globalIndex}
  <br />
  <br />
  <TemplateInputs
    bind:inputs={inputs}
    bind:globalIndex
    {errors}
    sectionIndex={index}
  />
{/if}