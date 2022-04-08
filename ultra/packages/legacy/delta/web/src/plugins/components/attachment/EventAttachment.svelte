<script lang="ts">
  import type { MappedEvent } from '../../../modules/CalendarModule';
  import type { Writable } from 'svelte/store';
  import type { EventsReferencedEngineRequest } from '../../../engines/events/EventsReferencedEngine';
  import type { EventsQueryResult } from '../../../requests/queries';
  import type { ReadableQuery } from 'svelte-apollo-client';
  
  import type { Selectable } from '../../../modules/SelectModule';
  import type { BasicEventFragment } from '../../../requests/fragments';
  
  import language from '../../../languages';
  import { sortEvents } from '../../../modules/CalendarModule';
  
  import EventsReferencedEngine from '../../../engines/events/EventsReferencedEngine';
  import EventsGenericEngine from '../../../engines/events/EventsGenericEngine';
  
  import { Block, Col, Row } from 'framework7-svelte';
  
  import PageUI from '../../../ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '../../../ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import InsetListUI from '../../../ui/list/InsetListUI/InsetListUI.svelte';
  import CollapsableContentPlaceholderUI
    from '../../../ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
    
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import EventCheckboxItem from '../../../components/item/event/EventCheckboxItem.svelte';
  
  export let attachments: Writable<string[]>;
  export let multiple: boolean;
  export let submit: () => void;
  export let back: boolean;

  export let filterVisits = false;

  export let reference: Selectable<'Patient' | 'Doctor'> | undefined = undefined;

  let events: Writable<MappedEvent<BasicEventFragment>[]>;
  let request: ReadableQuery<EventsReferencedEngineRequest | EventsQueryResult>;

  if (reference) {
    const result = EventsReferencedEngine({ selectable: reference });
    events = result.events;
    // I'm too lazy to debug intersecting types: the type signature is correct
    // @ts-expect-error
    request = result.request;
  } else {
    const result = EventsGenericEngine({ range: {
      start: {
        unixMilliseconds: new Date().getTime() - 31556926,
      },
      end: {
        unixMilliseconds: new Date().getTime() + 31556926,
      },
    } });

    events = result.events;
    // @ts-expect-error
    request = result.request;
  }
</script>

<PageUI>
  <AppBarUI
    slot="fixed"
    navigation={{ back, close: { popover: !back } }}
  >
    <svelte:fragment slot="right">
      <MenuButtonUI
        title={language().buttons.done.text}
        icon={{ f7: 'checkmark_circle_fill', color: 'purple' }}
        on:click={submit}
      />
    </svelte:fragment>
  </AppBarUI>
  <Block>
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if $events.length > 0}
        <Row class="no-gutter">
          {#each sortEvents($events) as event}
            {#if !filterVisits || (filterVisits && !event.visit)}
              <Col width="33" style="margin-bottom: 10px">
                <InsetListUI>
                  <EventCheckboxItem {event} bind:selected={$attachments} {multiple} />
                </InsetListUI>
              </Col>
            {/if}
          {/each}
        </Row>
      {:else}
        <br />
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noResults.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
  </Block>
</PageUI>