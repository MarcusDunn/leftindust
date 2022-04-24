<script lang="ts">
  import type { CardProps } from '@/features/Widgets';
  import type { Popup } from 'framework7/types';
  
  import { _ } from '@/language';
  import { pin, pinned } from '@/features/Pin';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';

  import { AppViews } from '@/features/App';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';
  
  import DoctorTags from '@/features/Doctor/components/DoctorTags/DoctorTags.svelte';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
  import { type DoctorFragmentFragment, DoctorsQueryDocument } from '@/api/server';
  import { operationStore, query } from '@urql/svelte';

  const { data, dragger, reference, attachments, quicklook } = $$props as CardProps;

  let quicklookPopup: Popup.Popup;

  let doctors: [DoctorFragmentFragment];

  const request = operationStore(DoctorsQueryDocument, {
    dids: [{ id: data.id }],
  });

  query(request);

  $: doctor = doctors[0];

  const url = `/doctor/${JSON.stringify(data)}/`;

</script>

<Quicklook
  {url}
  view={AppViews.Clients}
  color="blue"
  bind:instance={quicklookPopup}
/>

<Card
  color="blue"
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
    <div style="margin-right: 14px; margin-top: 6px">
      <Boxed
        color="gray"
        fill
        round
      >
        {`${doctor.firstName.charAt(0)}${doctor.lastName.charAt(0)}`}
      </Boxed>
    </div>
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <div>
      <DoctorTags {...doctor} small outline />
    </div>
  </svelte:fragment>
  
  <svelte:fragment slot="controls">
    {#if reference}
      <PinButton
        pinned={pinned({
          id: doctor.did.id,
          type: doctor.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: doctor.did.id,
          type: doctor.__typename,
        }, reference)}
      />
    {/if}
    <Row>
      <Col width="50">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== data.id);
            }}
          >
            <Icon f7="trash_fill" />
            {$_('generics.delete')}
          </Button>
        {:else}
          <Button
            round
            fill
            color="blue"
          >
            {$_('generics.viewDoctor')}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <div
          class={`${quicklook ? 'disabled' : ''}`}
          on:click={() => quicklookPopup.open()}
        >
          <Button
            round
            outline
            color="blue"
          >
            <Icon f7="eye_fill" />
            {$_('generics.quicklook')}
          </Button>
        </div>
      </Col>
    </Row>
  </svelte:fragment>
</Card>