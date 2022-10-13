<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';
  import { writable, type Writable } from 'svelte/store';
  import { onMount } from 'svelte';

  import './RecordingModal.scss';
  import RecordingIndicator from '../RecordingIndicator/RecordingIndicator.svelte';
  import DialogContent from '@/features/UI/components/Dialog/DialogContent.svelte';
  import { defaultAudioDeviceId } from '../../store';
  import { AudioStream, StartStreamTranscriptionCommand } from '@aws-sdk/client-transcribe-streaming';
  import { awsTranscribeClient, convertBlock, pcmEncodeChunk } from '../..';

  export let open: Writable<boolean>;

  const buffers: Promise<Float32Array>[] = [];

  const unprocessedBuffers: Writable<Float32Array[]> = writable([]);

  let media: BlobPart[] = [];
  let mediaRecorder: MediaRecorder;

  let audioRef: HTMLAudioElement;

  let playing = false;

  let initalized = false;

  let error = '';

  let pausedDuration = 0;

  let time = '00:00:00';

  const formatTimestamp = (timestamp: number): string => {
    const h = Math.floor(timestamp / 3600).toString().padStart(2,'0'),
      m = Math.floor(timestamp % 3600 / 60).toString().padStart(2,'0'),
      s = Math.floor(timestamp % 60).toString().padStart(2,'0');
    
    return h + ':' + m + ':' + s;
  };

  const audioStream: AsyncIterable<AudioStream> = {
    [Symbol.asyncIterator]() {
      return {
        async next() {
          return new Promise((resolve) => {
            unprocessedBuffers.subscribe((buffers) => {
              setTimeout(() => {
                if (playing) {
                  if (buffers) {
                    buffers.forEach((buffer) => {
                      resolve({
                        value: {
                          AudioEvent: {
                            AudioChunk: pcmEncodeChunk(buffer),
                          },  
                        },
                      });
                    });
                  }
                  
                  unprocessedBuffers.set([]);
                }
              }, 100);
            });
          });
        },
      };
    },
  };
  const command = new StartStreamTranscriptionCommand({
    // The language code for the input audio. Valid values are en-GB, en-US, es-US, fr-CA, and fr-FR
    LanguageCode: 'en-US',
    // The encoding used for the input audio. The only valid value is pcm.
    MediaEncoding: 'pcm',
    // The sample rate of the input audio in Hertz. We suggest that you use 8000 Hz for low-quality audio and 16000 Hz for
    // high-quality audio. The sample rate must match the sample rate in the audio file.
    MediaSampleRateHertz: 44100,
    AudioStream: audioStream,
  });

  
  onMount(async () => {
    if (audioRef) {
      const response = await awsTranscribeClient.send(command);

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

        initalized = true;

        // Get time offset from recording init
        const differenceBetweenClocks = (performance.now() / 1000) - audioRef.currentTime;

        mediaRecorder.ondataavailable = ({ timeStamp, data }) => {
          const audioContextTime = (timeStamp / 1000) - differenceBetweenClocks;

          void data.arrayBuffer().then((arrayBuffer) => {
            buffers.push(Promise.resolve(convertBlock(arrayBuffer)));

            unprocessedBuffers.update((buffers) => {
              buffers.push(convertBlock(arrayBuffer));
              return buffers;
            });
          });

          if (playing) {
            time = formatTimestamp(audioContextTime - pausedDuration);
          }

          media.push(data);
        };

        mediaRecorder.onstart = () => {
          playing = true;
        };

        mediaRecorder.onstop = () => {
          const blob = new Blob(media, { 'type' : 'audio/wav' });
          media = [];
          audioRef.src = window.URL.createObjectURL(blob);
        };

      } catch(err) {
        error = err as string;
      }
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
  {/if}
</div>