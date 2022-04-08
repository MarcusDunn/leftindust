<script lang="ts">
  import { PageContent, Block, BlockFooter } from 'framework7-svelte';

  import { _ } from '@/language';
</script>

<PageContent>
  <br />
  <Block class=" text-align-center no-margin-top">
    <h1 class="no-margin-bottom" style="font-size: 65px">{$_('generics.app')} <span class="text-color-purple">{$_('generics.alpha')}</span></h1>
    <Block>
      <BlockFooter>
        <p>{$_('descriptions.copyright')}</p>
        <br />
        {$_('descriptions.nda')}
        <p><b>{$_('descriptions.confidential')}</b></p>
      </BlockFooter>
    </Block>
  </Block>
  
</PageContent>
