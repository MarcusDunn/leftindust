<script lang="ts" context="module">
  import type { CalendarOptions } from '@fullcalendar/core';
  
  export const CalendarUIOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    scrollTime: '00:00:00',
    headerToolbar: {
      left: '',
      center: '',
      right: '',
    },
  };
</script>

<script lang="ts">
  import type { Calendar } from '@fullcalendar/core';
  import { f7 } from 'framework7-svelte';

  import { onMount } from 'svelte';

  export let options: CalendarOptions = CalendarUIOptions;
  export let ref: HTMLDivElement | undefined = undefined;
  export let instance: Calendar | undefined = undefined;

  export let grayscale = false;

  onMount(async () => {
    // TODO: Temporary fix for vite and fullcalendar's async chunk loading issue
    // https://github.com/fullcalendar/fullcalendar/issues/6371
    await import('@fullcalendar/core/vdom');
    
    const calendar = await import('@fullcalendar/core');

    const dayGridPlugin = await import('@fullcalendar/daygrid');
    const timeGridPlugin = await import('@fullcalendar/timegrid');
    const listPlugin = await import('@fullcalendar/list');
    const interactionPlugin = await import('@fullcalendar/interaction');
 
    if (ref) {
      instance = new calendar.Calendar(ref, {
        plugins: [dayGridPlugin.default, timeGridPlugin.default, listPlugin.default, interactionPlugin.default],
        ...options,
      });
      instance.render();
    }
  });

  $: if (ref) grayscale ? f7.$(ref).addClass('ultra-calendar-grayscale') : f7.$(ref).removeClass('ultra-calendar-grayscale');

  onMount(() => {
    f7.$('.view')
      .on('tab:show', () => {
        instance?.refetchEvents();
        instance?.updateSize();
      });

    f7.$('.panel')
      .on('panel:opened', () => {
        instance?.updateSize();
      });

    f7.$('.panel')
      .on('panel:closed', () => {
        instance?.updateSize();
      });
  });
</script>

<div
  class="ultra-calendar"
  bind:this={ref}
/>
