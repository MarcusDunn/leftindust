import type { EventInput } from '@fullcalendar/core';
import { writable } from 'svelte/store';

let start: number | Date = new Date();

start.setHours(start.getHours() + Math.round(start.getMinutes() / 60));
start.setMinutes(0, 0, 0);

let end: number | Date = new Date(start);
end.setHours(end.getHours() + 1);

end = end.getTime();
start = start.getTime();

export const EventWizardActive = writable(false);

export const EventWizardEventTemplate: EventInput & { start: number; end: number; } = {
  title: 'New Event',
  className: 'event-not-grayscale',
  id: 'wizard-event',
  start,
  end,
  allDay: false,
  editable: true,
  durationEditable: true,
  startEditable: true,
  endEditable: true,
  resourceEditable: true,
  extendedProps: {
    edit: undefined,
  },
};

export const EventWizardEvent = writable<EventInput & { start: number; end: number; }>({ ...EventWizardEventTemplate });