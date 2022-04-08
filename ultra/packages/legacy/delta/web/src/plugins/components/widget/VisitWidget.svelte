<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';
  import type { Popover } from 'framework7/types';
  
  import { openPopover, safeNavigate } from '../../../modules/NavigationModule';
  import VisitsSpecificEngine from '../../../engines/visits/VisitsSpecificEngine';
  import { pin, pinned } from '../../../modules/PinModule';
  
  import language from '../../../languages/index';

  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  import QuicklookUI from '../../../ui/view/QuicklookUI/QuicklookUI.svelte';
  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  import { AppViews } from '../../../modules/AppModule';
  
  import { ACCOUNT } from '../../../store';
  import PinButtonUI from '../../../ui/button/PinButtonUI/PinButtonUI.svelte';
  
  export let dragger: () => void | undefined;
  export let selectable: Selectable<'Visit'>;
  export let reference: Selectable | undefined = undefined;
  export let properties: NonNullable<Plugin['properties']>;
  export let attachments: Writable<Selectable[]> | undefined = undefined;

  export let quicklook = false;

  const { visits } = VisitsSpecificEngine({
    vids: [{ id: selectable.id }],
  });

  let quicklookPopover: Popover.Popover;

  const url = `/visit/${JSON.stringify(selectable)}/`;
</script>

<QuicklookUI
  {url}
  view={AppViews.Clients}
  color={properties.color}
  bind:instance={quicklookPopover}
/>

{#key $ACCOUNT}
  <PluginWidgetUI
    header={$visits[0]?.event.date || ''}
    title={$visits[0]?.title || ''}
    loading={!$visits?.[0]}
    description={$visits[0]?.description || language().placeholders.noDescription.text}
    color={properties.color}
    {dragger}
    shadow={!attachments}
  >
    <svelte:fragment slot="before-title">
      {#if reference}
        {#key $ACCOUNT}
          <PinButtonUI
            pinned={pinned({
              id: $visits[0].vid.id,
              type: $visits[0].__typename,
            }, reference)}
            on:pin={({ detail }) => reference && pin(detail, {
              id: $visits[0].vid.id,
              type: $visits[0].__typename,
            }, reference)}
          />
        {/key}
      {/if}
    </svelte:fragment>
    <svelte:fragment slot="controls">
      <Row>
        <Col width="50">
          {#if attachments}
            <Button
              round
              fill
              color="red"
              on:click={() => {
                if (attachments && $attachments)
                  $attachments = $attachments.filter((attachment) => attachment.id !== selectable.id);
              }}
            >
              <Icon f7="trash_fill" />
              {language().buttons.delete.text}
            </Button>
          {:else}
            <Button
              round
              fill
              on:click={() => safeNavigate(url, AppViews.Clients)}
            >
              {language().visits.buttons.viewVisit.text}
            </Button>
          {/if}
        </Col>
        <Col width="50">
          <div
            class={`${quicklook ? 'disabled' : ''}`}
            on:click={(event) => openPopover(quicklookPopover, event)}
          >
            <Button
              round
              outline
              color="blue"
            >
              <Icon f7="eye_fill" />
              {language().buttons.quicklook.text}
            </Button>
          </div>
        </Col>
      </Row>
    </svelte:fragment>
  </PluginWidgetUI>
{/key}