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

  type units = 'ms' | 's' | 'min' | 'hr' | 'day' | 'wk' | 'mo' | 'yr';
  
  export let store: {
    from: units,
    to: units,
  } = {
    from: 's',
    to: 's',
  };

  // Value is time relative to a minute
  const conversions: { [key in units]: number } = {
    'ms': 1.0/60000,
    's': 1.0/60,
    'min' : 1.0,
    'hr': 60.0,
    'day': 1440.0,
    'wk': 10080.0,
    'mo': 43800.0,
    'yr': 525600.0,
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
    <option value="ms">Milliseconds</option>
    <option value="s">Seconds</option>
    <option value="min">Minutes</option>
    <option value="hr">Hours</option>
    <option value="day">Days</option>
    <option value="wk">Weeks</option>
    <option value="mo">Months</option>
    <option value="yr">Years</option>
  </ListInput>

  <ListInput
    label="To"
    type="select"
    bind:value={store.to}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="ms">Milliseconds</option>
    <option value="s">Seconds</option>
    <option value="min">Minutes</option>
    <option value="hr">Hours</option>
    <option value="day">Days</option>
    <option value="wk">Weeks</option>
    <option value="mo">Months</option>
    <option value="yr">Years</option>
  </ListInput>
</List>