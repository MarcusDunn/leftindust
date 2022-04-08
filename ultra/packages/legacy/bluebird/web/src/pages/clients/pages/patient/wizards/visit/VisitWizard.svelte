<script lang="ts">
  import type { Popover } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { BasicEventFragment, BasicIcdFragment } from '@framework/requests/fragments';
  import type { VisitInput } from '@framework/schemas/leftindust.schema';
  import type { VisitEngine as VisitEngineType } from '@framework/engines/visits/VisitEngine';

  import { writable } from 'svelte/store';
  
  import language from '@framework/languages';
  import { closeWizard, openPopover } from '@framework/modules/NavigationModule';
  import { getTitleFromText } from '@framework/modules/InputsModule';
  import { roundToNearestMinutes } from 'date-fns';
  import VisitEngine from '@framework/engines/visits/VisitEngine';
  import deepmerge from 'deepmerge';
  
  import { BlockFooter } from 'framework7-svelte';
  
  import { AppViews, AppViewRoutes } from '@framework/modules/AppModule';
  import { PluginTypes } from '@framework/plugins';
  import { getNativeAPI } from '@framework/bridge';
  
  import VisitMutateEngine from '@framework/engines/visits/VisitMutateEngine';

  import WizardSplitUI from '@framework/ui/layout/WizardUI/WizardSplitUI.svelte';
  import IFrameUI from '@framework/ui/view/IFrameUI/IFrameUI.svelte';
  import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';
  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';

  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import EventMutateEngine from '@framework/engines/events/EventMutateEngine';
  import EventsSpecificEngine from '@framework/engines/events/EventsSpecificEngine';

  export let reference: Selectable<'Patient'>;
  export let selectable: Selectable<'Visit'> | undefined = undefined;
  
  const { Dialog } = getNativeAPI();
  
  let visit: VisitEngineType['visit'] = writable();

  if (selectable) {
    const engine = VisitEngine({
      vids: [{ id: selectable.id }],
    });

    visit = engine.visit;
  }

  const icds = writable<Selectable[]>([]);
  const events = writable<Selectable[]>([]);

  let icdFragments: Record<string, BasicIcdFragment>;
  let eventFragments: Record<string, BasicEventFragment>;

  let titleEdited = !!selectable;
  let disabled = false;

  let input: Partial<VisitInput> = {
    eid: undefined,
    title: '',
    description: undefined,
    foundationIcdCodes: [],
  };
  
  let icdPopover: Popover.Popover;
  let eventPopover: Popover.Popover;

  const checkDisabled = (): boolean => !input.title
    || !input.eid?.id
    || !!input.foundationIcdCodes && input.foundationIcdCodes.length === 0;

  const edit = () => {
    // The ts compiler is freaking out inconsistently
    // @ts-expect-error
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { event, icds: _, ...stripped } = deepmerge(input, {
      ...$visit,
      eid: { id: $visit.event.eid.id },
      foundationIcdCodes: $visit.icds.map((icd) => ({
        url: icd.id,
      })),
    });

    $events = [{
      type: $visit.event.__typename,
      id: $visit.event.eid.id,
    }];

    $icds = $visit.icds.map((icd) => ({
      type: icd.__typename,
      id: icd.id,
    }));

    const { events: fragment } = EventsSpecificEngine({ events: [{ id: $visit.event.eid.id }] });

    fragment.subscribe((data) => {
      if (data?.[0]?.eid?.id) eventFragments = { [data[0].eid.id]: data[0] };
    });

    input = stripped;

    disabled = checkDisabled();
    
  };

  const submit = async () => {
    const event = eventFragments[input?.eid?.id ?? ''];

    const submitVisit = () => {
      // @ts-expect-error
      void VisitMutateEngine(input, !!selectable).then((result) => {
        result ? (() => {
          closeWizard();
        })() : void Dialog.alert({
          message: language().errors.internalError.text,
          detail: language().errors.request.text,
          buttons: [language().buttons.ok.text],
        });
      });
    };

    if (event) {
      let date = new Date(event.startTime?.unixMilliseconds);
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);
      date.setMilliseconds(0);

      date = new Date(date.getTime() + (24 * 60 * 60 * 1000));

      if (date.getTime() > new Date().getTime()) {
        const result = await Dialog.alert({
          buttons: ['Reschedule', 'Don\'t Reschedule', 'Cancel'],
          message: 'Would you like to reschedule the original event?',
          detail: 'The event attached to this file is from the future. '
                + 'To keep your calendar up to date, we can automatically reschedule the event for you.',
          defaultId: 0,
        });

        if (result === 0) {
          const endTime = roundToNearestMinutes(new Date(), { nearestTo: 15 })
            .getTime();
          const startTime = endTime - new Date(
            event.endTime?.unixMilliseconds
              - event.startTime?.unixMilliseconds,
          ).getTime();

          void EventMutateEngine({
            eid: event.eid,
            start: {
              unixMilliseconds: startTime,
            },
            end: {
              unixMilliseconds: endTime,
            },
          }, true).then((result) => {
            result ? (() => {
              submitVisit();
            })() : void Dialog.alert({
              message: language().errors.internalError.text,
              detail: language().errors.request.text,
              buttons: [language().buttons.ok.text],
            });
          });
        } else if (result === 1) {
          submitVisit();
        }
      } else {
        submitVisit();
      }
    }
  };
  
  $: input.foundationIcdCodes = $icds.map((icd) => ({
    url: icd.id,
  }));

  $: if (!titleEdited) {
    input.title = getTitleFromText(
      $icds
        .map((icd) => icdFragments?.[icd.id]?.title ?? '')
        .filter((fragment) => !!fragment),
      language().events.buttons.newEvent.text);
  }

  $: input.eid = { id: $events[0]?.id };

  // @ts-expect-error
  $: input, checkDisabled();

  $: if ($visit) edit();
</script>

<WizardSplitUI
  title={selectable ? language().visits.headers.editVisit.text : language().visits.buttons.newVisit.text}
  subtitle={
    selectable ?
      language().visits.placeholders.editVisitDescription(`${$visit?.title}`).text
      : language().visits.descriptions.createDescription.text
  }
  {disabled}
  on:submit={submit}
>
  <AttachmentsModal
    attachments={events}
    types={['Event']}
    {reference}
    multiple={false}
    props={{
      filterVisits: !selectable,
    }}
    bind:instance={eventPopover}
    on:fragment={({ detail }) => {
      eventFragments = detail;
    }}
  />
  <AttachmentsModal
    attachments={icds}
    types={['IcdSimpleEntity', 'IcdLinearizationEntity']}
    bind:instance={icdPopover}
    on:fragment={({ detail }) => {
      icdFragments = detail;
    }}
  />
  <h4>Event</h4>
  {#key $events}
    {#if $events.length > 0}
      <SpecificPluginGrid
        props={$events.map((selectable) => {
          if (selectable.type) {
            return {
              id: selectable.type,
              selectable: {
                id: selectable.id,
                type: selectable.type,
              },
              attachments: events,
            };
          }
        })}
        type={PluginTypes.Widget}
        cols={[[Math.pow(10, 1000), 2], [550, 1]]}
        dynamicGap
        fixed
      />
    {/if}
  {/key}
  
  <AddUI title="Attach Event/Appointment" on:click={(event) => openPopover(eventPopover, event)} />
  <p />
  <BlockFooter>Attach the calendar event corresponding to this visit.</BlockFooter>

  <h4>Diagnosis</h4>
  {#key $icds}
    {#if $icds.length > 0}
      <SpecificPluginGrid
        props={$icds.map((selectable) => {
          if (selectable.type) {
            return {
              id: selectable.type,
              selectable: {
                id: selectable.id,
                type: selectable.type,
              },
              attachments: icds,
            };
          }
        })}
        type={PluginTypes.Widget}
        cols={[[Math.pow(10, 1000), 2], [550, 1]]}
        dynamicGap
        fixed
      />
    {/if}
  {/key}
  <AddUI title="Attach ICD Codes" on:click={(event) => openPopover(icdPopover, event)} />
  <p />
  <BlockFooter>Attach ICD codes to help describe your provisional diagnosis.</BlockFooter>

  <h4>Title</h4>
  <InputUI>
    <input
      type="text"
      placeholder="Eg. Osteoarthritis"
      bind:value={input.title}
      on:input={() => { titleEdited = true; }}
    />
  </InputUI>
  <p />
  <BlockFooter>Name this visit or call. If an ICD is attached the title will be used automatically.</BlockFooter>

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
            { url, selected: Object.values(AppViews)[index] === AppViews.Clients }
          ),
        )
    }
    url={`/patient/${JSON.stringify(reference)}/`}
  />
</WizardSplitUI>
