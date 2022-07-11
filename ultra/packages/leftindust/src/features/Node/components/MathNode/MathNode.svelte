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

  type OperationType = 'addition' | 'subtraction' | 'multiplication' | 'division';
  type OperationStore = { type: OperationType };
  export let store: OperationStore = { type: 'addition' };

  const { value: LHS } = inputs.LHS;
  const { value: RHS } = inputs.RHS;
  const { value: output } = outputs.Number;

  const getValue = () => {
    const { type } = store;

    // @ts-expect-error
    const left = parseInt($LHS, 10);
    // @ts-expect-error
    const right = parseInt($RHS, 10);

    switch (type) {
      case 'addition':
        $output = left + right;
        break;
      case 'subtraction':
        $output = left - right;
        break;
      case 'multiplication':
        $output = left * right;
        break;
      case 'division':
        $output = left / right;
        break;
    }
  };

  $: inputs, store, getValue();
</script>

<h1 class="no-margin" style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="addition">Addition</option>
    <option value="subtraction">Subtraction</option>
    <option value="multiplication">Multiplication</option>
    <option value="division">Division</option>
  </ListInput>
</List>