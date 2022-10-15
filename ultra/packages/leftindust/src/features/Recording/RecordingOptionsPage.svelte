<script lang="ts">
  import { LanguageCode, Specialty } from '@aws-sdk/client-transcribe-streaming';
  import { f7, Preloader, Toggle } from 'framework7-svelte';
  import { onMount } from 'svelte';
  import { detectRecordingDevice } from '.';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Control from '../UI/components/Control/Control.svelte';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import SelectButton from '../UI/components/SelectButton/SelectButton.svelte';
  import RecordingButton from './components/RecordingButton/RecordingButton.svelte';

  import {
    recordingSequenceStarted,
    recordingTimer,
    defaultAudioDeviceId,
    recordingLanguage,
    recordingMedicalSpecialty,
    recordingConversation,
  } from './store';

  let microphones: { id: string, value: string }[] | undefined = undefined;

  export let callback: () => void = () => undefined;

  onMount(() => {
    navigator.mediaDevices.enumerateDevices()
      .then((devices) => {
        microphones = detectRecordingDevice(devices);
      })
      .catch((error) => {
        console.error(error);
      });
  });
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar close={{ popup: true }} />
    <Footer>
      <div style="
        display: flex;
        align-items: center;
        padding: 20px 20px;
      "
      >
        <RecordingButton on:start={() => {
          f7.popup.close();
          callback();
        }} />
      </div>
    </Footer>
  </svelte:fragment>

  <div class={`${$recordingSequenceStarted ? 'disabled' : ''}`} style="padding: 20px 30px">
    <Control
      title="Profession"
      text="Select a profession to allow Iris to better understand specific terminology"
      icon={{ f7: 'briefcase_fill', color: 'blue' }}
    >
      <SelectButton
        options={[
          {
            text: 'General Physician',
            value: Specialty.PRIMARYCARE,
          },
          {
            text: 'Cardiologist',
            value: Specialty.CARDIOLOGY,
          },
          {
            text: 'Neurologist',
            value: Specialty.NEUROLOGY,
          },
          {
            text: 'Oncologist',
            value: Specialty.ONCOLOGY,
          },
          {
            text: 'Radiologist',
            value: Specialty.RADIOLOGY,
          },
          {
            text: 'Urologist',
            value: Specialty.UROLOGY,
          },
        ]}
        style="width: 230px"
        bind:value={$recordingMedicalSpecialty}
      />
    </Control>
    <br />
    <Control
      title="Converation"
      text="Enable this option if this interaction is between multiple people"
      icon={{ f7: 'chat_bubble_2_fill', color: 'lightblue' }}
    >
      <Toggle
        class="aurora"
        color="blue"
        bind:checked={$recordingConversation}
      />
    </Control>
    <br />
    <!--      
      <Control
        title="Language"
        text="Select the primary language to record in"
        icon={{ f7: 'globe', color: 'primary' }}
      >
        <SelectButton
          options={[
            {
              text: 'English',
              value: LanguageCode.EN_US,
            },
            {
              text: 'French',
              value: LanguageCode.FR_FR,
            },
            {
              text: 'Spanish',
              value: LanguageCode.ES_US,
            },
            {
              text: 'German',
              value: LanguageCode.DE_DE,
            },
            {
              text: 'Italian',
              value: LanguageCode.IT_IT,
            },
            {
              text: 'Japanese',
              value: LanguageCode.JA_JP,
            },
            {
              text: 'Korean',
              value: LanguageCode.KO_KR,
            },
            {
              text: 'Portuguese',
              value: LanguageCode.PT_BR,
            },
            {
              text: 'Chinese (PRC)',
              value: LanguageCode.ZH_CN,
            },
          ]}
          style="width: 230px"
          bind:value={$recordingLanguage}
        />
      
      </Control>
      <br />
    -->
    <br />
    <br />
    <Control
      title="Countdown"
      text="Countdown before recording starts"
      icon={{ f7: 'textformat_123' }}
    >
      <SelectButton
        options={[
          {
            text: 'None',
            value: '0',
          },
          {
            text: '3 seconds',
            value: '3',
          },
          {
            text: '5 seconds',
            value: '5',
          },
          {
            text: '10 seconds',
            value: '10',
          },
          {
            text: '15 seconds',
            value: '15',
          },
        ]}
        style="width: 200px"
        bind:value={$recordingTimer}
      />
    </Control>
    <br />
    <Control
      title="Microphone"
      text="The microphone used to record audio"
      icon={{ f7: 'mic_fill', color: 'orange' }}
    >
      {#if microphones}
        <SelectButton
          options={microphones.map((microphone) => ({
            text: microphone.value,
            value: microphone.id,
          }))}
          style="width: 200px"
          bind:value={$defaultAudioDeviceId}
        />
      {:else}
        <Preloader />
      {/if}
    </Control>
  </div>
</Page>