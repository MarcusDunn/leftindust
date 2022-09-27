<script lang="ts">
  import type { CreateEmail } from "@/api/server";

  import Add from "../Add/Add.svelte";
  import Delete from "../Delete/Delete.svelte";
  import Email from "./Email.svelte";

  export let title = "Add Email";
  export let value: Partial<CreateEmail>[] = [];
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Email bind:value={value[index]} />
    {#if value.length > 1 || !required}
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
      type: undefined,
      email: '',
    }];
  }}
/>