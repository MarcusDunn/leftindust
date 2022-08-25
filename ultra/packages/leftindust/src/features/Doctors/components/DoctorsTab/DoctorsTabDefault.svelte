<script lang="ts">
  import type { Router } from 'framework7/types';

  import { defaultRangeInput, DoctorsQueryDocument, type DoctorsFragment } from '@/api/server';
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
  import DoctorsCells from '../DoctorsCells/DoctorsCells.svelte';

  export let f7router: Router.Router;

  let doctors: DoctorsFragment[];
  let recents: DoctorsFragment[];

  const request = operationStore(DoctorsQueryDocument, {
    range: defaultRangeInput,
  });

  const recentsRequest = operationStore(DoctorsQueryDocument, {
    dids: getTimestampedValues($account.database.recents.Doctor ??= []).map((id) => ({ id })),
  });

  const navigate = (multiple: boolean) => {
    if (multiple) {
      f7router.navigate(`/people/${JSON.stringify($clientsSelected)}/`);
    } else {
      f7router.navigate(`/doctor/${JSON.stringify($clientsSelected[0])}/`);
    }
  };

  $: $recentsRequest.variables = {
    dids: getTimestampedValues($account.database.recents.Doctor ?? []).map((id) => ({ id })),
  };

  $: doctors = $request.data?.doctors ?? [];
  $: timestampedRecents = $account.database.recents.Doctor ?? {};
  $: recents = sortRecents(
    $recentsRequest.data?.doctors ?? [],
    timestampedRecents,
    (doctor => doctor.did.id),
  );

  query(request);
  query(recentsRequest);

</script>

<PageContent style="overflow-y:auto" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <br />
  <MasterListLayout>
    <Request {...$recentsRequest} reexecute={recentsRequest.reexecute} slot="recents">
      {#if recents.length > 0}
        <DoctorsCells
          doctors={recents || []}
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
      <DoctorsCells
        doctors={doctors || []}
        selected={clientsSelected}
        on:navigate={() => {
          if ($clientsSelected.length === 1) updateRecents('Doctor', $clientsSelected.filter((client) => client.type === 'Doctor')[0].id);
          navigate($clientsSelected.length > 1);
        }}
      />
    </Request>
  </MasterListLayout>
</PageContent>
