<script lang="ts">
  import { SurveyTemplateInputType, TemplateInputUploadType, type SurveyTemplateInput } from '@/api/server';

  import Add from '@/features/Input/components/Add/Add.svelte';
  import Checkbox from '@/features/Input/components/Checkbox/Checkbox.svelte';
  import Date from '@/features/Input/components/Date/Date.svelte';

  import Input from '@/features/Input/Input.svelte';
  import InputError from '@/features/Input/InputError.svelte';

  import { _ } from '@/language';
  import type { Writable } from 'svelte/store';

  const getUploadLabel = (accept?: TemplateInputUploadType, multiple?: boolean) => $_('generics.uploadWithLabel', {
    values: {
      label: (() => {
        if (accept === TemplateInputUploadType.Documents) return multiple ? $_('generics.documents') : $_('generics.document');
        if (accept === TemplateInputUploadType.Images) return multiple ? $_('generics.images') : $_('generics.image');
        return multiple ? $_('generics.files') : $_('generics.file');
      })(),
    },
  });

  export let input: SurveyTemplateInput;
  export let value: string | number | boolean | string[] | undefined;
  export let id: number;

  const { type, label, options, placeholder, uploadAccept, uploadMultiple } = input;

  export let data: Omit<Writable<Record<string, unknown>>, 'subscribe'> & {
    subscribe(subscriber: (values: {
      [x: string]: any;
    }) => any): () => void;
  } & Record<string, any>;
  export let errors: Writable<Record<string, string>>;

  $: $data[id] = value;

  $: console.log($errors);

</script>


{#if type === SurveyTemplateInputType.Title}
  <h4 style="padding-top: 20px">{label}</h4>
{:else}
  <div style="margin-bottom: 20px;">
    {#if type === SurveyTemplateInputType.Text}
      <Input title={label} clear error={$errors[id]}>
        <input type="text" {placeholder} bind:value />
      </Input>
    {:else if type === SurveyTemplateInputType.Number}
      <Input title={label} clear error={$errors[id]}>
        <input type="number" {placeholder} bind:value />
      </Input>
    {:else if type === SurveyTemplateInputType.Date}
      {#if typeof value === 'number' || typeof value === 'undefined'}
        <Date title={label} {placeholder} bind:value />
      {/if}
    {:else if type === SurveyTemplateInputType.Paragraph}
      <Input title={label} clear error={$errors[id]}>
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
        {#if $errors[id]}
          {#if Array.isArray($errors[id])}
            {#each $errors[id] as e}
              {#if typeof e === 'string'}
                <InputError message={e} />
              {/if}
            {/each}
          {:else}
            <InputError message={$errors[id]} />
          {/if}
        {/if}
      {/if}
    {/if}
  </div>
{/if}