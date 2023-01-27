<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  
  import { List, ListInput } from 'framework7-svelte';
  import { getDisplayOutput } from '../nodeUtils';
  
  export let inputs: InputSockets<{
    Input: InputSocket<number>;
  }>;
  
  export let outputs: OutputSockets<{
    Output: OutputSocket<number>;
  }>;

  type units = 'F' | 'C' | 'K';
  
  export let store: {
    from: units,
    to: units,
  } = {
    from: 'C',
    to: 'C',
  };
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = (() => {
    const input = $Input;

    if (store.from == store.to) return input;

    // No need for clever optimizations
    if (store.from == 'F') {
      if (store.to == 'C') return (input - 32) / 1.8;
      if (store.to == 'K') return (input + 459.67) / 1.8;
    }

    if (store.from == 'C') {
      if (store.to == 'F') return (input * 1.8) + 32;
      if (store.to == 'K') return input + 273.15;
    }

    if (store.from == 'K') {
      if (store.to == 'F') return (input * 1.8) - 459.67;
      if (store.to == 'C') return input - 273.15;
    }

    return NaN;
  })();

  $: displayOutput = getDisplayOutput($Output);
  $: inputs, store;
</script>

<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="From"
    type="select"
    bind:value={store.from}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="F">Fahrenheit</option>
    <option value="C">Celsius</option>
    <option value="K">Kelvin</option>
  </ListInput>

  <ListInput
    label="To"
    type="select"
    bind:value={store.to}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="F">Fahrenheit</option>
    <option value="C">Celsius</option>
    <option value="K">Kelvin</option>
  </ListInput>
</List>