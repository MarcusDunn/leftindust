<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';

  export let inputs: InputSockets<{
    Numbers: InputSocket<number[]>;
  }>;

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;

  const { value: Numbers } = inputs.Numbers;
  
  const { value: output } = outputs.Number;

  $: $output = (() => {
    let value = 0;

    $Numbers.forEach((number) => {
      value = value + number;
    });

    return value / $Numbers.length;
  })();
  
</script>

<h1 class="no-margin" style="text-align: center">{$output}</h1>