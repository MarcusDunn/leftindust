<script lang="ts">
  import type { TemplateInput } from '../..';
  import { TemplateInputUniqueIndex } from '../../store';
  import Input from '@/features/Input/Input.svelte';
  import TemplateInputs from '../TemplateInputs/TemplateInputs.svelte';

  import { _ } from 'svelte-i18n';

  export let title: string;
  export let subtitle: string | undefined = undefined;
  export let inputs: TemplateInput[] | undefined = undefined;

  export let index: number | undefined = undefined;
</script>

<Input
  title={$_('generics.title')}
  clear
  style="width: 100%"
>
  <input
    type="text"
    placeholder={
      typeof index === 'number' ?
        // eslint has a brain tumor
        // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
        $_('generics.sectionIndexed', { values: { number: index + 1 } })
        : 'Eg. Blood Sample'
      }
    bind:value={title}
  />
</Input>
<br />
<Input
  title={$_('generics.description')}
  clear
>
  <input type="text" placeholder="Additional Text" bind:value={subtitle} />
</Input>

{#if inputs}
  <br />
  <br />
  <TemplateInputs bind:inputs={inputs} bind:globalIndex={$TemplateInputUniqueIndex} />
{/if}