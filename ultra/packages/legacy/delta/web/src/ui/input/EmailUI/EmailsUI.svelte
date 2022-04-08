<script lang="ts">
  import type { EmailInput } from '../../../schemas/leftindust.schema';
  
  import AddUI from '../AddUI/AddUI.svelte';
  import DeleteButtonUI from '../../button/DeleteButtonUI/DeleteButtonUI.svelte';
  import EmailUI from './EmailUI.svelte';

  export let title = 'Add Email';
  export let value: Partial<EmailInput>[] = [];
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <EmailUI bind:value={value[index]} />
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
      email: '',
    }];
  }}
/>