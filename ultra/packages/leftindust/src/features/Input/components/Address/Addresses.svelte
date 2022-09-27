<script lang="ts">
  import type { CreateAddress } from '@/api/server';

  import { Countries } from '@/api/server';

  import Add from '../Add/Add.svelte';
  import Delete from '../Delete/Delete.svelte';
  import Address from './Address.svelte';

  export let title = 'Add Address';
  export let value: Partial<CreateAddress>[] = [];
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Address bind:value={value[index]} />
    {#if value.length > 1 || ! required}
      <Delete on:delete={() => {
        value = value.filter((_, i) => i !== index);
      }} />
    {/if}
  </div>
{/each}

<Add 
  {title}
  on:click={() => {
    value = [...value, {
      addressType: undefined,
      address: '',
      city: '',
      country: Countries.Canada,
      province: '',
      postalCode: '',
    }]
  }}
/>