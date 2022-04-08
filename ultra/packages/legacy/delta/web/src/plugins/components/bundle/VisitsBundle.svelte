<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';

  import language from '../../../languages/index';
  import { Button, Row, Col } from 'framework7-svelte';
  import { openWizard, safeNavigate } from '../../../modules/NavigationModule';
  import { pin, pinned } from '../../../modules/PinModule';
  import { ACCOUNT } from '../../../store';
  import { AppViews } from '../../../modules/AppModule';
  
  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';
  import PinnableItemUI from '../../../ui/item/PinnableItemUI/PinnableItemUI.svelte';
  
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import VisitsReferencedEngine from '../../../engines/visits/VisitsReferencedEngine';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable;
  export let properties: NonNullable<Plugin['properties']>;
  export let quicklook = false;

  const { request, visits } = VisitsReferencedEngine({ selectable });
</script>

{#key $ACCOUNT}
  <PluginBundleUI
    title={properties.title}
    icon={properties.icon}
    color={properties.color}
    {dragger}
    shadow
  >
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if $visits.length > 0}
        <PinnableListUI>
          {#each $visits.filter((_, index) => index <= 3) as visit}
            <PinnableItemUI
              header={visit.event.date}
              title={visit.title}
              text={visit.description || language().placeholders.noDescription.text}
              pinned={pinned({
                id: visit.vid.id,
                type: visit.__typename,
              }, selectable)}
              on:pin={({ detail }) => pin(detail, {
                id: visit.vid.id,
                type: visit.__typename,
              }, selectable)}
              on:click={() => safeNavigate(`/visit/${JSON.stringify({
                id: visit.vid.id,
                type: visit.__typename,
              })}/`, AppViews.Clients)}
            />
          {/each}
        </PinnableListUI>
      {:else}
        <div style="margin-top: 40px">
          <PluginPlaceholderLayout
            title={language().visits.placeholders.noVisits.text}
            description={language().visits.placeholders.addVisitPlaceholder(
              `${language().positions.bottom.text} ${language().positions.left.text}`,
            ).text}
            link={{
              label: language().visits.placeholders.learnMore.text,
              href: '#',
            }}
          />
        </div>
      {/if}
    </RequestLayout>
    <Row slot="controls">
      <Col width="50">
        <Button
          class={`${quicklook ? 'disabled' : ''}`}
          color={properties.color}
          fill
          round
          on:click={() => openWizard('/wizard/visit/', {
            reference: {
              type: selectable.type,
              id: selectable.id,
            },
          })}>
          {language().visits.buttons.newVisit.text}
        </Button>
      </Col>
      <Col width="50">
        <Button
          color={properties.color}
          round
          outline
          disabled={$visits.length === 0}
          on:click={() => safeNavigate(`/visits/${JSON.stringify(selectable)}/`, AppViews.Clients)}
        >
          {language().buttons.viewAll.text}
        </Button>
      </Col>
    </Row>
  </PluginBundleUI>
{/key}