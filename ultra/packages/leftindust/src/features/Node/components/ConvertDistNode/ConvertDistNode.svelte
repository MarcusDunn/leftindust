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

  type units = 'mm' | 'cm' | 'm' | 'km' | 'in' | 'ft' | 'mi';
  
  export let store: {
    from: units,
    to: units,
  } = {
    from: 'ft',
    to: 'm',
  };

  // Value is length relative to a meter
  const conversions: { [key in units]: number } = {
    'mm': 0.001,
    'cm': 0.01,
    'm' : 1.0,
    'km': 1000,
    'in': 0.0254,
    'ft': 0.3048,
    'mi': 1609.34,
  };
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = (() => {
    const input = $Input;

    return input * conversions[store.from] / conversions[store.to]; 
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
    <option value="mm">Millimeters</option>
    <option value="cm">Centimeters</option>
    <option value="m">Meters</option>
    <option value="km">Kilometers</option>
    <option value="in">Inches</option>
    <option value="ft">Feet</option>
    <option value="mi">Miles</option>
  </ListInput>

  <ListInput
    label="To"
    type="select"
    bind:value={store.to}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="mm">Millimeters</option>
    <option value="cm">Centimeters</option>
    <option value="m">Meters</option>
    <option value="km">Kilometers</option>
    <option value="in">Inches</option>
    <option value="ft">Feet</option>
    <option value="mi">Miles</option>
  </ListInput>
</List>