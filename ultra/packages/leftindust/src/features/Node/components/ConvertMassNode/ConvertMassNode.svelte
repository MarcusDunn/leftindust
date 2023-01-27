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

  type units = 'mg' | 'g' | 'kg' | 'mton' | 'oz' | 'lbs' | 'uston';
  
  export let store: {
    from: units,
    to: units,
  } = {
    from: 'kg',
    to: 'kg',
  };

  // Value is mass relative to a kilogram
  const conversions: { [key in units]: number } = {
    'mg': 1.0e-6,
    'g': 0.001,
    'kg': 1.0,
    'mton': 1000.0,
    'oz': 0.0283495,
    'lbs': 0.453592,
    'uston': 907.185,
  };
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = $Input * conversions[store.from] / conversions[store.to];
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
    <option value="mg">Milligrams</option>
    <option value="g">Grams</option>
    <option value="kg">Kilograms</option>
    <option value="oz">Ounces</option>
    <option value="lbs">Pounds</option>
    <option value="uston">Tons (US)</option>
    <option value="mton">Metric tons</option>
  </ListInput>

  <ListInput
    label="To"
    type="select"
    bind:value={store.to}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="mg">Milligrams</option>
    <option value="g">Grams</option>
    <option value="kg">Kilograms</option>
    <option value="oz">Ounces</option>
    <option value="lbs">Pounds</option>
    <option value="uston">Tons (US)</option>
    <option value="mton">Metric tons</option>
  </ListInput>
</List>