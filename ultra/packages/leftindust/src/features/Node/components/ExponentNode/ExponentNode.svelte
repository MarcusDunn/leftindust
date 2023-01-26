<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  import { getDisplayOutput } from '../nodeUtils';

  export let inputs: InputSockets<{
    Base: InputSocket<number>;
    Power: InputSocket<number>;
  }>;

  export let outputs: OutputSockets<{
    Output: OutputSocket<number>;
  }>;

  const { value: Base } = inputs.Base;
  const { value: Power } = inputs.Power;
  
  const { value: Output } = outputs.Output;

  $: $Output = (() => {
    const base = $Base;
    const power = $Power;
    console.log(base);

    return Math.pow(base, power);
  })();

  $: displayOutput = getDisplayOutput($Output);
  $: inputs;
</script>

<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>