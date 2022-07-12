<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { List } from 'framework7-svelte';
  import { joinStr } from '../..';

  export let inputs: InputSockets<{
    LHS: InputSocket<string>;
    RHS: InputSocket<string>;
    DELIMITER: InputSocket<string>;
  }>;

  export let outputs: OutputSockets<{
    Text: OutputSocket<string>;
  }>;

  const { value: LHS } = inputs.LHS;
  const { value: RHS } = inputs.RHS;
  const { value: DELIMITER } = inputs.DELIMITER;
  const { value: output } = outputs.Text;

  const getValue = () => {
    $output = joinStr($DELIMITER, $LHS, $RHS);
  };

  $: inputs, getValue();
</script>

<h1 class="no-margin" style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <option value="join" style="padding: 5px 0; text-align:center; background-color: transparent">Join</option>
</List>