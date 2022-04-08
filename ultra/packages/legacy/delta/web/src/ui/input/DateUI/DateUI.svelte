<script lang="ts">
  import type { Calendar } from 'framework7/types';
  import { f7 } from 'framework7-svelte';
  import InputUI from '../InputUI/InputUI.svelte';
  import { onMount, createEventDispatcher } from 'svelte';

  const dispatch = createEventDispatcher();

  export let value: number[] | number | undefined = undefined;
  export let placeholder = 'Select Date';
  export let header = 'Select Date';
  export let props: Calendar.AppParams['calendar'] = {};

  export let multiple = false;
  export let pastOnly = false;
  export let futureOnly = false;
  export let allDay = false;
  
  export let from: number | Date | undefined = undefined;
  export let to: number | Date | undefined = undefined;

  let ref: HTMLInputElement;
  let calendar: Calendar.Calendar;

  const today = new Date();

  // @ts-expect-error
  $: value, to, from, allDay, calendar && (() => {
    calendar.params.disabled = getDisabledDates();
    if (value) calendar.setValue(Array.isArray(value) ? value.map((date) => new Date(date)) : [new Date(value)]);
  })();

  const getDisabledDates = () => {
    if (!to && !from) {
      if (pastOnly) {
        return {
          from: today,
        };
      }

      if (futureOnly) {
        return {
          to: today,
        };
      }
    } else {
      const customFrom = from ? new Date(from) : undefined;
      const customTo = to ? new Date(to) : undefined;

      if (customTo && !allDay) customTo.setDate(customTo.getDate() - 1);
      if (customFrom && !allDay) customFrom.setDate(customFrom.getDate());

      if (customTo && customFrom) {
        return {
          to: customTo,
          from: customFrom,
        };
      }
      if (customTo) return { to: customTo };
      if (customFrom) return { from: customFrom };
    }
  };

  onMount(() => {
    calendar = f7.calendar.create({
      inputEl: ref,
      value: value ? Array.isArray(value) ? value.map((date) => new Date(date)) : [new Date(value)] : undefined,
      header: true,
      headerPlaceholder: header,
      footer: true,
      ...props,
      openIn: 'customModal',
      on: {
        change: (event, dates) => {
          if (event.opened) {
            if (multiple) {
              value = <number[]>(<unknown[]>dates).map((date) => {
                if (date) {
                  return new Date(<number | Date | string>date).getTime();
                }
              });
            } else {
              value = new Date((<number[]>dates)[0]).getTime();
            }

            dispatch('change', value);
          }
        },
      },
    });

    calendar.params.disabled = getDisabledDates();
  });

</script>

<div class="ultra-date">
  <InputUI>
    <input
      bind:this={ref}
      {placeholder}
      readonly
      type="text"
    />
  </InputUI>
</div>

