<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { SelectableRouteParams } from '@/routes';
  
  
  import { PluginTypes } from '@framework/plugins';
  
  import PatientEngine from '@framework/engines/patients/PatientEngine';
  import EventsReferencedEngine from '@framework/engines/events/EventsReferencedEngine';
  import { getPreviousEvents, getUpcomingEvents } from '@framework/modules/CalendarModule';
  import { WIZARD_ACTIVE } from '@framework/store';
  import { ClientsSelected } from '../../store/ClientsStore';

  import { Block } from 'framework7-svelte';
  
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';

  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  const { patient } = PatientEngine({ pids: [{ id: selectable.id }] });

  const { request, events } = EventsReferencedEngine({
    selectable,
  });

  $: previousEvents = getPreviousEvents($events).splice(0, 3);
  $: upcomingEvents = getUpcomingEvents($events).splice(0, 3);
</script>

<PageUI on:pageAfterIn={() => !$WIZARD_ACTIVE && ($ClientsSelected = [selectable])}>
  <svelte:fragment slot="fixed">
    <AppBarUI
      navigation={{ history: true }}
      {f7router}
    />
  </svelte:fragment>
  <Block class="no-margin-top">
    <Block>
      <RequestLayout {...$request} refetch={request.refetch} large middle>
        {#if $patient?.firstName}
          <h2>{$patient.firstName}'s Events</h2>
        {/if}
        <br />
        <br />
        {#if upcomingEvents.length > 0}
          <CollapsableContentUI title="Upcoming Events">
            <SpecificPluginGrid
              props={upcomingEvents.map((event) => {
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
            /> 
            <br />
          </CollapsableContentUI>
          <br />
        {/if}
        {#if previousEvents.length > 0}
          <CollapsableContentUI title="Past Events">
            <SpecificPluginGrid
              props={previousEvents.map((event) => {
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
            />
          </CollapsableContentUI>
        {/if}
      </RequestLayout>
    </Block>
  </Block>
</PageUI>
