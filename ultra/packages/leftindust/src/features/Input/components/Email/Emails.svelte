<script lang="ts">
  import type { CreateEmail } from '@/api/server';

  import Add from '../Add/Add.svelte';
  import Delete from '../Delete/Delete.svelte';
  import Email from './Email.svelte';

  export let title = 'Add Email';
  export let value: Partial<CreateEmail>[] = [];
  export let required = false;

  export let name: string | undefined = undefined;

  export let errors: {
    type: string[] | string | null | undefined,
    email: string[] | string | null | undefined
  }[] | any | undefined = undefined;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Email
      name={name ? `${name}[${index}]` : undefined}
      error={errors?.[index]}
      bind:value={value[index]}
    />
    {#if value.length > 1 || !required}
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
      type: undefined,
      email: '',
    }];
  }}
/>