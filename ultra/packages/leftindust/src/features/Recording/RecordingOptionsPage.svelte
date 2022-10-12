<script lang="ts">
  import { f7, Toggle } from 'framework7-svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Control from '../UI/components/Control/Control.svelte';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import SelectButton from '../UI/components/SelectButton/SelectButton.svelte';
  import RecordingButton from './components/RecordingButton/RecordingButton.svelte';

  import { recordingSequenceStarted, recordingTimer } from './store';

  export let callback: () => void = () => undefined;
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
            text: 'Practitioner',
            value: 'practitioner',
          },
        ]}
        style="width: 230px"
        value="practitioner"
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
      />
    </Control>
    <br />
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
  </div>
</Page>