<script lang="ts">
  import type { StackProps } from '@/features/Widgets';
  import Stack from '@/features/Widgets/components/Stack/Stack.svelte';
  import { _ } from '@/language';
  import { configuration } from '@/features/Doctors';
  import { operationStore, query } from '@urql/svelte';
  import { DoctorsQueryDocument, type DoctorsFragment, type DoctorFragment } from '@/api/server';
  // import { pinned, pin } from '@/features/Pin';
  // import DoctorPinnableCell from '@/features/Doctor/components/DoctorCell/DoctorPinnableCell.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  // import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  // import language from '@/language/locales/en';
  import DescriptivePlaceholder from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import { WidgetCategory, WidgetType } from '../../../Widgets';

  import SpecificGrid from '../../../Widgets/components/Grid/SpecificGrid.svelte';
  import { account } from '../../../Account/store';
  import type { WidgetGridProps } from '@/features/Widgets/components/Grid';

  let doctor: DoctorFragment | undefined;
  let doctors: DoctorsFragment[] = [];

  const { data, dragger, quicklook } = $$props as StackProps;
  const request = operationStore(DoctorsQueryDocument, {
    pid: { id: data.id },
  });
  
  query(request);
    
  $: if ($request.data?.doctors[0]) doctors = $request.data?.doctors;
</script>

<Stack
  title={$_('generics.doctors')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Request {...$request} refetch={request.reexecute} middle>
    {#key $account}
      {#if doctors.length > 0}
        <SpecificGrid
          props={doctors.map(({ did }) => ({
            id: 'Doctor',
            data: {
              type: 'Doctor',
              id: did.id,
            },
            quicklook,
          }))}
          type={WidgetType.Card}
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
    {/key}
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
      class={'widget-stack-header-button'}
      color={configuration.color}
      outline
      round
      disabled={doctors?.length === 0}
    >
      {$_('generics.viewAll')}
    </Button>
  </div>
</Stack>