<script lang="ts">
  import type { TemplateInput } from '../..';
  import { TemplateInputType, TemplateInputUploadType } from '../..';

  import { _ } from '@/language';
  
  import Input from '@/features/Input/Input.svelte';
  import Date from '@/features/Input/components/Date/DatePicker.svelte';
  import Add from '@/features/Input/components/Add/Add.svelte';
  import Checkbox from '@/features/Input/components/Checkbox/Checkbox.svelte';

  export let inputs: TemplateInput[];

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
    if (input.type === TemplateInputType.Text && typeof value === 'number') value = value.toString();
    if (input.type === TemplateInputType.Number && typeof value === 'string') value = parseInt(value, 10);

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
  {#if type === TemplateInputType.Title}
    <h4 style="padding-top: 20px">{label}</h4>
  {:else}
    <div style="margin-bottom: 20px;">
      {#if type === TemplateInputType.Text}
        <Input title={label} clear>
          <input type="text" {placeholder} bind:value />
        </Input>
      {:else if type === TemplateInputType.Number}
        <Input title={label} clear>
          <input type="number" {placeholder} bind:value />
        </Input>
      {:else if type === TemplateInputType.Date}
        {#if typeof value === 'number' || typeof value === 'undefined'}
          <Date title={label} {placeholder} bind:value />
        {/if}
      {:else if type === TemplateInputType.Paragraph}
        <Input title={label} clear>
          <textarea {placeholder} bind:value />
        </Input>
      {:else if type === TemplateInputType.Upload}
        <Add title={label} placeholder={placeholder || getUploadLabel(uploadAccept, uploadMultiple)} />
      {:else if type === TemplateInputType.MultiSelect || type === TemplateInputType.SingleSelect}
        {#if options}
          <div style="margin-top: 10px; margin-bottom: -10px; font-size: 14px">{label}</div>
          {#each options as option}
            <Checkbox
              title={option}
              multiple={type === TemplateInputType.MultiSelect}
              slot="content"
            />
          {/each}
        {/if}
      {/if}
    </div>
  {/if}
{/each}