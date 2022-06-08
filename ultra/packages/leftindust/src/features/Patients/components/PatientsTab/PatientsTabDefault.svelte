<script lang="ts">
  import type { Router } from 'framework7/types';

  import {
    defaultRangeInput,
    PatientsQueryDocument,
    SortableField,
    type PatientsFragment,
  } from '@/api/server';
  import { clientsSelected } from '@/features/Clients/store';

  import { _ } from '@/language';
  import { operationStore, query } from '@urql/svelte';
  import { account } from '@/features/Account/store';
  import { updateRecents } from '@/features/Recents';

  import { PageContent } from 'framework7-svelte';
  
  import CollapsableContentPlaceholder
    from '@/features/UI/components/Collapsable/CollapsableContentPlaceholder.svelte';

  import MasterListLayout
    from '@/features/Entity/components/MasterListLayout/MasterListLayout.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import PatientsCells from '../PatientsCells/PatientsCells.svelte';

  export let f7router: Router.Router;

  let patients: PatientsFragment[];
  let recents: PatientsFragment[];

  const request = operationStore(PatientsQueryDocument, {
    range: defaultRangeInput,
    sortedBy: SortableField.LastName,
  });

  const recentsRequest = operationStore(PatientsQueryDocument, {
    pids: ($account.database.recents.Patient ??= []).map((id) => ({ id })),
  });

  const navigate = (multiple: boolean) => {
    if (multiple) {
      f7router.navigate(`/people/${JSON.stringify($clientsSelected)}/`);
    } else {
      f7router.navigate(`/patient/${JSON.stringify($clientsSelected[0])}/`);
    }
  };

  $: $recentsRequest = {
    variables: {
      pids: ($account.database.recents.Patient ?? []).map((id) => ({ id })),
    },
  };

  $: patients = $request.data?.patients ?? [];
  $: recents = $recentsRequest.data?.patients ?? [];

  query(request);
  query(recentsRequest);

</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <MasterListLayout>
    <Request {...$recentsRequest} reexecute={recentsRequest.reexecute} slot="recents">
      {#if recents.length > 0}
        <PatientsCells
          patients={recents || []}
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

    <Request {...$request} reexecute={request.reexecute}>
      <PatientsCells
        patients={patients || []}
        selected={clientsSelected}
        on:navigate={() => {
          if ($clientsSelected.length === 1) updateRecents('Patient', $clientsSelected.filter((client) => client.type === 'Patient')[0].id);
          navigate($clientsSelected.length > 1);
        }}
      />
    </Request>
  </MasterListLayout>
</PageContent>
