<script lang="ts">
  import type { AddressInput } from '../../../schemas/leftindust.schema';

  import { Country } from '../../../schemas/leftindust.schema';
  
  import AddUI from '../AddUI/AddUI.svelte';
  import DeleteButtonUI from '../../button/DeleteButtonUI/DeleteButtonUI.svelte';
  import AddressUI from './AddressUI.svelte';

  export let title = 'Add Address';
  export let value: Partial<AddressInput>[] = [];
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <AddressUI bind:value={value[index]} />
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
      addressType: undefined,
      address: '',
      city: '',
      country: Country.Canada,
      province: '',
      postalCode: '',
    }];
  }}
/>