<script lang="ts">
  import DatePicker from '@/features/Input/components/Date/DatePicker.svelte';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Date: OutputSocket<number>;
  }>;
  
  const { value } = outputs.Date;
</script>

<div style="min-width: 430px">
  <DatePicker placeholder="Select a Date" bind:value={$value} />
</div>