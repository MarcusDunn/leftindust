<script lang="ts">
  import { PageContent, Block, BlockFooter } from 'framework7-svelte';

  import language from '@framework/languages';
</script>

<PageContent>
  <br />
  <Block class=" text-align-center no-margin-top">
    <h1 class="no-margin-bottom" style="font-size: 65px">{language().app.title.text} <span class="text-color-purple">{language().headers.alpha.text}</span></h1>
    <i>delta (0.1.0), bluebird (0.1.0), mockingbird (0.0.1)</i>
    
    <Block>
      <BlockFooter>
        <p>{language().legal.alpha.copyright.text}</p>
        <br />
        {language().legal.alpha.agreement.text}
        <p><b>{language().legal.alpha.confidential.text}</b></p>
      </BlockFooter>
    </Block>
  </Block>
  
</PageContent>
