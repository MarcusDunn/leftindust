<script lang="ts">
  import type { CreatePhone } from '@/api/server';

  import Add from '../Add/Add.svelte';
  import Delete from '../Delete/Delete.svelte';
  import Phone from './Phone.svelte';

  export let value: Partial<CreatePhone>[] = [];
  export let title = 'Add Phone';
  export let required = false;

  export let name: string | undefined = undefined;
  
  // Because felte sucks
  export let errors: {
    type: string[] | string | null | undefined,
    number: string[] | string | null | undefined
  }[] | any | undefined = undefined;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Phone
      name={name ? `${name}[${index}]` : undefined}
      bind:value={value[index]}
      error={errors?.[index]}
    />
    {#if value.length > 1 || !required}
      <div style="margin-top: 6px; margin-left: 10px">
        <Delete
          on:delete={() => {
            value = value.filter((_, i) => i !== index);
          }}
        />
      </div>
    {/if}
  </div>
  <p />
{/each}

<Add 
  placeholder={title}
  on:click={() => {
    value = [...value, {
      type: undefined,
      number: '',
    }];
  }}
/>