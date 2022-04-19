<script lang="ts">
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
  } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';
  import { TemplateInputItems, TemplateSelectedTab } from './store';

  import IFrame from '../View/components/IFrame/IFrame.svelte';
  import WizardSplit from '../Wizard/components/WizardSplit/WizardSplit.svelte';
  import TemplateCategoryInputs from './components/TemplateInputs/TemplateCategoryInputs.svelte';
  import TemplateSections from './components/TemplateSections/TemplateSections.svelte';
  import TemplateSectionInputs from './components/TemplateSection/TemplateSectionInputs.svelte';
</script>

<WizardSplit
  title="New Template"
  subtitle="Create  a new template"
  color="deeppurple"
>
  <svelte:fragment slot="appbar">
    <Segmented strong style="width: 200px;">
      <Button
        active={$TemplateSelectedTab === 'input'}
        on:click={() => ($TemplateSelectedTab = 'input')}
      >
        {$_('generics.input')}
      </Button>
      <Button
        active={$TemplateSelectedTab === 'output'}
        on:click={() => ($TemplateSelectedTab = 'output')}
      >
        {$_('generics.output')}
      </Button>
    </Segmented>
  </svelte:fragment>

  {#if $TemplateInputItems.sections.length > 1}
    <TemplateSectionInputs
      bind:title={$TemplateInputItems.title}
      bind:subtitle={$TemplateInputItems.subtitle}
    />
    <br />
    <br />
  {/if}
  <Tabs>
    <Tab tabActive={$TemplateSelectedTab === 'input'}>
      <TemplateSections bind:sections={$TemplateInputItems.sections} />
    </Tab>
    <Tab tabActive={$TemplateSelectedTab === 'output'}>
      <TemplateCategoryInputs bind:sections={$TemplateInputItems.sections} />
    </Tab>
  </Tabs>
  <br />
  <svelte:fragment slot="detail">
    <IFrame
      views={[
        {
          url: '/template/preview/',
          selected: true,
        },
      ]}
    />
  </svelte:fragment>
</WizardSplit>
