<script lang="ts">
  import Input from '@/features/Input/Input.svelte';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Value: OutputSocket<string>;
  }>;
  
  const { value } = outputs.Value;
</script>

<Input title="Text" style="min-width: 430px">
  <input type="text" placeholder="Eg. Basic Text" bind:value={$value} />
</Input>