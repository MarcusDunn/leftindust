<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { SelectableRouteParams } from '@/routes';
  import type { Calendar, CalendarOptions } from '@fullcalendar/core';

  import {
    Block,
    Button,
    Chip,
    Icon,
} from 'framework7-svelte';

  import language from '@framework/languages';
  import EventEngine from '@framework/engines/events/EventEngine';
  import { WIZARD_ACTIVE } from '@framework/store';
  import { PluginTypes } from '@framework/plugins';
  import { eventSources, showEventInCalendar } from '@framework/modules/CalendarModule';
  import { ClientsSelected } from '../../store/ClientsStore';
  import { AppViews, APP_ACTIVE_TAB } from '@framework/modules/AppModule';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import ProfileUI from '@framework/ui/layout/ProfileUI/ProfileUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';
  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
  import CalendarUI, { CalendarUIOptions } from '@framework/ui/layout/CalendarUI/CalendarUI.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import { openWizard, safeNavigate } from '@framework/modules/NavigationModule';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;

  let calendar: Calendar | undefined;
  let options: CalendarOptions;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  const { request, event } = EventEngine({
    eids: [{ id: selectable.id }],
  });

  $: options = {
    ...CalendarUIOptions,
    initialView: 'timeGridWeek',
    eventSources: [eventSources(selectable.id).leftindust],
    initialDate: $event?.startTime?.unixMilliseconds,
    scrollTime: new Date($event?.startTime?.unixMilliseconds - 3600000).toLocaleTimeString('en-GB'),
  };

  const pageAfterIn = () => {
    if (!$WIZARD_ACTIVE && !quicklook)
      event.subscribe((value) => {
        if (value)
          $ClientsSelected = [
            ...value.doctors.map((doctor) => ({ type: 'Doctor', id: doctor.id?.value })),
            ...value.patients.map((doctor) => ({ type: 'Patient', id: doctor.pid.id })),
          ] as Selectable[];
      });
  };
</script>

<style lang="scss">
  div {
    margin-top: -10px;
    & :global(.ultra-calendar) {
      height: 350px;
    }
  }
</style>

<PageUI on:pageAfterIn={pageAfterIn}>
  <svelte:fragment slot="fixed">
    <AppBarUI
      navigation={{ close: quicklook, history: !quicklook }}
      {f7router}
    >
      <svelte:fragment slot="right">
        {#if !WIZARD_ACTIVE || !quicklook}
          <MenuButtonUI
            title={language().buttons.edit.text}
            icon={{ f7: 'pencil_outline', color: 'gray' }}
            on:click={() => openWizard('/wizard/event/', {
              selectable: {
                type: $event?.__typename,
                id: $event?.eid.id,
              },
            })}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>
  <RequestLayout {...$request} refetch={request.refetch} large middle>
    <ProfileUI drawer={{ visible: false }}>
      <h2 slot="title">{$event.title}</h2>
      <svelte:fragment slot="tags">
      <Chip
        text={$event.date}
        mediaBgColor="pink"
        outline
      >
        <span slot="media"><Icon f7="calendar" /></span>
      </Chip>
      </svelte:fragment>
    </ProfileUI>
    <Block style="margin-left: 25px;margin-right: 25px">
      <CollapsableContentUI title={language().events.headers.calendar.text}>
        <svelte:fragment slot="controls">
          {#if !(quicklook && $APP_ACTIVE_TAB === AppViews.Calendar)}
            <Button
              color="pink"
              small
              outline
              round
              on:click={() => showEventInCalendar($event)}
              popoverClose
            >
              {language().events.buttons.seeInCalendar.text}
            </Button>
          {/if}
        </svelte:fragment>
        <div>
          <CalendarUI
            {options}
            grayscale
            bind:instance={calendar}
          />
        </div>
        <br />
      </CollapsableContentUI>
      <br />
      <CollapsableContentUI title={language().headers.description.text}>
        <p>{$event.description ?? language().placeholders.noDescription.text}</p>
        <br />
      </CollapsableContentUI>
      <br />
      <CollapsableContentUI title={language().clients.people.title.text}>
        {#if [...$event.patients, ...$event.doctors].length > 0}
          <SpecificPluginGrid
            props={[...$event.patients, ...$event.doctors].map((person) => ({
              id: person.__typename,
              selectable: {
                id: ('pid' in person) ? person.pid.id : person.did.id,
                type: person.__typename,
              },
              quicklook,
            }))}
            type={PluginTypes.Widget}
            fixed
          />
        {:else}
          <CollapsableContentPlaceholderUI center>
            {language().clients.people.placeholders.notFound.text}
          </CollapsableContentPlaceholderUI>
        {/if}
        <div class="display-flex" slot="controls">
          <Button
            color="purple"
            small
            outline
            round
            disabled={[...$event.patients, ...$event.doctors].length < 2}
            on:click={() => safeNavigate(`/people/${JSON.stringify([...$event.patients, ...$event.doctors].map((person) => ({
              id: ('pid' in person) ? person.pid.id : person.did.id,
              type: person.__typename,
            })))}/`, AppViews.Clients)}
          >
            {language().buttons.viewAll.text}
          </Button>
        </div>
        <br />
      </CollapsableContentUI>
      <br />
    </Block>
  </RequestLayout>
  <br />
  <br />
</PageUI>
