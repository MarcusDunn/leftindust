<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { Button, List, ListInput } from 'framework7-svelte';

  export let inputs: InputSockets<{
    LHS: InputSocket<number>;
    RHS: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Boolean: OutputSocket<boolean>;
  }>;

  type OperationType = '=' | '!=' | 'excluded' | 'included';
  type OperationStore = { type: OperationType };
  export let store: OperationStore = { type: '=' };

  const { value: LHS } = inputs.LHS;
  const { value: RHS } = inputs.RHS;
  const { value: output } = outputs.Boolean;

  let flip = false;

  const getValue = () => {
    store;
    const { type } = store;

    // @ts-expect-error
    const left = parseInt($LHS, 10);
    // @ts-expect-error
    const right = parseInt($RHS, 10);

    switch (type) {
      case '=':
        $output = left === right;
        break;
      case '!=':
        $output = left !== right;
        break;
      case 'excluded':
        $output = flip ? left > right : left < right;
        break;
      case 'included':
        $output = flip ? left >= right : left <= right;
        break;
    }
  };

  $: inputs, store, flip, getValue();
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
    <option value="=">Equals</option>
    <option value="!=">Not Equals</option>
    <option value="excluded">{flip ? '>' : '<'}</option>
    <option value="included">{flip ? '>=' : '<='}</option>
  </ListInput>
</List>
<div class="display-flex">
  <Button
    fill={!flip}
    outline={flip}
    round
    color="deeppurple"
    style="width: 80%; margin: 1rem auto"
    on:click={() => flip = !flip}
  >
    Flip
  </Button>
</div>