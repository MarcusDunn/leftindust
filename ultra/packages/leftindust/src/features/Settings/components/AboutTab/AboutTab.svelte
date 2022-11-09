<script lang="ts">
  import { Block, BlockFooter } from 'framework7-svelte';

  import { _ } from '@/language';
</script>

<Block class="no-margin-top">
  <h1 class="no-margin-bottom" style="font-size: 65px">{$_('generics.app')} <span class="text-color-purple">{$_('generics.alpha')}</span></h1>
  <BlockFooter>
    <p>{$_('descriptions.copyright')}</p>
    <br />
    {$_('descriptions.nda')}
    <p><b>{$_('descriptions.confidential')}</b></p>
  </BlockFooter>
</Block>
