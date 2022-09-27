<script lang="ts">
  import type { CreatePhone } from "@/api/server";

  import Add from "../Add/Add.svelte";
  import Delete from "../Delete/Delete.svelte";
  import Phone from "./Phone.svelte";

  export let value: Partial<CreatePhone>[] = [];
  export let title = 'Add Phone';
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <Phone bind:value={value[index]} />
    {#if value.length > 1 || !required}
      <Delete on:delete={() => {
        value = value.filter((_, i) => i !== index);
      }}/>
    {/if}
  </div>
{/each}

<Add 
  {title}
  on:click={() => {
    value = [...value, {
      type: undefined,
      number: '',
    }]
  }}
/>