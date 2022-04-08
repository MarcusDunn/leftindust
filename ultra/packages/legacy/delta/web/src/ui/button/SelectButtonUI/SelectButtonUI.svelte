<script lang="ts" context="module">
  import type { Framework7Icon } from '../../../modules/UIModule';

  export type SelectButtonUIOption = {
    text: string;
    value: string;
    icon?: Framework7Icon;
  };
</script>

<script lang="ts">
  import { Link, Icon } from 'framework7-svelte';

  export let value: string;
  export let options: SelectButtonUIOption[];

  let icons = options.some((option) => option.icon);
  
  $: selected = options[options.findIndex((option) => option.value === value)];

</script>

<Link
  class={`ultra-select-button ${icons ? 'ultra-select-button-icons' : ''}`}
  smartSelect
  smartSelectParams={{
    openIn: 'popover',
    closeOnSelect: true,
  }}
>
  {#if icons}
    <div class="ultra-select-button-selected-icon">
      <i class="f7-icons icon">{selected.icon?.f7}</i>
    </div>
  {:else}
    <div class="ultra-select-button-selected">{selected?.text}</div>
  {/if}
  <div class="ultra-select-button-icon">
    <Icon f7="chevron_up_chevron_down" />
  </div>
  <select bind:value={value}>
    {#each options as option}
      <option value={option.value}>{option.text}</option>
    {/each}
  </select>
</Link>
