<script lang="ts">
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
  } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';
  import { Template, TemplateNodesModalOpen, TemplateSelectedTab, TemplateCalculations as TemplateCalculationsStore } from './store';
  import { fade } from 'svelte/transition';

  import IFrame from '../View/components/IFrame/IFrame.svelte';
  import WizardSplit from '../Wizard/components/WizardSplit/WizardSplit.svelte';
  import TemplateSections from './components/TemplateSections/TemplateSections.svelte';
  import TemplateSectionInputs from './components/TemplateSection/TemplateSectionInputs.svelte';
  import TemplateCalculations from './components/TemplateCalculations/TemplateCalculations.svelte';
  import { templateForm } from '.';
  import TemplateCategoryInputs from './components/TemplateInputs/TemplateCategoryInputs.svelte';
  import { closeWizard } from '../Wizard';

  const closeWizardHandler = () => {
    $Template = JSON.parse(JSON.stringify($Template));
    $TemplateCalculationsStore = [];
    closeWizard();
  };

  const { form, errors, data } = templateForm(closeWizardHandler);

  let ref: HTMLFormElement;
</script>

<WizardSplit
  title="New Template"
  subtitle="Create a new template"
  color="deeppurple"
  on:submit={() => ref?.requestSubmit()}
  on:close={() => closeWizardHandler()}
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
        {#if $Template.sections.length > 1}
          <TemplateSectionInputs
            bind:title={$Template.title}
            bind:subtitle={$Template.subtitle}
            {data}
            {errors}
          />
          <br />
          <br />
        {/if}
        <TemplateSections
          bind:sections={$Template.sections}
          {errors}
          {data}
        />
        <TemplateCalculations
          bind:calculations={$TemplateCalculationsStore}
          {errors}
        />
      </Tab>
      <Tab tabActive={$TemplateSelectedTab === 'output'}>       
        <TemplateSectionInputs
          bind:title={$Template.title}
          bind:subtitle={$Template.subtitle}
          {data}
          {errors}
        />
        <br />
        <br />
        <TemplateCategoryInputs bind:sections={$Template.sections} />
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
