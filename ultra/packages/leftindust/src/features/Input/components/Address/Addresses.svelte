<script lang="ts">
  import type { CreateAddress } from '@/api/server';

  import { Countries } from '@/api/server';

  import Add from '../Add/Add.svelte';
  import Delete from '../Delete/Delete.svelte';
  import Address from './Address.svelte';

  export let title = 'Add Address';
  export let value: Partial<CreateAddress>[] = [];
  export let required = false;

  export let name: string | undefined = undefined;

  export let errors: {
    address: string[] | string | null | undefined,
    addressType: string[] | string | null | undefined,
    city: string[] | string | null | undefined,
    country: string[] | string | null | undefined,
    postalCode: string[] | string | null | undefined,
    province: string[] | string | null | undefined,
  }[] | any | undefined = undefined;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Address
      name={name ? `${name}[${index}]` : undefined}
      error={errors?.[index]}
      bind:value={value[index]}
    />
    {#if value.length > 1 || ! required}
      <div style="margin-top: 6px; margin-left: 10px">
        <Delete on:delete={() => {
          value = value.filter((_, i) => i !== index);
        }} />
      </div>
    {/if}
  </div>
  <p />
{/each}

<Add 
  placeholder={title}
  on:click={() => {
    value = [...value, {
      addressType: undefined,
      address: '',
      city: '',
      country: Countries.Canada,
      province: '',
      postalCode: '',
    }];
  }}
/>