<script lang="ts">
  import type { SelectOption } from '.';
  import { createEventDispatcher } from 'svelte';
  import { ListItem } from 'framework7-svelte';
  
  import Input from '../../Input.svelte';

  import './Select.scss';
  import InputError from '../../InputError.svelte';
  
  const dispatch = createEventDispatcher();

  export let value: string | number = '';
  export let options: SelectOption[];
  export let disabled = false;

  export let title = '';
  export let placeholder = '';

  export let name: string | undefined = undefined;

  export let error: string[] | string | null | undefined = undefined;
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
        title={placeholder}
        slot="content"
      >
        <select
          bind:value
          on:change={() => dispatch('change', value)}
          {name}
        >
          {#each options as option}
            <option value={option.value}>{option.text}</option>
          {/each}
        </select>
      </ListItem>
    </Input>
  </div>
{/key}

{#if error}
  {#if Array.isArray(error)}
    {#each error as e}
      <InputError message={e} />
    {/each}
  {:else}
    <InputError message={error} />
  {/if}
{/if}