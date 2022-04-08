<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';

  import language from '../../../languages/index';
  import EventsReferencedEngine from '../../../engines/events/EventsReferencedEngine';
  import { getPreviousEvents, getUpcomingEvents } from '../../../modules/CalendarModule';
  import { openWizard, safeNavigate } from '../../..//modules/NavigationModule';

  import {
    Button,
    Col,
    Row,
    Segmented,
    BlockFooter,
  } from 'framework7-svelte';

  import { AppViews } from '../../../modules/AppModule';

  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';
  import PinnableItemUI from '../../../ui/item/PinnableItemUI/PinnableItemUI.svelte';

  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';
  import { ACCOUNT } from '../../../store';
  import { pin, pinned } from '../../../modules/PinModule';

  export let dragger: () => void | undefined;
  export let selectable: Selectable<'Patient' | 'Doctor'>;
  export let properties: NonNullable<Plugin['properties']>;
  export let quicklook = false;

  const { request, events } = EventsReferencedEngine({
    selectable,
  });

  enum EventsTabs {
    Past = 'past',
    Upcoming = 'upcoming'
  }

  let currentTab: EventsTabs = EventsTabs.Upcoming;

  $: previousEvents = getPreviousEvents($events).splice(0, 3);
  $: upcomingEvents = getUpcomingEvents($events).splice(0, 3);
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
      {#if $events.length > 0}
        <Segmented strong style="margin-top: 10px; margin-bottom: 25px">
          <Button
            active={currentTab === EventsTabs.Past}
            on:click={() => {
              currentTab = EventsTabs.Past;
            }}
          >
            Past Events
          </Button>
          <Button
            active={currentTab === EventsTabs.Upcoming}
            on:click={() => {
              currentTab = EventsTabs.Upcoming;
            }}
          >
            Upcoming Events
          </Button>
        </Segmented>
        <PinnableListUI>
          {#if currentTab === EventsTabs.Past && previousEvents.length === 0}
            <BlockFooter>No past events availble</BlockFooter>
          {:else if currentTab === EventsTabs.Upcoming && upcomingEvents.length === 0}
            <BlockFooter>No upcoming events availble</BlockFooter>
          {/if}
            {#each (currentTab === EventsTabs.Past ? previousEvents : upcomingEvents) as event}
              <PinnableItemUI
                after={event.interval}
                header={event.date}
                title={event.title}
                text={event.description || language().placeholders.noDescription.text}
                pinned={pinned({
                  id: event.eid.id,
                  type: event.__typename,
                }, selectable)}
                on:pin={({ detail }) => pin(detail, {
                  id: event.eid.id,
                  type: event.__typename,
                }, selectable)}
                on:click={() => safeNavigate(`/event/${JSON.stringify({
                  id: event.eid.id,
                  type: event.__typename,
                })}/`, AppViews.Clients)}
              />
            {/each}
          </PinnableListUI>
      {:else}
        <div style="margin-top: 40px">
          <PluginPlaceholderLayout
            title={language().events.placeholders.noEvents.text}
            description={language().events.placeholders.addEventPlaceholder(
              `${language().positions.bottom.text} ${language().positions.left.text}`,
            ).text}
            link={{
              label: language().events.placeholders.learnMore.text,
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
          on:click={() => openWizard('/wizard/event/', {
            selectables: [{
              type: selectable.type,
              id: selectable.id,
            }],
          })}>
          {language().events.buttons.newEvent.text}
        </Button>
      </Col>
      <Col width="50">
        <Button
          color={properties.color}
          round
          outline
          disabled={$events.length === 0}
          on:click={() => safeNavigate(`/events/${JSON.stringify(selectable)}/`, AppViews.Clients)}
        >
          {language().buttons.viewAll.text}
        </Button>
      </Col>
    </Row>
  </PluginBundleUI>
{/key}