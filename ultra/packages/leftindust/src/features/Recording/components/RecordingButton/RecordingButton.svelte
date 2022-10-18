<script lang="ts">
  import { Button } from 'framework7-svelte';
  import './RecordingButton.scss';
  import { cubicInOut } from 'svelte/easing';
  import { scale } from 'svelte/transition';
  import { recordingSequenceStarted, recordingTimer, forceRecordingSequence } from '../../store';
  import { createEventDispatcher, tick } from 'svelte';

  export let label = 'Start Recording';

  export let style = '';

  let recordingSequenceEnded = false;

  $forceRecordingSequence = false;

  const dispatch = createEventDispatcher();

  $: counter = parseInt($recordingTimer);

  $: {
    if ($forceRecordingSequence) {
      dispatch('start');
      void tick().then(() => {
        $forceRecordingSequence = false;
      });
    }
  }

  $: {
    const timeout = setTimeout(() => {
      if ($recordingSequenceStarted && counter >= 0) counter--;
      if (counter <= 1) recordingSequenceEnded = true;
    }, 1000);

    if (counter <= 0 || !$recordingSequenceStarted) {
      clearTimeout(timeout);

      if (recordingSequenceEnded) dispatch('start');

      counter = parseInt($recordingTimer);
      $recordingSequenceStarted = false;
      recordingSequenceEnded = false;
    }
  }
</script>

<div class="recording-recording_button_container" {style}>
  <div class="recording-recording_button_placeholder" />
  {#if !$recordingSequenceStarted}
    <div
      transition:scale={{
        duration: 200,
        easing: cubicInOut,
      }}
    >
      <Button
        fill
        round
        color="primary"
        style="
          height: 100%;
          margin: 0 2px;
        "

        on:click={() => {
          if (counter > 0) {
            $recordingSequenceStarted = true;
          } else {
            $forceRecordingSequence = true;
          }
        }}
      >
        {label}
      </Button>
    </div>
  {:else}
    {#key counter}
      <!-- svelte-ignore a11y-click-events-have-key-events -->
      <div
        transition:scale={{
          delay: 200,
          duration: 200,
          easing: cubicInOut,
        }}
        class="recording-recording_button_counter"
        on:click={() => {
          $recordingSequenceStarted = false;
          recordingSequenceEnded = false;
        }}
      >
        <div class="text-color-blue">{counter}</div>
      </div>
    {/key}
  {/if}
</div>