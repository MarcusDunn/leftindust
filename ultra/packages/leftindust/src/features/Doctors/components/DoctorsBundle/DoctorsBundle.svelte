<script lang="ts">
  import type { BundleProps } from '@/features/Widgets';
  import Bundle from '@/features/Widgets/components/Bundle/Bundle.svelte';
  import { _ } from '@/language';
  import { configuration } from '@/features/Doctors';
  import { operationStore, query } from '@urql/svelte';
  import { PartialDoctorsByDoctorIdQueryDocument, type PartialDoctorFragment } from '@/api/server';
  import { pinned, pin } from '@/features/Pin';
  import DoctorPinnableCell from '@/features/Doctor/components/DoctorCell/DoctorPinnableCell.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  import DescriptivePlaceholder from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';

  let doctors: PartialDoctorFragment[] = [];

  const { data, dragger, quicklook } = $$props as BundleProps;

  const request = operationStore(PartialDoctorsByDoctorIdQueryDocument, {
    doctorIds: { value: data.id },
  });

  query(request);
  
  $: doctors = $request.data?.doctorsByDoctorIds.filter(
    (doctor): doctor is PartialDoctorFragment => !!doctor,
  ) ?? [];
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
              id: doctor.id?.value,
              type: doctor.__typename,
            }, data)}
            on:pin={({ detail }) => pin(detail, {
              id: doctor.id?.value,
              type: doctor.__typename,
            }, data)}
          />
        {/each}
      </Cells>
    {:else}
      <DescriptivePlaceholder 
        title={$_('generics.noDoctors')}
        description={$_('descriptions.noDoctors')}
        link = {{
          label: $_('descriptions.learnMoreDoctors'),
        }}
      />
    {/if}
  </Request>
  <Row slot="controls">
    <Col width="50">
      <Button
        class={`${quicklook ? 'disabled' : ''}`}
        color={configuration.color}
        fill
        round
      >
        {$_('generics.newDoctor')}
      </Button>
    </Col>
    <Col width="50">
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