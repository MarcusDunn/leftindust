<script lang="ts">
  // This component was created as compensation for a bug in svelte
  // https://github.com/sveltejs/svelte/issues/6341
  
  import Input from '@/features/Input/Input.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  
  import { _ } from 'svelte-i18n';
  import { templateForm } from '../..';

  import './TemplateInputSelect.scss';

  const error = templateForm().errors;
  export let errors: typeof error;

  export let index: number;
  export let inputIndex: number;
  export let sectionIndex: number;

  export let options: string[];

  export let title: string | undefined = undefined;
  $: console.log($errors?.sections?.[sectionIndex]?.inputs?.[inputIndex]?.options);
</script>


<div class="templates-input_select">
  <div class="templates-input_select-input">
    <Input
      {title}
      clear
    >
      <input
        type="text"
        placeholder={$_('generics.optionIndexed', { values: { index: index + 1 } })}
        bind:value={options[index]}
        name={`sections.${sectionIndex}.inputs.${inputIndex}.options.${index}`}
      />
    </Input>
  </div>
  {#if options.length > 1}
    <div class="templates-input_select-input-delete" style={`margin-top: ${title ? '28px' : '10px'}`}>
      <MenuButton
        title={$_('generics.delete')}
        icon={{
          f7: 'minus_circle_fill',
          color: 'red',
        }}
        on:click={() => (options = options.filter((_, i) => i !== index))}
      />
    </div>
  {/if}
</div>