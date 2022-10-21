<script lang="ts">
  import type { SelectButtonOption } from '.';
  import { Link, Icon } from 'framework7-svelte';

  import './SelectButton.scss';

  export let value: string;
  export let options: SelectButtonOption[];

  export let style = '';

  let icons = options.some((option) => option.icon);
  $: selected = options[options.findIndex((option) => option.value === value)];
</script>

<Link
  class={`ui-select-button ${icons ? 'ui-select-button-icons' : ''}`}
  smartSelect
  smartSelectParams={{
    openIn: 'popover',
    closeOnSelect: true,
  }}
  {style}
  on:click
>
  {#if icons}
    <div class="ui-select-button-selected-icon">
      <i class="f7-icons icon">{selected.icon?.f7}</i>
    </div>
  {:else}
    <div class="ui-select-button-selected">{selected?.text}</div>
  {/if}
  <div class="ui-select-button-icon">
    <Icon f7="chevron_up_chevron_down" />
  </div>
  <select bind:value={value}>
    {#each options as option}
      <option value={option.value}>{option.text}</option>
    {/each}
  </select>
</Link>
