<script lang="ts">
  import type { Calendar, CalendarOptions, EventApi } from '@fullcalendar/core';
  import type { Popover } from 'framework7/types';

  import { f7, PageContent } from 'framework7-svelte';
  import language from '@framework/languages';
  import addHours from 'date-fns/addHours';
  
  import { WIZARD_ACTIVE } from '@framework/store';
  import { getTrigger } from '@framework/modules/TriggerModule';
  
  import EventMutateEngine from '@framework/engines/events/EventMutateEngine';
  
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import CalendarUI, { CalendarUIOptions } from '@framework/ui/layout/CalendarUI/CalendarUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import SelectButtonUI from '@framework/ui/button/SelectButtonUI/SelectButtonUI.svelte';
  import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';
  import QuicklookUI from '@framework/ui/view/QuicklookUI/QuicklookUI.svelte';

  import { eventSources, mostRecentlyActiveEvent } from '@framework/modules/CalendarModule';
  import { EventWizardEvent, EventWizardActive } from './wizards/event/store';
  import { openWizard, openPopover } from '@framework/modules/NavigationModule';
  import { AppViews } from '@framework/modules/AppModule';
  import { tick } from 'svelte';

  let createMenu: Popover.Popover;

  let calendar: Calendar | undefined;
  let view = 'dayGridMonth';

  let activeEvent: EventApi | null | undefined = undefined;
  let quickLookModal: Popover.Popover;

  let options: CalendarOptions = {
    ...CalendarUIOptions,
    initialView: 'dayGridMonth',
    editable: !$WIZARD_ACTIVE,
    eventSources: [$EventWizardActive ? eventSources($EventWizardEvent.id).leftindust : eventSources().leftindust],
    eventDidMount: (calEvent) => {
      if (!$WIZARD_ACTIVE) {
        // Add accessible DOM id hooks to events
        f7.$(calEvent.el)
          .attr('id', calEvent.event.id);
      }
    },
    eventDrop: (calEvent) => {
      let end: Date;
      if (calEvent.event.end) {
        end = calEvent.event.end;
      } else if (calEvent.event.allDay) {
        end = new Date(calEvent.event.start ?? new Date());
        end.setDate(end.getDate() + 1);
      } else {
        end = addHours(calEvent.event.start ?? new Date(), 1);
      }

      if (!$WIZARD_ACTIVE) {
        void EventMutateEngine({
          eid: {
            id: calEvent.event.id,
          },
          allDay: calEvent.event.allDay,
          start: { unixMilliseconds: calEvent.event.start?.getTime() },
          end: { unixMilliseconds: end.getTime() },
        }, true);
        
      } else {
        $EventWizardEvent.allDay = calEvent.event.allDay;
        $EventWizardEvent.start = calEvent.event.start?.getTime() ?? 0;
        $EventWizardEvent.end = end.getTime();
      }
    },
    eventClick: (calEvent) => {
      if (!$EventWizardActive || ($EventWizardActive && calEvent.event.id === 'event-not-grayscale')) {
        activeEvent = calEvent.event;
        setTimeout(() => {
          openPopover(quickLookModal, calEvent.el);
        }, 10);
      }
    },
    eventResize: (calEvent) => {
      if (!$WIZARD_ACTIVE) {
        void EventMutateEngine({
          eid: {
            id: calEvent.event.id,
          },
          allDay: calEvent.event.allDay,
          start: { unixMilliseconds: calEvent.event.start?.getTime() },
          end: { unixMilliseconds: calEvent.event.end?.getTime() },
        }, true);
      } else {
        $EventWizardEvent.allDay = calEvent.event.allDay;
        $EventWizardEvent.start = calEvent.event.start?.getTime() ?? 0;
        $EventWizardEvent.end = calEvent.event.end?.getTime() ?? 0;
      }
    },
    dayCellDidMount: (event) => {
      event.el.addEventListener('dblclick', () => {
        if (!$WIZARD_ACTIVE) {
          const date = event.date.getTime();
          $EventWizardEvent.start = date;
          $EventWizardEvent.end = addHours(date, 1).getTime();

          openWizard('/wizard/event/');
        }
      });
    },
    loading: (loading) => {
      if (!loading) {
        title = calendar?.currentData.viewTitle;

        // TODO: Fullcalendar's hooks are just broken so I need to timeout before the events are "officially" rendered
        setTimeout(() => {
          calendar?.getEventById($EventWizardEvent.extendedProps?.edit)?.remove();
        }, 100);
      }
    },
  };

  const initializePosition = () => {
    if ($EventWizardActive) {
      view = 'timeGridWeek';
      calendar?.gotoDate($EventWizardEvent.start);
    }
  };

  $: title = calendar?.currentData.viewTitle;
  $: calendar?.changeView(view);

  $: (() => {
    calendar?.getEventById('wizard-event')?.remove();
    if ($EventWizardActive) calendar?.addEvent($EventWizardEvent);
  })();
  
  $: void tick().then(() => {
    if ($mostRecentlyActiveEvent) {
      calendar?.gotoDate($mostRecentlyActiveEvent?.startTime?.unixMilliseconds);
      
      calendar?.on('loading', (loading) => {
        if (!loading) {
          // The timeouts are a bit janky but fullcalendar doesn't have a way to listen for
          // view changes, so these timeouts do the trick
          
          // Wait for view to navigate to the correct month in calendar
          setTimeout(() => {
            activeEvent = calendar?.getEventById($mostRecentlyActiveEvent?.eid.id);
  
            setTimeout(() => {
              if (activeEvent) {
                openPopover(quickLookModal, `#${CSS.escape($mostRecentlyActiveEvent?.eid.id)}`);
                $mostRecentlyActiveEvent = undefined;
              }
            }, 20);
          }, 20);
        }
      });
    }
  });

  // @ts-expect-error
  $: calendar, initializePosition();

  getTrigger('refreshEvents').subscribe(() => {
    calendar?.refetchEvents();
  });

</script>

<style lang="scss">
  div {
    height: 100%;
    & :global(.ultra-calendar) {
      height: calc(100% - 70px);
    }
  }

  span {
    display: flex;

    h3 {
      font-size: 24px;
      margin: 10px 15px 0;
    }
  }
</style>

<QuicklookUI
  url={`/event/${JSON.stringify({ type: 'Event', id: activeEvent?.id ?? '' })}/`}
  view={AppViews.Clients}
  bind:instance={quickLookModal}
  color="pink"
/>

<PageUI style="overflow: hidden !important;" pageContent={false}>
  <MenuUI
    items={[
      {
        title: language().events.headers.event.text,
        text: language().events.descriptions.createDescription.text,
        icon: {
          f7: 'calendar_today',
          color: 'pink',
        },
        onClick: () => openWizard('/wizard/event/'),
      },
    ]}
    bind:instance={createMenu}
  />
  <AppBarUI slot="fixed">
    <svelte:fragment slot="left">
      <SelectButtonUI
        options={[
          {
            text: language().dates.month.text,
            value: 'dayGridMonth',
          },
          {
            text: language().dates.week.text,
            value: 'timeGridWeek',
          },
          {
            text: language().dates.day.text,
            value: 'timeGridDay',
          },
        ]}
        bind:value={view}
      />
    </svelte:fragment>
    <svelte:fragment slot="center">
      <span>
        <MenuButtonUI
          title={language().buttons.back.text}
          icon={{ f7: 'chevron_left_circle_fill', color: 'gray' }}
          on:click={() => calendar?.prev()}
        />
        <h3>{title ?? ''}</h3>
        <MenuButtonUI
          title={language().headers.today.text}
          icon={{ f7: 'calendar_today', color: 'purple' }}
          on:click={() => calendar?.today()}
        />
        <MenuButtonUI
          title={language().buttons.next.text}
          icon={{ f7: 'chevron_right_circle_fill', color: 'gray' }}
          on:click={() => calendar?.next()}
        />
      </span>
    </svelte:fragment>
    <svelte:fragment slot="right">
      {#if !$WIZARD_ACTIVE}
        <MenuButtonUI
          title={language().buttons.create.text}
          icon={{ f7: 'plus_circle_fill', color: 'purple' }}
          on:click={(event) => { openPopover(createMenu, event); }}
        />
      {/if}
    </svelte:fragment>
  </AppBarUI>
  <PageContent style="overflow: hidden !important">
    <div>
      <CalendarUI
        {options}
        grayscale={$EventWizardActive}
        bind:instance={calendar}
      />
    </div>
  </PageContent>
</PageUI>
