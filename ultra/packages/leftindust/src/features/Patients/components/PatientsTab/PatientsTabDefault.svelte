<script lang="ts">
  import type { Router } from 'framework7/types';

  import {
    defaultRangeInput,
    PartialPatientsByPatientIdQueryDocument,
    SortableField,
    type PartialPatientFragment,
  } from '@/api/server';
  import { clientsSelected } from '@/features/Clients/store';

  import { _ } from '@/language';
  import { operationStore, query } from '@urql/svelte';
  import { account } from '@/features/Account/store';
  import { sortRecents, updateRecents } from '@/features/Recents';
  import { getTimestampedValues } from '@/features/Recents';

  import { PageContent } from 'framework7-svelte';

  import CollapsableContentPlaceholder from '@/features/UI/components/Collapsable/CollapsableContentPlaceholder.svelte';

  import MasterListLayout from '@/features/Entity/components/MasterListLayout/MasterListLayout.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import PatientsCells from '../PatientsCells/PatientsCells.svelte';

  export let f7router: Router.Router;

  let patients: PartialPatientFragment[];
  let recents: PartialPatientFragment[];

  const request = operationStore(PartialPatientsByPatientIdQueryDocument, {
    range: defaultRangeInput,
    sortedBy: SortableField.LastName,
  });

  const recentsRequest = operationStore(PartialPatientsByPatientIdQueryDocument, {
    pids: getTimestampedValues($account.database.recents.Patient ??= {}).map(id => ({ id })),
  });

  const navigate = (multiple: boolean) => {
    if (multiple) {
      f7router.navigate(`/people/${JSON.stringify($clientsSelected)}/`);
    } else {
      f7router.navigate(`/patient/${JSON.stringify($clientsSelected[0])}/`);
    }
  };

  $: $recentsRequest.variables = {
    pids: getTimestampedValues($account.database.recents.Patient ?? {}).map(id => ({ id })),
  };

  $: patients = $request.data?.patientsByPatientId ?? [];
  $: timestampedRecents = $account.database.recents.Patient ?? {};
  $: recents = sortRecents(
    $recentsRequest.data?.patients ?? [],
    timestampedRecents,
    (patient => patient.id.value),
  );

  query(request);
  query(recentsRequest);
</script>

<PageContent style="overflow-y:auto" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}> 
  <br />
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
