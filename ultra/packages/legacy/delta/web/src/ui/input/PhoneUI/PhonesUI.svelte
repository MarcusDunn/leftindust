<script lang="ts">
  import type { PhoneInput } from '../../../schemas/leftindust.schema';
  
  import AddUI from '../AddUI/AddUI.svelte';
  import DeleteButtonUI from '../../button/DeleteButtonUI/DeleteButtonUI.svelte';
  import PhoneUI from './PhoneUI.svelte';

  export let value: Partial<PhoneInput>[] = [];
  export let title = 'Add Phone';
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <PhoneUI bind:value={value[index]} />
    {#if value.length > 1 || !required}
      <DeleteButtonUI on:delete={() => {
        value = value.filter((_, i) => i !== index);
      }} />
    {/if}
  </div>
  <p />
{/each}

<AddUI
  {title}
  on:click={() => {
    value = [...value, {
      type: undefined,
      number: '',
    }];
  }}
/>