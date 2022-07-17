<script lang="ts">
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
  } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';
  import { TemplateInputItems, TemplateNodesModalOpen, TemplateSelectedTab, TemplateCalculations as TemplateCalculationsStore } from './store';
  import { fade } from 'svelte/transition';

  import IFrame from '../View/components/IFrame/IFrame.svelte';
  import WizardSplit from '../Wizard/components/WizardSplit/WizardSplit.svelte';
  import TemplateCategoryInputs from './components/TemplateInputs/TemplateCategoryInputs.svelte';
  import TemplateSections from './components/TemplateSections/TemplateSections.svelte';
  import TemplateSectionInputs from './components/TemplateSection/TemplateSectionInputs.svelte';
  import TemplateCalculations from './components/TemplateCalculations/TemplateCalculations.svelte';
  import { templateForm } from '.';

  const { form, errors, data } = templateForm();

  let ref: HTMLFormElement;
</script>

<WizardSplit
  title="New Template"
  subtitle="Create a new template"
  color="deeppurple"
  on:submit={() => ref?.requestSubmit()}
>
  <svelte:fragment slot="appbar">
    {#if !$TemplateNodesModalOpen}
      <div transition:fade={{ duration: 100 }}>
        <Segmented
          strong
          style="width: 200px;"
        >
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
      </div>
    {/if}
  </svelte:fragment>

  <form use:form bind:this={ref}>
    <Tabs>
      <Tab tabActive={$TemplateSelectedTab === 'input'}>
        {#if $TemplateInputItems.sections.length > 1}
          <TemplateSectionInputs
            bind:title={$TemplateInputItems.title}
            bind:subtitle={$TemplateInputItems.subtitle}
            {errors}
            {data}
          />
          <br />
          <br />
        {/if}
        <TemplateSections
          bind:sections={$TemplateInputItems.sections}
          {errors}
          {data}
        />
        <TemplateCalculations
          bind:calculations={$TemplateCalculationsStore}
          {errors}
          {data}
        />
      </Tab>
      <Tab tabActive={$TemplateSelectedTab === 'output'}>
        <TemplateSectionInputs
          bind:title={$TemplateInputItems.title}
          bind:subtitle={$TemplateInputItems.subtitle}
          {errors}
          {data}
        />
        <br />
        <br />
        <TemplateCategoryInputs bind:sections={$TemplateInputItems.sections} />
      </Tab>
    </Tabs>
  </form>
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
