<script lang="ts">
  import { _ } from '@/language';
  import { Block, Icon } from 'framework7-svelte';
  
  import { signOut } from '../Account';
  
  import Page from '@/features/UI/components/Page/Page.svelte';
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import ErrorButtons from './components/ErrorButtons/ErrorButtons.svelte';

  export let error: Error;

</script>

<Page>
  <AppBar slot="fixed" />
  <Block class="text-align-center" style="padding: 40px; padding-top: 20px">
    <br />
    <Icon class="text-color-yellow" f7="face_smiling_fill" style="font-size: 70px; transform: rotate(-180deg)" />
    <br />
    <br />
    <h2>{$_('errors.internalError')}</h2>
    <p>{$_('errors.runtimeError')}</p>
    <br />
    <br />
    <ErrorButtons
      reload={() => location.reload()}
      report={() => undefined}
      signOut={() => {
        signOut();
        location.reload();
      }}
    />
  </Block>
</Page>
