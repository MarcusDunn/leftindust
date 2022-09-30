<script lang="ts">
  import type { Router } from 'framework7/types';

  import type { PartialDoctorFragment, PartialDoctorsByDoctorIdQueryQuery, PartialDoctorsByRangeQueryQuery, Range } from '@/api/server';
  import { clientsSelected } from '@/features/Clients/store';

  import { _ } from '@/language';
  import { query, type OperationStore } from '@urql/svelte';
  import { account } from '@/features/Account/store';
  import { sortRecents, updateRecents } from '@/features/Recents';
  import { getTimestampedValues } from '@/features/Recents';

  import { PageContent } from 'framework7-svelte';
  
  import CollapsableContentPlaceholder from '@/features/UI/components/Collapsable/CollapsableContentPlaceholder.svelte';

  import MasterListLayout from '@/features/Entity/components/MasterListLayout/MasterListLayout.svelte';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import DoctorsCells from '../DoctorsCells/DoctorsCells.svelte';

  export let f7router: Router.Router;

  export let request: OperationStore<PartialDoctorsByRangeQueryQuery, {
    range: Range;
  }, PartialDoctorsByRangeQueryQuery>;

  export let recentsRequest: OperationStore<PartialDoctorsByDoctorIdQueryQuery, {
    doctorIds: {
      value: string;
    }[];
  }, PartialDoctorsByDoctorIdQueryQuery>;

  let doctors: PartialDoctorFragment[] = [];
  let recents: PartialDoctorFragment[];

  const navigate = (multiple: boolean) => {
    if (multiple) {
      f7router.navigate(`/people/${JSON.stringify($clientsSelected)}/`);
    } else {
      f7router.navigate(`/doctor/${JSON.stringify($clientsSelected[0])}/`);
    }
  };

  $: $recentsRequest.variables = {
    doctorIds: getTimestampedValues($account.database.recents.Doctor ?? [])
      .map((id) => ({ value: id }))
      .filter((value) => value != undefined),
  };

  $: doctors = $request.data?.doctorsByRange.filter((value) => value != undefined) ?? [];
  $: timestampedRecents = $account.database.recents.Doctor ?? {};
  $: recents = sortRecents(
    $recentsRequest.data?.doctorsByDoctorIds.filter((value) => value != undefined) ?? [],
    timestampedRecents,
    ((doctor) => doctor?.id?.value ?? ''),
  ) as PartialDoctorFragment[];

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
      {#if doctors.length > 0}
        <DoctorsCells
          doctors={doctors || []}
          selected={clientsSelected}
          on:navigate={() => {
            if ($clientsSelected.length === 1) updateRecents('Doctor', $clientsSelected.filter((client) => client.type === 'Doctor')[0].id);
            navigate($clientsSelected.length > 1);
          }}
        />
      {:else}
        <CollapsableContentPlaceholder center>
          No doctors found...
        </CollapsableContentPlaceholder>
      {/if}
    </Request>
  </MasterListLayout>
</PageContent>
