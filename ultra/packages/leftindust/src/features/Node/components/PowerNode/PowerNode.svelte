<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { List, ListInput } from 'framework7-svelte';

  export let inputs: InputSockets<{
    BASE: InputSocket<number>;
    POWER: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;

  type OperationType = 'power' | 'root';
  type OperationStore = { type: OperationType };
  export let store: OperationStore = { type: 'power' };

  const { value: BASE } = inputs.BASE;
  const { value: POWER } = inputs.POWER;
  const { value: output } = outputs.Number;

  const getValue = () => {
    const { type } = store;

    // @ts-expect-error
    const base = parseInt($BASE, 10);
    // @ts-expect-error
    const power = parseInt($POWER, 10);
    
    switch (type) {
      case 'power':
        $output = Math.pow(base, power);
        break;
      case 'root':
        $output = power === 2 ? Math.sqrt(base) : Math.pow(base, 1 / power);
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
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="power">Power</option>
    <option value="root">Root</option>
  </ListInput>
</List>