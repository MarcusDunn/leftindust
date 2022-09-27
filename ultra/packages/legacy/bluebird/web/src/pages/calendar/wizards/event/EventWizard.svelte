<script lang="ts">
  import type { Popover } from 'framework7/types';
  import type {
    EventInput,
    PatientIdInput,
    DoctorIdInput,
    EventEditInput,
  } from '@framework/schemas/leftindust.schema';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { BasicDoctorFragment, BasicPatientFragment } from '@framework/requests/fragments';
  import type { EventEngine as EventEngineType } from '@framework/engines/events/EventEngine';
  
  import { AppViews, AppViewRoutes } from '@framework/modules/AppModule';
  import { writable } from 'svelte/store';
  import language from '@framework/languages';
  import { EventWizardEvent, EventWizardEventTemplate, EventWizardActive } from './store';
  import { closeWizard, openPopover } from '@framework/modules/NavigationModule';
  import { PluginTypes } from '@framework/plugins';
  import { getTitleFromNames } from '@framework/modules/InputsModule';
  import { addHours } from 'date-fns';
  import deepmerge from 'deepmerge';
  import EventMutateEngine from '@framework/engines/events/EventMutateEngine';
  import EventEngine from '@framework/engines/events/EventEngine';
  import getNativeAPI from '@framework/bridge';
  
  import {
    BlockFooter,
    ListItem,
    Toggle,
    Row,
    Col,
  } from 'framework7-svelte';

  import WizardSplitUI from '@framework/ui/layout/WizardUI/WizardSplitUI.svelte';
  import IFrameUI from '@framework/ui/view/IFrameUI/IFrameUI.svelte';
  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
  import DateUI from '@framework/ui/input/DateUI/DateUI.svelte';
  import TimeUI from '@framework/ui/input/TimeUI/TimeUI.svelte';
  import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';

  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';

  const { Dialog } = getNativeAPI();

  export let selectable: Selectable<'Event'> | undefined = undefined;
  export let selectables: Selectable[] = [];
  
  let clients = writable<Selectable[]>(selectables);
  let clientsPopover: Popover.Popover;

  let fragments: Record<string, (BasicPatientFragment | BasicDoctorFragment)>;

  let titleEdited = !!selectable;
  let disabled = false;

  let event: EventEngineType['event'] = writable();

  let input: Partial<EventInput> = {
    patients: [],
    doctors: [],
    title: '',
    description: undefined,
  };

  if (selectable) {
    const engine = EventEngine({
      eids: [{ id: selectable.id }],
    });

    event = engine.event;
  }

  $EventWizardActive = true;

  const allDayChange = (allDay: boolean) => {
    if (allDay) {
      // Get the next day
      const end = new Date($EventWizardEvent.start);
      end.setDate(end.getDate() + 1);
      $EventWizardEvent.end = end.getTime();
    } else {
      // Get the next hour
      const end = addHours(new Date($EventWizardEvent.start), 1);
      $EventWizardEvent.end = end.getTime();
    }
  };

  const reset = () => {
    $EventWizardEvent = deepmerge($EventWizardEvent, EventWizardEventTemplate);
    $EventWizardActive = false;
  };

  const submit = () => {
    void EventMutateEngine(<EventInput | EventEditInput>{
      ...input,
      start: {
        unixMilliseconds: $EventWizardEvent.start,
      },
      end: {
        unixMilliseconds: $EventWizardEvent.end,
      },
      allDay: $EventWizardEvent.allDay ?? false,
    }, !!selectable).then((result) => {
      result ? (() => {
        reset();
        closeWizard();
      })() : void Dialog.alert({
        message: language().errors.internalError.text,
        detail: language().errors.request.text,
        buttons: [language().buttons.ok.text],
      });
    });
  };

  const edit = () => {
    // @ts-expect-error
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { visit, startTime, endTime, date, interval, ...stripped } = deepmerge(input, {
      ...$event,
      patients: $event.patients.map((patient) => ({ id: patient.id.value })),
      doctors: $event.doctors.map((doctor) => ({ id: doctor.id?.value })),
      start: $event.startTime,
      end: $event.endTime,
    });

    $clients = [
      ...$event.doctors.map((doctor) => ({
        type: doctor.__typename,
        id: doctor.id?.value,
      })),
      ...$event.patients.map((patient) => ({
        type: patient.__typename,
        id: patient.id.value,
      })),
    ];

    $EventWizardEvent = deepmerge($EventWizardEvent, {
      ...EventWizardEventTemplate,
      title: $event.title,
      start: $event.startTime?.unixMilliseconds,
      end: $event.endTime?.unixMilliseconds,
      allDay: $event.allDay,
      extendedProps: {
        edit: $event.eid.id,
      },
    });

    input = stripped;
  };
  
  $: (() => {
    let patients: PatientIdInput[] = [];
    let doctors: DoctorIdInput[] = [];
    
    $clients.forEach((client) => {
      if (input.patients && input.doctors) {
        if (client.type === 'Patient') patients = [...patients, { id: client.id }];
        if (client.type === 'Doctor') doctors = [...doctors, { id: client.id }];
      }
    });
    
    input.patients = patients;
    input.doctors = doctors;
  })();
  
  $: if (!titleEdited) {
    input.title = getTitleFromNames(
      $clients
        .map((client) => fragments?.[client.id])
        .filter((fragment) => !!fragment)
      , language().events.buttons.newEvent.text);
  }

  $: $EventWizardEvent = {
    ...$EventWizardEvent,
    title: input.title,
  };

  $: disabled =
    !input.title
    || !$EventWizardEvent.start
    || !$EventWizardEvent.end
    || ($EventWizardEvent.start >= $EventWizardEvent.end);

  $: if ($event) edit();
  
</script>

<WizardSplitUI
  title={selectable ? language().events.headers.editEvent.text : language().events.buttons.newEvent.text}
    subtitle={
    selectable ?
      language().events.placeholders.editEventDescription(`${$event?.title}`).text
      : language().events.descriptions.createDescription.text
  }
  {disabled}
  on:submit={submit}
  on:close={reset}
>
  <AttachmentsModal
    attachments={clients}
    types={['Patient', 'Doctor']}
    bind:instance={clientsPopover}
    on:fragment={({ detail }) => {
      fragments = detail;
    }}
  />
  <h4>People</h4>
  {#if $clients.length > 0}
    {#key $clients}
      <SpecificPluginGrid
        props={$clients.map((selectable) => {
          if (selectable.type) {
            return {
              id: selectable.type,
              selectable: {
                id: selectable.id,
                type: selectable.type,
              },
              attachments: clients,
            };
          }
        })}
        type={PluginTypes.Widget}
        cols={[[Math.pow(10, 1000), 2], [550, 1]]}
        dynamicGap
      />
    {/key}
  {/if}
  <AddUI title="Attach Person (Optional)" on:click={(event) => openPopover(clientsPopover, event)} />
  <p />
  <BlockFooter>Attach a person to illustrate who will be attending the event.</BlockFooter>
  <h4>Title</h4>
  <InputUI>
    <input
      type="text"
      placeholder="Eg. John Doe"
      on:input={() => { titleEdited = true; }}
      bind:value={input.title}
    />
  </InputUI>
  <p />
  <BlockFooter>
    Name this appointment or event.
    If a patient is attached their name will be used automatically.
  </BlockFooter>
  <Row>
    <Col width="50">
      <h4>Start Date</h4>
      <DateUI
        from={$EventWizardEvent.end}
        allDay={$EventWizardEvent.allDay}
        bind:value={$EventWizardEvent.start}
      />
    </Col>
    <Col width="50">
      <h4>End Date</h4>
      <DateUI
        to={$EventWizardEvent.start}
        allDay={$EventWizardEvent.allDay}
        bind:value={$EventWizardEvent.end}
      />
    </Col>
  </Row>
  <BlockFooter>
    Determine a start and an end for your event. By default, events will be in the same day.
  </BlockFooter>
  <InputUI>
    <ListItem slot="content">
      <span>All Day</span>
      <Toggle 
        class="aurora"
        color="purple"
        bind:checked={$EventWizardEvent.allDay}
        onToggleChange={(toggle) => allDayChange(toggle) }
      />
    </ListItem>
  </InputUI>
  <br />
  <br />
  {#if !$EventWizardEvent.allDay}
    <Row>
      <Col width="50">
        <h4>Start Time</h4>
        <TimeUI bind:value={$EventWizardEvent.start} />
      </Col>
      <Col width="50">
        <h4>End Time</h4>
        <TimeUI bind:value={$EventWizardEvent.end} />
      </Col>
    </Row>
    <BlockFooter>
      The start time and end time correspond to the dates set on the start and end date input from above.
    </BlockFooter>
  {/if}
  <h4>Notes</h4>
  <InputUI>
    <textarea placeholder="Description (Optional)" bind:value={input.description} />
  </InputUI>

  <IFrameUI
    slot="detail"
    views={
      Object.values(AppViewRoutes)
        .map(
          (url, index) => (
            { url, selected: Object.values(AppViews)[index] === AppViews.Calendar }
          ),
        )
    }
  />
</WizardSplitUI>
