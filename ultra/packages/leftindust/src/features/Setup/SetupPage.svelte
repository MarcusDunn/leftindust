<script lang="ts">
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';

  import { account } from '@/features/Account/store';

  import { _ } from '@/language';
  import { Block, BlockFooter, Button, f7 } from 'framework7-svelte';

</script>

<Page>
  <Appbar slot="fixed" />
  <Block>

    <h1 class="no-margin-bottom" style="font-size: 65px">{$_('generics.app')} <span class="text-color-purple">{$_('generics.alpha')}</span></h1>
    <p>Welcome to the pre-release version of MedIQ!</p>
    <br />
    <br />
    <Button
      large
      fill
      color="purple"
      on:click={() => {
        $account.database.settings.setup = true;
        f7.popup.close();
      }}
    >
      {$_('generics.continue')}
    </Button>
    <br />
    <br />
    <br />
    <p class="text-align-center"><i>Please note that many features are still in development and may not work as expected</i></p>
    <p />
    <BlockFooter class="text-align-center" style="font-size: 14px;">
      <p>This software is proprietary and confidential</p>
      <p><b>By clicking continue, you are agreeing to these terms.</b></p>
    </BlockFooter>
    <br />
    <br />
    <p class="text-align-center">{$_('descriptions.copyright')}</p>
  </Block>
</Page>