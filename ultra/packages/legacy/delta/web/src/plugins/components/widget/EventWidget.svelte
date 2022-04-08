<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';
  import type { Popover } from 'framework7/types';
  
  import { openPopover, safeNavigate } from '../../../modules/NavigationModule';
  import EventsSpecificEngine from '../../../engines/events/EventsSpecificEngine';
  import { pin, pinned } from '../../../modules/PinModule';
  
  import language from '../../../languages/index';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  
  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  import QuicklookUI from '../../../ui/view/QuicklookUI/QuicklookUI.svelte';
  import PinButtonUI from '../../../ui/button/PinButtonUI/PinButtonUI.svelte';
  import MenuUI from '../../../ui/controller/MenuUI/MenuUI.svelte';
   
  import { ACCOUNT } from '../../../store';
  import { AppViews } from '../../../modules/AppModule';
  
  export let dragger: () => void | undefined;
  export let selectable: Selectable<'Event'>;
  export let reference: Selectable | undefined = undefined;
  export let properties: NonNullable<Plugin['properties']>;
  export let attachments: Writable<Selectable[]> | undefined = undefined;

  export let quicklook = false;

  const { events } = EventsSpecificEngine({
    events: [{ id: selectable.id }],
  });

  let quicklookPopover: Popover.Popover;
  let menu: Popover.Popover;

  const url = `/event/${JSON.stringify(selectable)}/`;

</script>

<QuicklookUI
  {url}
  view={AppViews.Clients}
  color={properties.color}
  bind:instance={quicklookPopover}
/>

<MenuUI
  items={[
    {
      title: language().buttons.edit.text,
      text: language().events.descriptions.editDescription.text,
      icon: {
        f7: 'pencil_outline',
        color: 'gray',
      },
    },
    {
      title: language().buttons.delete.text,
      text: language().events.descriptions.deleteDescription.text,
      icon: {
        f7: 'trash_fill',
        color: 'red',
      },
    },
  ]}
  bind:instance={menu}
/>

{#key $ACCOUNT}
  <PluginWidgetUI
    header={$events[0]?.date || ''}
    title={$events[0]?.title || ''}
    loading={!$events?.[0]}
    description={$events[0]?.description || language().placeholders.noDescription.text}
    color={properties.color}
    {dragger}
    shadow={!attachments}
  >
    <svelte:fragment slot="before-title">
      {#key $ACCOUNT}
        {#if reference}
          <PinButtonUI
            pinned={pinned({
              id: $events[0].eid.id,
              type: $events[0].__typename,
            }, reference)}
            on:pin={({ detail }) => reference && pin(detail, {
              id: $events[0].eid.id,
              type: $events[0].__typename,
            }, reference)}
          />
        {/if}
      {/key}
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
              {language().events.buttons.viewEvent.text}
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