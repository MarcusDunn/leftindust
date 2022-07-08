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
  
  import GenericGrid from '../../../Widgets/components/Grid/GenericGrid.svelte';
  import { WidgetCategory, WidgetType } from '../../../Widgets';

  let patients: PatientsFragment[] = [];
  
  const { data, dragger, quicklook } = $$props as StackProps;
  
  const request = operationStore(PatientsQueryDocument, {
    pids: { id: data.id },
  });
  
  query(request);
  
  $: if ($request.data?.patients[0]) patients = $request.data?.patients;

  //data = Doctors, should be patients
  $: console.log($request.data?.patients[0])
  $: console.log(data)
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
      <GenericGrid 
        props={{ id:'Patient', data, quicklook }}
        type={WidgetType.Card}
        dataType={['Patient']}
        category={[WidgetCategory.Document]}
        store
        fixed
      />
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