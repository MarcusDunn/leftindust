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
    type: 'default' | 'inverse', 
    func: 'sin' | 'cos' | 'tan'
  } = {
    type: 'default',
    func: 'sin',
  };

  $: useInverse = store.type === 'inverse';
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = (() => {
    const input = $Input;
  
    switch (store.func) {
      case 'sin':
        return useInverse ? Math.asin(input) : Math.sin(input);
      case 'cos':
        return useInverse ? Math.acos(input) : Math.cos(input);
      case 'tan':
        return useInverse ? Math.atan(input) : Math.tan(input);
    }
  })();

  $: displayOutput = (() => {
    if ($Output.toString().length > 5) {
      return ($Output < 0.1) ? $Output.toExponential(3) : $Output.toPrecision(4);
    }
    
    return $Output;
  })();

  $: inputs, store;
</script>
  
<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="default">Default</option>
    <option value="inverse">Inverse</option>
  </ListInput>

  <ListInput
    label="Function"
    type="select"
    bind:value={store.func}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="sin">{useInverse ? 'asin' : 'sin'}</option>
    <option value="cos">{useInverse ? 'acos' : 'cos'}</option>
    <option value="tan">{useInverse ? 'atan' : 'tan'}</option>
  </ListInput>
</List>