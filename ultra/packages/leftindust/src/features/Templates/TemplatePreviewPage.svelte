<script lang="ts">
  import { TemplateIndex, TemplateInputItems, TemplateSelectedTab } from './store';
  import { _ } from '@/language';
  import TemplatePreviewCard from './components/TemplateCard/TemplatePreviewCard.svelte';
  import {
    Block,
    f7,
    Progressbar,
    Tab,
    Tabs,
  } from 'framework7-svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import TemplateInputsPreview from './components/TemplateInputs/TemplateInputsPreview.svelte';

  let progressbar: Progressbar | undefined;

  $: if ($TemplateInputItems.sections.length > 1) {
    f7.progressbar.set(
      progressbar?.$$.ctx[1],
      ($TemplateIndex / $TemplateInputItems.sections.length) * 100,
    );
  }
</script>

<Page style="height: 100%; overflow: hidden">
  <Appbar slot="fixed" />
  <p />
  <Tabs style="height: 100%">
    <Tab tabActive={$TemplateSelectedTab === 'input'}>
      <Block class="no-margin-top">
        {#if $TemplateInputItems.sections.length > 1}
          <h2>{$TemplateInputItems.sections[$TemplateIndex].title}</h2>
          <p>{$TemplateInputItems.sections[$TemplateIndex].subtitle ?? ''}</p>
          <p />
          <Progressbar
            bind:this={progressbar}
            color="deeppurple"
            progress={0}
          />
          <br />
          <TemplateInputsPreview
            bind:inputs={$TemplateInputItems.sections[$TemplateIndex].inputs}
          />
        {:else}
          <h2>{$TemplateInputItems.title}</h2>
          <p>{$TemplateInputItems.subtitle ?? ''}</p>
          <br />
          <TemplateInputsPreview
            bind:inputs={$TemplateInputItems.sections[0].inputs}
          />
        {/if}
      </Block>
    </Tab>
    <Tab style="height: 100%" tabActive={$TemplateSelectedTab === 'output'}>
      <div style="height: 100%" class="middle-of-page">
        <div style="height: 190px; width: 340px">
          <TemplatePreviewCard />
        </div>
      </div>
    </Tab>
  </Tabs>
</Page>
