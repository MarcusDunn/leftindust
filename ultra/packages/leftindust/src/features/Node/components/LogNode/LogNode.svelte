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
    VALUE: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;

  const { value: BASE } = inputs.BASE;
  const { value: VALUE } = inputs.VALUE;
  const { value: output } = outputs.Number;

  const BASE10 = 10;
  const BASE2 = 2;
  const getValue = () => {
    switch ($BASE) {
      case BASE10:
        $output = Math.log10($VALUE);
        break;
      case BASE2:
        $output = Math.log2($VALUE);
        break;
      default:
        $output = Math.log($VALUE) / Math.log($BASE);
        break;
    }
  };

  $: inputs, getValue();
</script>

<h1 class="no-margin" style="text-align: center">{$output}</h1>
<List class="no-margin" noHairlines>
  <option value="log" style="padding: 5px 0; text-align:center">Logarithm</option>
</List>