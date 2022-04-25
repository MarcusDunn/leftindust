<script lang="ts">
  import type { CardProps } from '@/features/Widgets';
  import type { Popup } from 'framework7/types';
  import { PatientsQueryDocument, type PatientsFragment } from '@/api/server';
  
  import { _ } from '@/language';
  import { pin, pinned } from '@/features/Pin';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';

  import { AppViews } from '@/features/App';
  import { openPopup } from '@/features/View';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';
  
  import PatientTags from '@/features/Patient/components/PatientTags/PatientTags.svelte';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
  import { operationStore, query } from '@urql/svelte';

  const { data, dragger, reference, attachments, quicklook } = $$props as CardProps;

  let quicklookPopup: Popup.Popup;
  let patient: PatientsFragment;

  const request = operationStore(PatientsQueryDocument, {
    pids: [{ id: data.id }],
  });

  query(request);

  $: if ($request.data?.patients[0]) patient = $request.data?.patients[0];

  const url = `/patient/${JSON.stringify(data)}/`;
</script>

<Quicklook
  {url}
  view={AppViews.Clients}
  color="purple"
  bind:instance={quicklookPopup}
/>

<Card
  color="purple"
  {dragger}
  loading={!patient}
  shadow={!attachments}
>
  <svelte:fragment slot="title">
    <div>
      {`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
    </div>
  </svelte:fragment>
  
  <svelte:fragment slot="before-title">
    <div style="margin-top: 6px">
      <Boxed
        color="gray"
        fill
        round
      >
        {`${patient.firstName.charAt(0)}${patient.lastName.charAt(0)}`}
      </Boxed>
    </div>
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <PatientTags {...patient} small outline />
  </svelte:fragment>

  <svelte:fragment slot="controls">
    {#if reference}
      <PinButton
        pinned={pinned({
          id: patient.pid.id,
          type: patient.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: patient.pid.id,
          type: patient.__typename,
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
            color="purple"
          >
            {$_('generics.viewPatient')}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <div
          class={`${quicklook ? 'disabled' : ''}`}
          on:click={() => openPopup(quicklookPopup)}
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