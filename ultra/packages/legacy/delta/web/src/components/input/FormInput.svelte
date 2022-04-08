<script lang="ts">
  import { DataType } from '../../schemas/leftindust.schema';
  
  import InputUI from '../../ui/input/InputUI/InputUI.svelte';
  import DateUI from '../../ui/input/DateUI/DateUI.svelte';
  import CheckboxItemUI from '../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';
  
  export let value: string | number | string[];
  export let type: DataType;

  export let multiSelectPossibilities: string[] = [];

</script>


{#if type === DataType.Text}
  <InputUI>
    <input type="text" placeholder="Answer" bind:value />
  </InputUI>
{:else if type === DataType.Integer || type === DataType.Float}
  <InputUI>
    <input type="number" placeholder="Answer" bind:value />
  </InputUI>
{:else if type === DataType.Date}
  {#if typeof value === 'number'}
    <DateUI bind:value />
  {/if}
{:else if type === DataType.MultiMuliSelect || type === DataType.SingleMuliSelect}
  {#if Array.isArray(value)}
    {#each multiSelectPossibilities as options, index}
      <InputUI>
        <CheckboxItemUI
          title={options}
          multiple={type === DataType.MultiMuliSelect}
          value={multiSelectPossibilities[index]}
          bind:selected={value}
          slot="content"
        />
      </InputUI>
      <p />
    {/each}
  {/if}
{/if}
