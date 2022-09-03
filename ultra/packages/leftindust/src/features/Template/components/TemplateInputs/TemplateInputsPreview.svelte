<script lang="ts">
  import { SurveyTemplateInputType, TemplateInputUploadType } from '@/api/server';
  import type { Template } from '../..';

  import { _ } from '@/language';
  
  import Input from '@/features/Input/Input.svelte';
  import Date from '@/features/Input/components/Date/Date.svelte';
  import Add from '@/features/Input/components/Add/Add.svelte';
  import Checkbox from '@/features/Input/components/Checkbox/Checkbox.svelte';

  export let inputs: Template['sections'][number]['inputs'];

  const getUploadLabel = (accept?: TemplateInputUploadType, multiple?: boolean) => $_('generics.uploadWithLabel', {
    values: {
      label: (() => {
        if (accept === TemplateInputUploadType.Documents) return multiple ? $_('generics.documents') : $_('generics.document');
        if (accept === TemplateInputUploadType.Images) return multiple ? $_('generics.images') : $_('generics.image');
        return multiple ? $_('generics.files') : $_('generics.file');
      })(),
    },
  });

  $: inputs = inputs.map((input) => {
    let value = input.value;
    if (input.type === SurveyTemplateInputType.Text && typeof value === 'number') value = value.toString();
    if (input.type === SurveyTemplateInputType.Number && typeof value === 'string') value = parseInt(value, 10);
    if (
      (input.type === SurveyTemplateInputType.SingleSelect
        || input.type === SurveyTemplateInputType.MultiSelect)
        && !Array.isArray(value)
    ) {
      value = [''];
    }

    return {
      ...input,
      value,
    };
  });
</script>

{#each inputs as {
  type,
  label,
  value,
  options,
  placeholder,
  uploadAccept,
  uploadMultiple,
}}
  {#if type === SurveyTemplateInputType.Title}
    <h4 style="padding-top: 20px">{label}</h4>
  {:else}
    <div style="margin-bottom: 20px;">
      {#if type === SurveyTemplateInputType.Text}
        <Input title={label} clear>
          <input type="text" {placeholder} bind:value />
        </Input>
      {:else if type === SurveyTemplateInputType.Number}
        <Input title={label} clear>
          <input type="number" {placeholder} bind:value />
        </Input>
      {:else if type === SurveyTemplateInputType.Date}
        <Date title={label} {placeholder} bind:value />
      {:else if type === SurveyTemplateInputType.Paragraph}
        <Input title={label} clear>
          <textarea {placeholder} bind:value />
        </Input>
      {:else if type === SurveyTemplateInputType.Upload}
        <Add title={label} placeholder={placeholder || getUploadLabel(uploadAccept, uploadMultiple)} />
      {:else if type === SurveyTemplateInputType.MultiSelect || type === SurveyTemplateInputType.SingleSelect}
        {#if options}
          <div style="margin-top: 10px; margin-bottom: -10px; font-size: 14px">{label}</div>
          {#each options as option, index}
            {#if Array.isArray(value)}
              <Checkbox
                title={option}
                multiple={type === SurveyTemplateInputType.MultiSelect}
                slot="content"
                bind:selected={value}
                value={options[index]}
              />
            {/if}
          {/each}
        {/if}
      {/if}
    </div>
  {/if}
{/each}