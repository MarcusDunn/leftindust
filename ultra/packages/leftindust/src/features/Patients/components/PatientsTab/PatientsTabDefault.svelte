<script lang="ts">
  import type { Router } from 'framework7/types';

  import { defaultRangeInput } from '@/api/server';
  import { clientsSelected } from '@/features/Clients/store';
  import { SortableField } from '@/api/server/schema/leftindust.schema';

  import { _ } from '@/language';
  import PatientsGenericEngine from '@/api/server/engines/patients/PatientsGenericEngine';
  import PatientsSpecificEngine from '@/api/server/engines/patients/PatientsSpecificEngine';
  import { account } from '@/features/Account/store';
  import { updateRecents } from '@/features/Recents';

  import { PageContent } from 'framework7-svelte';
  
  import CollapsableContentPlaceholder
    from '@/features/UI/components/Collapsable/CollapsableContentPlaceholder.svelte';

  import MasterListLayout from '@/features/Entity/components/MasterListLayout/MasterListLayout.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import PatientsCells from '../PatientsCells/PatientsCells.svelte';
  
  export let f7router: Router.Router;

  const { request, patients } = PatientsGenericEngine({
    range: defaultRangeInput,
    sortBy: SortableField.LastName,
  });

  const { request: recentsRequest, patients: recents } = PatientsSpecificEngine({
    pids: ($account.database.recents.Patient ??= []).map((id) => ({ id })),
  });

  const navigate = (multiple: boolean) => {
    if (multiple) {
      f7router.navigate(`/people/${JSON.stringify($clientsSelected)}/`);
    } else {
      f7router.navigate(`/patient/${JSON.stringify($clientsSelected[0])}/`);
    }
  };

  $: void recentsRequest.setVariables({
    pids: ($account.database.recents.Patient).map((id) => ({ id })),
  });

</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <MasterListLayout>
    <Request {...$recentsRequest} refetch={recentsRequest.refetch} slot="recents">
      {#if $recents.length > 0}
        <PatientsCells
          patients={$recents || []}
          selected={clientsSelected}
          on:navigate={() => navigate($clientsSelected.length > 1)}
        />
        <br />
      {:else}
        <CollapsableContentPlaceholder center>
          {$_('descriptions.noRecents')}
        </CollapsableContentPlaceholder>
      {/if}
    </Request>

    <Request {...$request} refetch={request.refetch}>
      <PatientsCells
        patients={$patients || []}
        selected={clientsSelected}
        on:navigate={() => {
          if ($clientsSelected.length === 1) updateRecents('Patient', $clientsSelected.filter((client) => client.type === 'Patient')[0].id);
          navigate($clientsSelected.length > 1);
        }}
      />
    </Request>
  </MasterListLayout>
</PageContent>
