<script lang="ts">
  import type { StackProps } from '@/features/Widgets';
  import Stack from '@/features/Widgets/components/Stack/Stack.svelte';
  import { _ } from '@/language';
  import { configuration } from '@/features/Patients';
  import { operationStore, query } from '@urql/svelte';
  import type { PatientsFragment } from '@/api/server';
  import { PatientsQueryDocument } from '@/api/server';
  import { pinned, pin } from '@/features/Pin';
  import PatientPinnableCell from
    '@/features/Patient/components/PatientCell/PatientPinnableCell.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  import language from '@/language/locales/en';
  import DescriptivePlaceholder from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  
  let patients: PatientsFragment[] = [];
  
  const { data, dragger, quicklook } = $$props as StackProps;
  
  const request = operationStore(PatientsQueryDocument, {
    pids: { id: data.id },
  });
  
  query(request);
  
  $: if ($request.data?.patients[0]) patients = $request.data?.patients;
</script>
  
<Stack
  title={$_('generics.patients')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Request {...$request} refetch={request.reexecute} middle>
    {#if patients.length > 0}
      <Cells>
        {#each patients as patient}
          <PatientPinnableCell 
            {patient}
            pinned={pinned({
              id: patient.pid.id,
              type: patient.__typename,
            }, data)}
            on:pin={({ detail }) => pin(detail, {
              id: patient.pid.id,
              type: patient.__typename,
            }, data)}
          />
        {/each}
      </Cells>
    {:else}
      <DescriptivePlaceholder 
        title={$_('generics.noPatients')} 
        description={$_('descriptions.noPatients')}
        link = {{
          label: $_('descriptions.learnMorePatients'),
        }}
      />
    {/if}
  </Request>
  <div slot="controls" class="display-flex widget-stack-header-buttons">
    <Button
      class={`${quicklook ? 'disabled' : ''} widget-stack-header-button`}
      color={configuration.color}
      fill
      round
    >
    {$_('generics.newPatient')}
    </Button>
    <Button
      class={`widget-stack-header-button`}
      color={configuration.color}
      outline
      round
      disabled={patients?.length === 0}
    >
    {$_('generics.viewAll')}
    </Button>
  </div>
</Stack>