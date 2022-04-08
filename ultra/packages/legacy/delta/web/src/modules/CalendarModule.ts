import type { BasicEventFragment } from '../requests/fragments';
import type { Event } from '../schemas/leftindust.schema';

import format from 'date-fns/format';
import intervalToDuration from 'date-fns/intervalToDuration';
import type { EventInput } from '@fullcalendar/core';
import EventsGenericEngine from '../engines/events/EventsGenericEngine';
import { writable } from 'svelte/store';
import { safelyExitWizard } from './NavigationModule';
import { AppViews, APP_ACTIVE_TAB } from './AppModule';
import { f7 } from 'framework7-svelte';

export type EventDates = {
  eid: Event['eid'];
  startTime: Event['startTime'];
  endTime: Event['endTime'];
  allDay: Event['allDay'];
}

export type MappedEvent<T extends EventDates = EventDates> = (T & {
  date?: string;
  interval?: string;
});

export type EventSources = (id?: string) => {
  leftindust: (
    data: { start: Date, end: Date, startStr: string, endStr: string, timeZone: string },
    successCallback: (e: EventInput[]) => void,
    failureCallback: (error: { message: string }) => void,
  ) => void;
}

export const mapEvents = <T extends EventDates = EventDates>(events: T[]): MappedEvent<T>[] =>
  events.map((event) => {
    let date: string;
    let interval: string;

    // Get date string
    if (event.allDay) {
      // Add date range if allDay
      date = `${format(new Date(event.startTime?.unixMilliseconds), 'PP')}-${format(
        new Date(event.endTime?.unixMilliseconds),
        'PP',
      )}`;
    } else {
      // Add time range if not allDay
      date = `${format(event.startTime?.unixMilliseconds, 'PP')} @
      ${format(new Date(event.startTime?.unixMilliseconds), 'p')}-${format(new Date(event.endTime?.unixMilliseconds), 'p')}`;
    }

    // Get interval
    const intervalObject = intervalToDuration({
      start: new Date(event.startTime?.unixMilliseconds),
      end: new Date(),
    });

    // Get interval as string
    if (intervalObject.years && intervalObject.years > 0) {
      interval = `${intervalObject.years} year${intervalObject.years !== 1 ? 's' : ''}`;
    } else if (intervalObject.months && intervalObject.months > 0) {
      interval = `${intervalObject.months} month${intervalObject.months !== 1 ? 's' : ''}`;
    } else if (intervalObject.weeks && intervalObject.weeks > 0) {
      interval = `${intervalObject.weeks} week${intervalObject.weeks !== 1 ? 's' : ''}`;
    } else if (intervalObject.days && intervalObject.days > 0) {
      interval = `${intervalObject.days} day${intervalObject.days !== 1 ? 's' : ''}`;
    } else if (intervalObject.hours && intervalObject.hours > 0) {
      interval = `${intervalObject.hours} hour${intervalObject.hours !== 1 ? 's' : ''}`;
    } else {
      interval = `${intervalObject.minutes} minute${intervalObject.minutes !== 1 ? 's' : ''}`;
    }

    // Set past
    if (event.endTime?.unixMilliseconds < new Date().getTime()) {
      interval += ' ago';
    }

    return {
      ...event,
      date,
      interval,
    };
  });

export const sortEvents = (events: BasicEventFragment[]): MappedEvent<BasicEventFragment>[] => events.sort((a, b) => a.startTime?.unixMilliseconds - b.startTime?.unixMilliseconds);

export const getUpcomingEvents = (events: BasicEventFragment[]): MappedEvent<BasicEventFragment>[] => {
  // Filter out all events in the past
  events = events.filter((event) => event.startTime?.unixMilliseconds > new Date().getTime());

  // Sort by date
  events = sortEvents(events);

  return events;
};

export const getPreviousEvents = (events: BasicEventFragment[]): MappedEvent<BasicEventFragment>[] => {
  // Filter out all events in the future
  events = events.filter((event) => event.startTime?.unixMilliseconds < new Date().getTime());

  // Sort by date
  events = sortEvents(events);

  return events;
};

export const makeEventsConsumable = (events: BasicEventFragment[]): EventInput[] => events.map((event) => ({
  id: event.eid.id,
  title: event.title,
  description: event.description,
  allDay: event.allDay,
  start: event.startTime?.unixMilliseconds,
  end: event.endTime?.unixMilliseconds,
}));

export const eventSources: EventSources = (id) => ({
  leftindust: (data, successCallback, failureCallback) => {
    const { request, events } = EventsGenericEngine({
      range: {
        start: {
          unixMilliseconds: data.start.valueOf(),
        },
        end: {
          unixMilliseconds: data.end.valueOf(),
        },
      },
    }, { fetchPolicy: 'no-cache' });

    request.subscribe(({ data, error }) => {
      if (!error) {
        if (data) {
          events.subscribe((source) => {
            successCallback(makeEventsConsumable(source).map((event) => {
              if (id) {
                return {
                  ...event,
                  className: (event.id === id) ? 'event-not-grayscale' : '',
                };
              }

              return event;
            }));
          });
        }
      } else {
        failureCallback({
          message: JSON.stringify(error),
        });
      }
    });
  },
});

export const mostRecentlyActiveEvent = writable<BasicEventFragment | undefined>();

export const showEventInCalendar = async (event: BasicEventFragment): Promise<void> => {
  if (await safelyExitWizard()) {
    f7.tab.show(`#${AppViews.Calendar}`);
    APP_ACTIVE_TAB.set(AppViews.Calendar);

    mostRecentlyActiveEvent.set(event);
  }
};