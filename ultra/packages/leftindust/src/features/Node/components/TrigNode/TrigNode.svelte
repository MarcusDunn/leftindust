<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { List, ListInput } from 'framework7-svelte';

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

  const getValue = () => {
    const { type } = store;

    switch (type) {
      case 'sin':
        $output = Math.sin($OPERAND);
        break;
      case 'cos':
        $output = Math.cos($OPERAND);
        break;
      case 'tan':
        $output = Math.tan($OPERAND);
        break;
    }
  };

  $: inputs, store, getValue();
</script>

<h1 style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="sin">Sine</option>
    <option value="cos">Cosine</option>
    <option value="tan">Tangent</option>
  </ListInput>
</List>