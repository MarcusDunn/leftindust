<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Writable } from 'svelte/store';
  import type { Plugin } from '../../';
  import type { Popover } from 'framework7/types';
  
  import DoctorsSpecificEngine from '../../../engines/doctors/DoctorsSpecificEngine';
  
  import language from '../../../languages/index';
  import { pin, pinned } from '../../../modules/PinModule';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  import { openPopover, safeNavigate } from '../../../modules/NavigationModule';
  import { AppViews } from '../../../modules/AppModule';
  
  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';
  import QuicklookUI from '../../../ui/view/QuicklookUI/QuicklookUI.svelte';
  import PinButtonUI from '../../../ui/button/PinButtonUI/PinButtonUI.svelte';

  import DoctorTags from '../../../components/tags/DoctorTags.svelte';
  
  export let dragger: () => void | undefined;
  export let selectable: Selectable<'Doctor'>;
  export let properties: NonNullable<Plugin['properties']>;
  export let reference: Selectable | undefined = undefined;
  export let attachments: Writable<Selectable[]> | undefined = undefined;

  export let quicklook = false;

  let quicklookPopover: Popover.Popover;

  const { doctors } = DoctorsSpecificEngine({
    dids: [{ id: selectable.id }],
  });

  $: doctor = $doctors[0];

  const url = `/doctor/${JSON.stringify(selectable)}/`;
</script>

<QuicklookUI
  {url}
  view={AppViews.Clients}
  color={properties.color}
  bind:instance={quicklookPopover}
/>

<PluginWidgetUI
  color={properties.color}
  dragger={dragger}
  shadow={!attachments}
  loading={!doctor}
>
  <svelte:fragment slot="title">
    <div>
      {`${doctor.firstName} ${doctor?.middleName ? `${doctor?.middleName?.charAt(0)}.` : ''} ${doctor.lastName}`}
    </div>
  </svelte:fragment>

  <svelte:fragment slot="before-title">
    {#if reference}
      <PinButtonUI
        pinned={pinned({
          id: doctor.did.id,
          type: doctor.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: doctor.did.id,
          type: doctor.__typename,
        }, reference)}
      />
    {:else}
      <div style="margin-right: 14px; margin-top: 6px">
        <BoxedUI
          color={properties.color}
          fill
          round
        >
          {`${doctor.firstName.charAt(0)}${doctor.lastName.charAt(0)}`}
        </BoxedUI>
      </div>
    {/if}
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <div>
      <DoctorTags {...doctor} small outline />
    </div>
  </svelte:fragment>
  
  <svelte:fragment slot="controls">
    <Row>
      <Col width="50">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== selectable.id);
            }}
          >
            <Icon f7="trash_fill" />
            {language().buttons.delete.text}
          </Button>
        {:else}
          <Button
            round
            fill
            color={properties.color}
            on:click={() => safeNavigate(url, AppViews.Clients)}
          >
            {language().clients.doctors.buttons.viewDoctor.text}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <div
          class={`${quicklook ? 'disabled' : ''}`}
          on:click={(event) => openPopover(quicklookPopover, event)}
        >
          <Button
            round
            outline
            color="blue"
          >
            <Icon f7="eye_fill" />
            {language().buttons.quicklook.text}
          </Button>
        </div>
      </Col>
    </Row>
  </svelte:fragment>
</PluginWidgetUI>