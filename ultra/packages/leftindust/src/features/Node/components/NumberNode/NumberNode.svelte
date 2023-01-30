<script lang="ts">
  import Input from '@/features/Input/Input.svelte';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Number: OutputSocket<number>;
  }>;
  
  const { value } = outputs.Number;
</script>

<Input style="min-width: 430px">
  <input type="number" placeholder="Eg. 100" bind:value={$value} />
</Input>