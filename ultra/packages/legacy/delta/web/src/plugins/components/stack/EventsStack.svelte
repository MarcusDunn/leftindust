<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';

  import { ACCOUNT } from '../../../store/index';

  import { PluginTypes } from '../../index';

  import language from '../../../languages/index';
  import EventsGenericEngine from '../../../engines/events/EventsReferencedEngine';
  import { getPreviousEvents, getUpcomingEvents } from '../../../modules/CalendarModule';

  import { Button } from 'framework7-svelte';

  import PluginStackUI from '../../../ui/plugin/PluginStackUI/PluginStackUI.svelte';

  import RequestLayout from '../../../components/layout/RequestLayout.svelte';

  import SpecificPluginGrid from '../../../components/grid/SpecificPluginGrid.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable;
  export let properties: NonNullable<Plugin['properties']>;

  $ACCOUNT.database.layout.pinned['Patient'][selectable.id] ??= [];

  const { request, events } = EventsGenericEngine({
    selectable,
  });

  $: previousEvents = getPreviousEvents($events);
  $: upcomingEvents = getUpcomingEvents($events);

</script>

<PluginStackUI
  title={properties.title}
  color={properties.color}
  {dragger}
  opened
>
  <RequestLayout {...$request} refetch={request.refetch} middle>
    {#if $events.length > 0}
      <SpecificPluginGrid
        props={$events.map((event) => {
          if (event.__typename) {
            return {
              id: event.__typename,
              selectable: {
                id: event.eid.id,
                type: event.__typename,
              },
            };
          }
        })}
        type={PluginTypes.Widget}
        fixed
      />
    {:else}
      <PluginPlaceholderLayout
        title={language().events.placeholders.noEvents.text}
        description={language().events.placeholders.addEventPlaceholder(
          `${language().positions.top.text} ${language().positions.right.text}`,
        ).text}
        link={{
          label: language().events.placeholders.learnMore.text,
          href: language().events.placeholders.learnMore.link,
        }}
      />
    {/if}
  </RequestLayout>
  <div class="display-flex" slot="controls">
    <Button
      color={properties.color}
      small
      fill
      round
    >
      {language().events.buttons.newEvent.text}
    </Button>
    <Button
      color={properties.color}
      small
      round
      outline
      disabled={$events.length == 0}
    >
      {language().buttons.viewAll.text}
    </Button>
  </div>
</PluginStackUI>
