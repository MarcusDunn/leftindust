<script lang="ts">
  import type { BundleProps } from '@/features/Widgets';
  import Bundle from '@/features/Widgets/components/Bundle/Bundle.svelte';
  import { _ } from '@/language';
  import { configuration } from '@/features/Doctors';
  import { operationStore, query } from '@urql/svelte';
  import { DoctorsQueryDocument, type DoctorsFragment } from '@/api/server';
  import { pinned, pin } from '@/features/Pin';
  import DoctorPinnableCell from '@/features/Doctor/components/DoctorCell/DoctorPinnableCell.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';

  let doctors: DoctorsFragment[] = [];

  const { data, dragger, quicklook } = $$props as BundleProps;

  const request = operationStore(DoctorsQueryDocument, {
    pid: { id: data.id },
  });

  query(request);
  
  $: if ($request.data?.doctors[0]) doctors = $request.data?.doctors;
</script>

<Bundle
  title={$_('generics.doctors')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Request {...$request} refetch={request.reexecute} middle>
    {#if doctors.length > 0}
      <Cells>
        {#each doctors as doctor}
          <DoctorPinnableCell
            {doctor}
            pinned={pinned({
              id: doctor.did.id,
              type: doctor.__typename,
            }, data)}
            on:pin={({ detail }) => pin(detail, {
              id: doctor.did.id,
              type: doctor.__typename,
            }, data)}
          />
        {/each}
      </Cells>
    {/if}
  </Request>
  <Row slot='controls'>
    <Col width='50'>
      <Button
        class={`${quicklook ? 'disabled' : ''}`}
        color={configuration.color}
        fill
        round
      >
        {$_('generics.newDoctor')}
      </Button>
    </Col>
    <Col width='50'>
      <Button
        color={configuration.color}
        outline
        round
        disabled={doctors?.length === 0}
      >
        {$_('generics.viewAll')}
      </Button>
    </Col>
  </Row>
</Bundle>