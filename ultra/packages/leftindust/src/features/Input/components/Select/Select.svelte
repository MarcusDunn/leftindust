<script lang="ts">
  import type { SelectOption } from '.';
  import { createEventDispatcher } from 'svelte';
  import { ListItem } from 'framework7-svelte';
  
  import Input from '../../Input.svelte';

  import './Select.scss';
  
  const dispatch = createEventDispatcher();

  export let value: string | number = '';
  export let options: SelectOption[];
  export let disabled = false;

  export let title = '';
  export let placeholder = '';
</script>

{#key options}
  <div class={`${disabled ? 'disabled' : ''}`}>
    <Input {title}>
      <ListItem
        class="input-select"
        smartSelect
        smartSelectParams={{
          openIn: 'popover',
          closeOnSelect: true,
        }}
        title=""
        slot="content"
        {placeholder}
      >
        <select
          bind:value
          on:change={() => dispatch('change', value)}
        >
          {#each options as option}
            <option value={option.value}>{option.text}</option>
          {/each}
        </select>
      </ListItem>
    </Input>
  </div>
{/key}