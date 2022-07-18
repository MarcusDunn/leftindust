<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { Button, List, ListInput } from 'framework7-svelte';

  export let inputs: InputSockets<{
    OPERAND: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;

  type OperationType = 'sin' | 'cos' | 'tan';
  type OperationStore = { type: OperationType };
  export let store: OperationStore = { type: 'sin' };

  const { value: OPERAND } = inputs.OPERAND;
  const { value: output } = outputs.Number;

  let inverse = false;

  const getValue = () => {
    const { type } = store;
    
    switch (type) {
      case 'sin':
        $output = inverse ? Math.asin($OPERAND) : Math.sin($OPERAND);
        break;
      case 'cos':
        $output = inverse ? Math.acos($OPERAND) : Math.cos($OPERAND);
        break;
      case 'tan':
        $output = inverse ? Math.atan($OPERAND) : Math.tan($OPERAND);
        break;
    }
  };

  $: inputs, store, inverse, getValue();
</script>

<h1 style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="sin">Sine{inverse ? '-1' : ''}</option>
    <option value="cos">Cosine{inverse ? '-1' : ''}</option>
    <option value="tan">Tangent{inverse ? '-1' : ''}</option>
  </ListInput>
</List>
<div class="display-flex">
  <Button
    fill={!inverse}
    outline={inverse}
    round
    color="black"
    style="width: 80%; margin: 1rem auto"
    on:click={() => inverse = !inverse}
  >
    Inverse
  </Button>
</div>

