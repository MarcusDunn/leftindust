<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';
  import type { Writable } from 'svelte/store';
  import { onMount } from 'svelte';

  import './RecordingModal.scss';
  import RecordingIndicator from '../RecordingIndicator/RecordingIndicator.svelte';
  import DialogContent from '@/features/UI/components/Dialog/DialogContent.svelte';
  import { defaultAudioDeviceId, recordingConversation, recordingLanguage, recordingMedicalSpecialty } from '../../store';
  import { StartMedicalStreamTranscriptionCommand, StartStreamTranscriptionCommand } from '@aws-sdk/client-transcribe-streaming';
  import { animateIncomingText, audioStream, awsTranscribeClient } from '../..';
  import Microphone from 'microphone-stream';
  import { Block } from 'framework7-svelte';
  import { fade, scale } from 'svelte/transition';

  export let open: Writable<boolean>;

  const refreshDuration = 500;

  let audioRef: HTMLAudioElement;
  
  let mediaRecorder: MediaRecorder;

  let media: BlobPart[] = [];

  let initalized = false;
  let playing = false;
  let error = '';

  let pausedDuration = 0;
  let time = '00:00:00';

  let bufferedTranscript: { time: string; text: string }[] = [{ time: '00:00:00', text: '' }];
  let transcript = '';

  const formatTimestamp = (timestamp: number): string => {
    const h = Math.floor(timestamp / 3600).toString().padStart(2,'0'),
      m = Math.floor(timestamp % 3600 / 60).toString().padStart(2,'0'),
      s = Math.floor(timestamp % 60).toString().padStart(2,'0');
    
    return h + ':' + m + ':' + s;
  };
  
  onMount(async () => {
    if (audioRef) {
      try {
        const stream = await navigator.mediaDevices.getUserMedia(
          {
            audio: {
              deviceId: {
                exact: $defaultAudioDeviceId,
              },
            },
          },
        );

        mediaRecorder = new MediaRecorder(stream);

        let microphoneStream = new Microphone();
        await microphoneStream.setStream(stream);

        const command = new StartMedicalStreamTranscriptionCommand({
          Specialty: $recordingMedicalSpecialty,
          Type: $recordingConversation ? 'CONVERSATION' : 'DICTATION',
          // The language code for the input audio. Valid values are en-GB, en-US, es-US, fr-CA, and fr-FR
          LanguageCode: $recordingLanguage,
          // The encoding used for the input audio. The only valid value is pcm.
          MediaEncoding: 'pcm',
          // The sample rate of the input audio in Hertz. We suggest that you use 8000 Hz for low-quality audio and 16000 Hz for
          // high-quality audio. The sample rate must match the sample rate in the audio file.
          MediaSampleRateHertz: 44100,
          AudioStream: audioStream(microphoneStream),
        });

        initalized = true;

        // Get time offset from recording init
        const differenceBetweenClocks = (performance.now() / 1000) - audioRef.currentTime;

        mediaRecorder.ondataavailable = ({ timeStamp, data }) => {
          const audioContextTime = (timeStamp / 1000) - differenceBetweenClocks;

          if (playing) time = formatTimestamp(audioContextTime - pausedDuration);

          media.push(data);
        };

        mediaRecorder.onstart = () => {
          playing = true;
        };

        mediaRecorder.onpause = () => microphoneStream.pause();

        mediaRecorder.onresume = () => microphoneStream.resume();

        mediaRecorder.onstop = () => {
          microphoneStream.stop();
          const blob = new Blob(media, { 'type' : 'audio/wav' });
          media = [];
          audioRef.src = window.URL.createObjectURL(blob);
        };

        const response = await awsTranscribeClient.send(command);

        for await (const event of response.TranscriptResultStream ?? []) {
          if (event.TranscriptEvent) {
            const results = event.TranscriptEvent?.Transcript?.Results;
            if (results && results.length > 0) {
              const [result] = results;
              const final = !result.IsPartial;
              const alternatives = result.Alternatives;
              if (alternatives && alternatives.length > 0) {
                const [alternative] = alternatives;
                const text = alternative.Transcript;
                if (final) {
                  bufferedTranscript[bufferedTranscript.length - 1] = {
                    time,
                    text: `${text}\n` ?? '',
                  };
                  bufferedTranscript = [...bufferedTranscript, {
                    time,
                    text: '',
                  }];
                } else {
                  bufferedTranscript[bufferedTranscript.length - 1] = {
                    time,
                    text: text ?? '',
                  };
                }
              }
            }  
          }
        }
      } catch(err) {
        error = err as string;
      }
    }
  });

  $: transcript = bufferedTranscript.map(({ text }) => text).join(' ');
  
  $: {
    const timeout = setTimeout(() => {
      if (!playing) {
        pausedDuration += refreshDuration / 1000;
      } else {
        clearTimeout(timeout);
      }
    }, refreshDuration);
  }

  const startRecording = () => {
    if (mediaRecorder.state !== 'recording') mediaRecorder.start(refreshDuration);
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
  <audio bind:this={audioRef} controls hidden />
  {#if !initalized}
    {#if error}
      <DialogContent dialog={{
        title: 'Failed to start recording',
        text: error,
        buttons: [
          {
            label: $_('generics.ok'),
            onClick: () => $open = false,
          },
        ],
      }}
      />
    {/if}
  {/if}

  {#if initalized}
    <div class="recording-recording_modal-transcription-container">
      <div class="recording-recording_modal-transcription">
        {#each transcript.split(' ') as word, index}
          {#if transcript.split(' ').length - 1 === index}
            <span in:fade>{word + ' '}</span>
          {:else}
            <span>{word + ' '}</span>
          {/if}
        {/each}
      </div>
      <div class="recording-recording_modal-transcription-taper" />
    </div>
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
            $open = false;
          }}
        />
      </div>
    </div>
  {/if}
</div>