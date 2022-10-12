<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';
  import type { Writable } from 'svelte/store';
  import { onMount } from 'svelte';

  import './RecordingModal.scss';
  import RecordingIndicator from '../RecordingIndicator/RecordingIndicator.svelte';
  import { fade } from 'svelte/transition';

  export let open: Writable<boolean>;

  let media: BlobPart[] = [];
  let mediaRecorder: MediaRecorder;

  let audioRef: HTMLAudioElement;

  let playing = false;

  let pausedDuration = 0;

  let time = '00:00:00';

  const formatTimestamp = (timestamp: number): string => {
    const h = Math.floor(timestamp / 3600).toString().padStart(2,'0'),
      m = Math.floor(timestamp % 3600 / 60).toString().padStart(2,'0'),
      s = Math.floor(timestamp % 60).toString().padStart(2,'0');
    
    return h + ':' + m + ':' + s;
  };

  onMount(() => {
    if (audioRef) {
      navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {        
        mediaRecorder = new MediaRecorder(stream);
        // Get time offset from recording init
        const differenceBetweenClocks = (performance.now() / 1000) - audioRef.currentTime;
        mediaRecorder.ondataavailable = ({ timeStamp, data }) => {
          const audioContextTime = (timeStamp / 1000) - differenceBetweenClocks;

          if (playing) {
            time = formatTimestamp(audioContextTime - pausedDuration);
          }
  
          media.push(data);
        };
  
        mediaRecorder.onstart = () => {
          playing = true;
        };
  
        mediaRecorder.onstop = () => {
          const blob = new Blob(media, { 'type' : 'audio/mpeg' });
          media = [];
          audioRef.src = window.URL.createObjectURL(blob);
        };
      }).catch((err) => {
        console.error(err);
      });
    }
  });
  
  $: {
    const timeout = setTimeout(() => {
      if (!playing) {
        pausedDuration += 0.1;
      } else {
        clearTimeout(timeout);
      }
    }, 100);
  }
  const startRecording = () => {
    mediaRecorder.start(100);
    playing = true;
  };

  const pauseRecording = () => {
    mediaRecorder.pause();
    playing = false;
  };

  const playRecording = () => {
    mediaRecorder.resume();
    playing = true;
  };

  const stopRecording = () => {
    mediaRecorder.stop();
    playing = false;
  };

  // For some reason if this is not here the timeout in paused duration will not run
  $: pausedDuration;

  $: if (mediaRecorder) startRecording();
</script>

<div class="recording-recording_modal">
  <audio bind:this={audioRef} controls />
  <div class={`recording-recording_modal-controls ${$open ? 'recording-recording_modal-controls-open' : ''}`}>
    <div class="recording-recording_modal-controls-content">
      <RecordingIndicator {time} pulse={playing} />

      <div class="flex-grow" />
      {#key playing}
        <MenuButton
          title={playing ? $_('generics.pause') :  $_('generics.play')}
          icon={{ f7: playing ? 'pause_fill' : 'play_fill', color: 'gray' }}
          on:click={() => {
            if (playing) {
              pauseRecording();
            } else {
              playRecording();
            }
          }}
        />
      {/key}
      <MenuButton
        title={$_('generics.stop')}
        icon={{ f7: 'square_fill', color: 'red' }}
        on:click={() => {
          stopRecording();
        // $open = false;
        }}
      />
    </div>
  </div>
</div>