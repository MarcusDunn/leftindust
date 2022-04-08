<script lang="ts" context="module">
  export type SelectUIOption = {
    text: string;
    value: string;
  };
</script>

<script lang="ts">
  import { createEventDispatcher } from 'svelte';
  import { ListItem } from 'framework7-svelte';
  
  import InputUI from '../InputUI/InputUI.svelte';

  const dispatch = createEventDispatcher();

  export let value = '';
  export let options: SelectUIOption[];
  export let disabled = false;

  export let title = '';

</script>

<div class={`${disabled ? 'disabled' : ''}`}>
  <InputUI>
    <ListItem
      class={`ultra-select ${!title ? 'ultra-select-no-title' : ''}`}
      smartSelect
      smartSelectParams={{
        openIn: 'popover',
        closeOnSelect: true,
      }}
      {title}
      slot="content"
    >
      <select bind:value on:change={() => dispatch('change', value)}>
        {#each options as option}
          <option value={option.value}>{option.text}</option>
        {/each}
      </select>
    </ListItem>
  </InputUI>
</div>

