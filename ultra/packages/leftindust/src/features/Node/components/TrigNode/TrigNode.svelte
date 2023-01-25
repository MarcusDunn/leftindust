<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  
  import { List, ListInput } from 'framework7-svelte';
  
  export let inputs: InputSockets<{
    Input: InputSocket<number>;
  }>;
  
  export let outputs: OutputSockets<{
    Output: OutputSocket<number>;
  }>;
  
  export let store: {
    type: 'radians' | 'degrees', 
    func: 'sin' | 'cos' | 'tan' | 'asin' | 'acos' | 'atan'
  } = {
    type: 'radians',
    func: 'sin',
  };

  $: useDegrees = store.type === 'degrees';
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = (() => {
    const input = $Input;
  
    switch (store.func) {
      case 'sin':
        return Math.sin(input * (useDegrees ? (Math.PI / 180) : 1));
      case 'cos':
        return Math.cos(input * (useDegrees ? (Math.PI / 180) : 1));
      case 'tan':
        return Math.tan(input * (useDegrees ? (Math.PI / 180) : 1));
      case 'asin':
        return Math.asin(input) * (useDegrees ? (180 / Math.PI) : 1);
      case 'acos':
        return Math.acos(input) * (useDegrees ? (180 / Math.PI) : 1);
      case 'atan':
        return Math.atan(input) * (useDegrees ? (180 / Math.PI) : 1);
    }
  })();

  $: displayOutput = (() => {
    if ($Output.toString().length > 5) {
      return (Math.abs($Output) < 0.1) ? $Output.toExponential(3) : $Output.toPrecision(4);
    }
    
    return $Output;
  })();

  $: inputs, store;
</script>
  
<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Function"
    type="select"
    bind:value={store.func}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="sin">sin</option>
    <option value="cos">cos</option>
    <option value="tan">tan</option>
    <option value="asin">asin</option>
    <option value="acos">acos</option>
    <option value="atan">atan</option>
  </ListInput>

  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="radians">Radians</option>
    <option value="degrees">Degrees</option>
  </ListInput>
</List>