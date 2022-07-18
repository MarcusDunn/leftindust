<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { Button, List, ListInput } from 'framework7-svelte';
  import { invPower, nRoot } from '../..';
  import { flip } from 'svelte/animate';

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

  let pwrFlip = false;

  const getValue = () => {
    const { type } = store;
    
    switch (type) {
      case 'power':
        $output = invPower($BASE, $POWER, pwrFlip);
        break;
      case 'root':
        $output = nRoot($BASE, $POWER, pwrFlip);
        break;
    }
  };

  $: inputs, store, getValue(), pwrFlip;
</script>

<h1 class="no-margin" style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Type"
    type="select"
    bind:value={store.type}
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="power">{pwrFlip ? '1/' : ''}Power</option>
    <option value="root">{pwrFlip ? '1/' : ''}Root</option>
  </ListInput>
</List>
<div class="display-flex">
  <Button
    fill={!pwrFlip}
    outline={pwrFlip}
    round
    color="black"
    style="width: 80%; margin: 1rem auto"
    on:click={() => pwrFlip = !pwrFlip}
  >
    Flip
  </Button>
</div>