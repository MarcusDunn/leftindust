<script lang="ts">
  import type { Router } from 'framework7/types';

  import { DEFAULT_RANGE_INPUT } from '@framework/requests/index';
  import { ClientsSelected } from '@/pages/clients/store/ClientsStore';
  import { SortableField } from '@framework/schemas/leftindust.schema';

  import language from '@framework/languages/index';
  import PatientsGenericEngine from '@framework/engines/patients/PatientsGenericEngine';
  import { ACCOUNT } from '@framework/store';
  import PatientsSpecificEngine from '@framework/engines/patients/PatientsSpecificEngine';
  import { updateRecents } from '@framework/modules/RecentsModule';

  import { PageContent } from 'framework7-svelte';
  
  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import MasterListLayout from '@framework/components/layout/MasterListLayout.svelte';
  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import PatientsTabSelectableList from '../list/PatientsTabSelectableList.svelte';
  
  export let f7router: Router.Router;

  const { request, patients } = PatientsGenericEngine({
    range: DEFAULT_RANGE_INPUT,
    sortBy: SortableField.LastName,
  });

  const navigate = () => {
    if ($ClientsSelected.length > 1) {
      f7router.navigate(`/people/${JSON.stringify($ClientsSelected)}/`);
    } else {
      f7router.navigate(`/patient/${JSON.stringify($ClientsSelected[0])}/`);
    }
  };

</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <MasterListLayout>

    <RequestLayout {...$request} refetch={request.refetch}>
      <PatientsTabSelectableList
        patients={$patients || []}
        selected={ClientsSelected}
        on:navigate={() => {
          updateRecents('Patient', $ClientsSelected.filter((client) => client.type === 'Patient')[0].id);
          navigate();
        }}
      />
    </RequestLayout>
  </MasterListLayout>
</PageContent>
