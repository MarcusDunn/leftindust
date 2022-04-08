<script lang="ts">
  import language from '@framework/languages';
  import { ACCOUNT } from '@framework/store';
  import {
    Block,
    BlockFooter,
    Button,
    f7,
  } from 'framework7-svelte';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';

</script>

<PageUI>
  <AppBarUI />
  <Block>
    <Block>
      <h1 class="no-margin-bottom" style="font-size: 65px">{language().app.title.text} <span class="text-color-purple">{language().headers.alpha.text}</span></h1>
      <p>{language().placeholders.preRelease.text}</p>
      <br />
      <br />
      <Button
        large
        fill
        color="purple"
        on:click={() => {
          $ACCOUNT.database.settings.setup = true;
          f7.popup.close();
        }}
      >
        {language().buttons.continue.text}
      </Button>
      <br />
      <p class="text-align-center"><i>Please note that many features are still in development and may not work as expected</i></p>
      <p />
      <BlockFooter style="font-size: 14px;">
        <p>{language().legal.alpha.agreement.text}</p>
        <p><b>{language().legal.placeholders.byClickingNext.text}</b></p>
      </BlockFooter>
      <br />
      <br />
      <p class="text-align-center">{language().legal.alpha.copyright.text}</p>
    </Block>
  </Block>
</PageUI>