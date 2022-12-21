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
  
  const { value: Output } = outputs.Number;

  $: $Output = (() => {
    let value = 0;

    $Numbers.forEach((number) => {
      value = value + number;
    });

    return value / $Numbers.length;
  })();

  $: displayOutput = (() => {
    if ($Output.toString().length > 5) {
      return ($Output < 0.1) ? $Output.toExponential(3) : $Output.toPrecision(4);
    }
    
    return $Output;
  })();
</script>

<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>