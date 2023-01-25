<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';

  import { List, ListInput } from 'framework7-svelte';

  export let inputs: InputSockets<{
    LHS: InputSocket<number>;
    RHS: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;

  export let store: {
    type: 'addition' | 'subtraction' | 'multiplication' | 'division'
  } = {
    type: 'addition',
  };

  const { value: LHS } = inputs.LHS;
  const { value: RHS } = inputs.RHS;
  
  const { value: Output } = outputs.Number;

  $: $Output = (() => {
    const { type } = store;

    const left = $LHS;
    const right = $RHS;

    switch (type) {
      case 'addition':
        return left + right;
      case 'subtraction':
        return left - right;
      case 'multiplication':
        return left * right;
      case 'division':
        return left / right;
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
    label="Type"
    type="select"
    bind:value={store.type}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="addition">Add</option>
    <option value="subtraction">Subtract</option>
    <option value="multiplication">Multiply</option>
    <option value="division">Divide</option>
  </ListInput>
</List>