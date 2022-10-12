<script lang="ts">
  import { Button, Searchbar } from 'framework7-svelte';
  import RecordingButton from '../Recording/components/RecordingButton/RecordingButton.svelte';
  import RecordingModal from '../Recording/components/RecordingModal/RecordingModal.svelte';
  import { recordingSequenceStarted } from '../Recording/store';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import { openDialog } from '../UI/components/Dialog';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import MenuButton from '../UI/components/MenuButton/MenuButton.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { openPopupUrl } from '../View';
</script>

<Page style="overflow: hidden">
  <svelte:fragment slot="fixed">
    <Appbar>
      <Searchbar
        class="color-blue"
        customSearch
        inline
        disableButton={false}
        placeholder="Search for Recordings..."
      />
    </Appbar>
    <Footer>
      <div style="
        display: flex;
        align-items: center;
        padding: 25px 20px;
      "
      >
        <RecordingButton
          style="margin-right: 20px;"
          on:start={() => openDialog({
            title: 'Recording',
            component: RecordingModal,
          })}
        />
        <MenuButton
          icon={{ f7: 'slider_horizontal_3' }}
          on:click={() => openPopupUrl('/options/')}
          disabled={$recordingSequenceStarted}
        />
      </div>
    </Footer>
  </svelte:fragment>
</Page>