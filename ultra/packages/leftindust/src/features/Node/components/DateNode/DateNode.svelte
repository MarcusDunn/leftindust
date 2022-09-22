<script lang="ts">
  import Date from '@/features/Input/components/Date/DatePicker.svelte';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Value: OutputSocket<number>;
  }>;
  
  const { value } = outputs.Value;
</script>

<div style="min-width: 430px">
  <Date title="Date" placeholder="Select a Date" bind:value={$value} />
</div>