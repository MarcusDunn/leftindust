<script lang="ts">
  import type { StackProps } from '@/features/Widgets';
  import Stack from '@/features/Widgets/components/Stack/Stack.svelte';
  import { _ } from '@/language';
  import { configuration } from '@/features/Doctors';
  import { operationStore, query } from '@urql/svelte';
  import { DoctorsQueryDocument, type DoctorsFragment } from '@/api/server';
  // import { pinned, pin } from '@/features/Pin';
  // import DoctorPinnableCell from '@/features/Doctor/components/DoctorCell/DoctorPinnableCell.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  // import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  // import language from '@/language/locales/en';
  import DescriptivePlaceholder from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import GenericGrid from '../../../Widgets/components/Grid/GenericGrid.svelte';
  import { WidgetCategory, WidgetType } from '../../../Widgets';

  let doctors: DoctorsFragment[] = [];

  const { data, dragger, quicklook } = $$props as StackProps;

  const request = operationStore(DoctorsQueryDocument, {
    dids: { id: data.id },
  });
  
  query(request);
    
  $: if ($request.data?.doctors[0]) doctors = $request.data?.doctors;

  //StackProps is getting patients, should be doctors
  $: console.log(request.data?.doctors[0])
  $: console.log(data)
</script>

<Stack
  title={$_('generics.doctors')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Request {...$request} refetch={request.reexecute} middle>
    {#if doctors.length > 0}
      <GenericGrid 
        props={{ id:'Doctor', data, quicklook }}
        type={WidgetType.Card}
        dataType={['Doctor']}
        category={[WidgetCategory.Document]}
        store
        fixed
      />
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
  <div slot="controls" class="display-flex widget-stack-header-buttons">
    <Button
      class={`${quicklook ? 'disabled' : ''} widget-stack-header-button`}
      color={configuration.color}
      fill
      round
    >
    {$_('generics.newDoctor')}
    </Button>
    <Button
      class={`widget-stack-header-button`}
      color={configuration.color}
      outline
      round
      disabled={doctors?.length === 0}
    >
    {$_('generics.viewAll')}
    </Button>
  </div>
</Stack>