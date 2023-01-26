<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { getDisplayOutput } from '../nodeUtils';

  export let inputs: InputSockets<{
    Arg: InputSocket<number>;
    Base: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Output: OutputSocket<number>;
  }>;

  const { value: Arg } = inputs.Arg;
  const { value: Base } = inputs.Base;
  
  const { value: Output } = outputs.Output;

  $: $Output = (() => {
    const arg = $Arg;
    const base = $Base ? $Base : 10;

    return Math.log(arg) / Math.log(base);
  })();

  $: displayOutput = getDisplayOutput($Output);
  $: inputs;
</script>

<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>