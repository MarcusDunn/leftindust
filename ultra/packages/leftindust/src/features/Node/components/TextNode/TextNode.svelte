<script lang="ts">
  import Input from '@/features/Input/Input.svelte';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Text: OutputSocket<string>;
  }>;
  
  const { value } = outputs.Text;
</script>

<Input style="min-width: 430px">
  <input type="text" placeholder="Eg. Basic Text" bind:value={$value} />
</Input>