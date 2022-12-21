<script lang="ts">
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import type { Router } from 'framework7/types';
  import { operationStore, query } from '@urql/svelte';
  import { SurveyTemplateByIdQueryDocument, type Data, type TemplateFragmentFragment } from '@/api/server';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import TemplateTags from './components/TemplateTags/TemplateTags.svelte';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '../UI/components/Collapsable/CollapsableContent.svelte';
  import RecordCalculation from '../Record/components/RecordCalculation/RecordCalculation.svelte';
  import TemplateSectionSelectableCell from './components/TemplateSectionCell/TemplateSectionSelectableCell.svelte';
  import { writable, type Writable } from 'svelte/store';
  import Cells from '../UI/components/Cells/Cells.svelte';
  import IFrame from '../View/components/IFrame/IFrame.svelte';
  import CollapsableContentPlaceholder from '../UI/components/Collapsable/CollapsableContentPlaceholder.svelte';
  import NodesModal from '../Nodes/components/NodesModal/NodesModal.svelte';
  import { getTrigger } from '../Triggers';
  import { templateCalculationNodes } from './';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  
  export let quicklook = false;

  const widthBreakpoint = 700;
  
  let sectionLoaded = false;
  let isMobile = false;

  let template: Writable<TemplateFragmentFragment | undefined> = writable();

  let width = window.innerWidth;
  
  const selectedSection: Writable<Data[]> = writable([]);
  const selectedSectionIndex: Writable<number> = writable(0);
  
  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(SurveyTemplateByIdQueryDocument, {
    surveyTemplateId: { value: data.id },
  });

  // TODO: Remove this in favour of graphcache
  getTrigger('template-update').subscribe(() => request.reexecute());

  $: calculations = $template?.calculations.map((calculation) => ({
    ...calculation,
    calculationModalOpen: false,
    editor: undefined,
  })) ?? [];

  $: $template = $request.data?.surveyTemplateById;

  query(request);

  $: if ($template?.sections[0] && !sectionLoaded) {
    $selectedSection = [{
      id: $template.sections[0].id.value,
      type: $template.sections[0].__typename,
    }];
    sectionLoaded = true;
  }

  $: $selectedSectionIndex = $template?.sections.findIndex((section) => section.id.value === $selectedSection[0]?.id) ?? 0;

  $: isMobile = width > widthBreakpoint;
</script>

<svelte:window bind:innerWidth={width} />

<Page>
  <Appbar
    slot="fixed"
    close={{ popup: quicklook }}
    history={!quicklook}
    {f7router}
  />
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if $template}
      <Profile>
        <h2 slot="title">{$template.title}</h2>
        <TemplateTags template={$template} slot="tags" />
        {#if $template.subtitle}
          <p>{$template.subtitle}</p>
        {/if}
      </Profile>
      <Block style="margin-left: 35px;margin-right: 35px">
        <CollapsableContent title="Preview">
          <div
            class="display-flex"
            style={`
              margin-top: 4px;
              height: ${isMobile ? '700px' : 'auto'};
              margin-bottom: ${isMobile ? '-80px' : 'auto'};
            `}>
            <div
              style={`
                width: ${isMobile ? '375px' : '100%'};
                padding: ${isMobile ? '20px 0' : 0};
                overflow-y: scroll;
                flex-shrink: 0;
              `}
            >
              <Cells selected={$selectedSection}>              
                {#each $template.sections as section}
                  <TemplateSectionSelectableCell
                    {section}
                    sections={$template.sections}
                    selected={selectedSection}
                    multiselect={false}
                  />
                {/each}
              </Cells>
            </div>
            {#if isMobile}
              <div style="width: 100%">
                <IFrame
                  style="margin-top: -25px;margin-bottom: 0;"
                  views={[
                    {
                      url: '/wizard/template/preview/',
                      selected: true,
                      props: {
                        template,
                        selectedSectionIndex,
                      },
                    },
                  ]}
                />
              </div>
            {/if}
          </div>
          <br />
        </CollapsableContent>
        <p />
        <CollapsableContent title="Calculations">
          {#if $template.calculations.length > 0}
            {#each calculations as calculation}
              <NodesModal
                nodes={templateCalculationNodes}
                state={JSON.parse(calculation.calculation ?? '{}')}
                bind:open={calculation.calculationModalOpen}
                bind:editor={calculation.editor}
                editable={false}
              />
              <RecordCalculation
                {calculation}
                on:click={() => (calculation.calculationModalOpen = true)}
              />
            {/each}
          {:else}
            <CollapsableContentPlaceholder center>
              No calculations found...
            </CollapsableContentPlaceholder>
          {/if}
        </CollapsableContent>
      </Block>
    {/if}
  </Request>
</Page>
