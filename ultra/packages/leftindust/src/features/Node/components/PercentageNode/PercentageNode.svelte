<script lang="ts">
  import type {
    InputSockets,
    InputSocket,
    OutputSocket,
    OutputSockets,
  } from 'function-junctions/types';
  
  import { List, ListInput } from 'framework7-svelte';
  import { getDisplayOutput } from '../nodeUtils';
  
  export let inputs: InputSockets<{
    Input: InputSocket<number>;
  }>;
  
  export let outputs: OutputSockets<{
    Output: OutputSocket<number>;
  }>;
  
  export let store: 'to' | 'from' = 'to';
  
  const { value: Input } = inputs.Input;
  const { value: Output } = outputs.Output;
  
  $: $Output = $Input * (store == 'to' ? 100 : 0.01);
  $: displayOutput = getDisplayOutput($Output) + (store == 'to' ? '%' : '');
  $: inputs, store;
</script>

<h1 class="no-margin" style="text-align: center">{displayOutput}</h1>
<List class="no-margin" noHairlines>
  <ListInput
    label="Conversion"
    type="select"
    bind:value={store}
    style="margin-right: 20px"
  >
    <i class="icon demo-list-icon" slot="media" />
    <option value="to">Decimal &rarr; Percent</option>
    <option value="from">Percent &rarr; Decimal</option>
  </ListInput>
</List>