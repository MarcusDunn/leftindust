<script lang="ts">
  import { TemplateSelectedTab } from './store';
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
  import type { Template } from '.';
  import type { Writable } from 'svelte/store';

  export let template: Writable<Template>;
  export let selectedSectionIndex: Writable<number>;

  let progressbar: Progressbar | undefined;

  $: if ($template.sections.length > 1) {
    f7.progressbar.set(
      progressbar?.$$.ctx[1],
      ($selectedSectionIndex / $template.sections.length) * 100,
    );
  }
</script>

<Page style="height: 100%; overflow: hidden">
  <Appbar slot="fixed" />
  <p />
  <Tabs style="height: 100%">
    <Tab tabActive={$TemplateSelectedTab === 'input'}>
      <Block class="no-margin-top" style="overflow-y: scroll; height: 100%; padding-bottom: 20px;">
        {#if $template.sections.length > 1}
          <h2>{$template.sections[$selectedSectionIndex].title}</h2>
          <p>{$template.sections[$selectedSectionIndex].subtitle ?? ''}</p>
          <p />
          <Progressbar
            bind:this={progressbar}
            color="deeppurple"
            progress={0}
          />
          <br />
          <TemplateInputsPreview
            bind:inputs={$template.sections[$selectedSectionIndex].inputs}
          />
        {:else}
          <h2>{$template.title}</h2>
          <p>{$template.subtitle ?? ''}</p>
          <br />
          <TemplateInputsPreview
            bind:inputs={$template.sections[0].inputs}
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
