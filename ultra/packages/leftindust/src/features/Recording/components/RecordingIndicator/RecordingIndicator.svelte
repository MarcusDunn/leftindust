<script lang="ts">
  import { scale } from 'svelte/transition';
  import './RecordingIndicator.scss';

  export let pulse = true;
  export let time: string;
</script>

<div class="recording-recording_indicator">
  {#key pulse}
    {#if pulse}
      <div class="recording-recording_indicator-dot" transition:scale={{ duration: 400 }} />
    {/if}
  {/key}
  <div
    class={`recording-recording_indicator-timestamp
      ${!pulse ? 'recording-recording_indicator-timestamp-slide' : ''}
    `}
  >
    {time}
  </div>
</div>