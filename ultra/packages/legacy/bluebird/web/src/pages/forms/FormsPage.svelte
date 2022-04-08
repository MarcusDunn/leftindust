<script lang="ts">
  import type { Popover, Router } from 'framework7/types';
  
  import language from '@framework/languages';
  import { openPopover, openWizard } from '@framework/modules/NavigationModule';
  import { WIZARD_ACTIVE } from '@framework/store';
  
  import { Tabs, Tab } from 'framework7-svelte';
  
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';
  import SelectButtonUI from '@framework/ui/button/SelectButtonUI/SelectButtonUI.svelte';
  import SurveysTab from './tabs/surveys/SurveysTab.svelte';

  let createMenu: Popover.Popover;

  enum FormTabs {
    Forms = 'forms',
    Surveys = 'surveys'
  }

  let tab: FormTabs = FormTabs.Surveys;

  export let f7router: Router.Router;
  export let f7route: Router.Route;
</script>

<PageUI>
  <svelte:fragment slot="fixed">
    <MenuUI
      items={[
				{
          title: language().forms.headers.survey.text,
          text: language().forms.placeholders.addSurveyDescription.text,
          icon: {
            f7: 'doc_person_fill',
            color: 'blue',
          },
					onClick: () => openWizard('/wizard/survey/template/'),
        },
      ]}
      bind:instance={createMenu}
    />
    <AppBarUI>
      <svelte:fragment slot="left">
        <SelectButtonUI
          options={[
            {
              text: 'Forms',
              value: FormTabs.Forms,
            },
            {
              text: 'Surveys',
              value: FormTabs.Surveys,
            },
          ]}
          bind:value={tab}
        />
      </svelte:fragment>
      <svelte:fragment slot="right">
        {#if !$WIZARD_ACTIVE}
          <MenuButtonUI
            title={language().buttons.create.text}
            icon={{ f7: 'plus_circle_fill', color: 'deeppurple' }}
            on:click={(event) => openPopover(createMenu, event)}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>

  <Tabs>
    <Tab tabActive={tab === FormTabs.Forms} />
    <Tab tabActive={tab === FormTabs.Surveys}>
      <SurveysTab {f7router} {f7route} />
    </Tab>
  </Tabs>
</PageUI>