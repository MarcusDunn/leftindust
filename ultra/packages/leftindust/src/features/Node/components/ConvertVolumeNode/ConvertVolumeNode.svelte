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

  type units = 'ml' | 'l' | 'floz' | 'tsp' | 'tbsp' | 'cp' | 'pt' | 'qt' | 'gal';
  
  export let store: {
    from: units,
    to: units,
  } = {
    from: 'l',
    to: 'l',
  };

  // Value is volume relative to a liter
  const conversions: { [key in units]: number } = {
    'ml': 0.001,
    'l': 1.0,
    'floz': 0.0295735,
    'tsp': 0.00492892,
    'tbsp': 0.0147868,
    'cp': 0.24,
    'pt': 0.473176,
    'qt': 0.946353,
    'gal': 3.78541,
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
    <option value="ml">Milliliters</option>
    <option value="l">Liters</option>
    <option value="floz">Fluid Ounces</option>
    <option value="tsp">Teaspoons</option>
    <option value="tbsp">Tablespoons</option>
    <option value="cp">Cups</option>
    <option value="pt">Pints (US)</option>
    <option value="qt">Quarts (US)</option>
    <option value="gal">Gallons (US)</option>
  </ListInput>

  <ListInput
    label="To"
    type="select"
    bind:value={store.to}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="ml">Milliliters</option>
    <option value="l">Liters</option>
    <option value="floz">Fluid Ounces</option>
    <option value="tsp">Teaspoons</option>
    <option value="tbsp">Tablespoons</option>
    <option value="cp">Cups</option>
    <option value="pt">Pints (US)</option>
    <option value="qt">Quarts (US)</option>
    <option value="gal">Gallons (US)</option>
  </ListInput>
</List>