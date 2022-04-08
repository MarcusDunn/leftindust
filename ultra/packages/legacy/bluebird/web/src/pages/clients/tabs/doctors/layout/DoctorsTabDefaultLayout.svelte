<script lang="ts">
  import type { Router } from 'framework7/types';
  import { DEFAULT_RANGE_INPUT } from '@framework/requests/index';
  import { ClientsSelected } from '@/pages/clients/store/ClientsStore';
  import { ACCOUNT } from '@framework/store';

  import language from '@framework/languages/index';
  import DoctorsGenericEngine from '@framework/engines/doctors/DoctorsGenericEngine';
  import DoctorsSpecificEngine from '@framework/engines/doctors/DoctorsSpecificEngine';
  import { updateRecents } from '@framework/modules/RecentsModule';

  import { PageContent } from 'framework7-svelte';

  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import MasterListLayout from '@framework/components/layout/MasterListLayout.svelte';
  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import DoctorsTabSelectableList from '../list/DoctorsTabSelectableList.svelte';

  export let f7router: Router.Router;
  
  const { request } = DoctorsGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  });

  const { request: recentsRequest, doctors: recents } = DoctorsSpecificEngine({
    dids: ($ACCOUNT.database.recents.Doctor ??= []).map((id) => ({ id })),
  });

  const navigate = () => {
    if ($ClientsSelected.length > 1) {
      f7router.navigate(`/people/${JSON.stringify($ClientsSelected)}/`);
    } else {
      f7router.navigate(`/doctor/${JSON.stringify($ClientsSelected[0])}/`);
    }
  };

  $: void recentsRequest.setVariables({
    dids: ($ACCOUNT.database.recents.Doctor).map((id) => ({ id })),
  });
</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <MasterListLayout>
    <RequestLayout {...$recentsRequest} refetch={recentsRequest.refetch} slot="recents">
      {#if $recents.length > 0}
        <DoctorsTabSelectableList
          doctors={$recents || []}
          selected={ClientsSelected}
          on:navigate={navigate}
        />
        <br />
      {:else}
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noRecents.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>

    <RequestLayout {...$request} refetch={request.refetch}>
      <DoctorsTabSelectableList
        doctors={$request.data?.doctors || []}
        selected={ClientsSelected}
        on:navigate={() => {
          updateRecents('Doctor', $ClientsSelected.filter((client) => client.type === 'Doctor')[0].id);
          navigate();
        }}
      />
    </RequestLayout>
  </MasterListLayout>
</PageContent>
