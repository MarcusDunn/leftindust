<script lang="ts">
  import type { Router } from 'framework7/types';

  import { ClientsSearchQuery, ClientsSelected } from '@/pages/clients/store/ClientsStore';

  import DoctorsGenericEngine, {
    DoctorsGenericEngineDefaultExample,
  }  from '@framework/engines/doctors/DoctorsGenericEngine';

  import { PageContent, Block } from 'framework7-svelte';
  
  import QueryResultsHeader from '@framework/components/header/QueryResultsHeader.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import DoctorsTabSelectableList from '../list/DoctorsTabSelectableList.svelte';
  
  export let f7router: Router.Router;
  
  let timeout: ReturnType<typeof setTimeout>;

  const { request, doctors } = DoctorsGenericEngine({
    example: DoctorsGenericEngineDefaultExample($ClientsSearchQuery),
  });

  $: (() => {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      void request.setVariables({
        example: {
          ...DoctorsGenericEngineDefaultExample($ClientsSearchQuery),
        },
      });
    }, 100);
  })();

  const navigate = () => {
    if ($ClientsSelected.length > 1) {
      f7router.navigate(`/people/${JSON.stringify($ClientsSelected)}/`);
    } else {
      f7router.navigate(`/doctor/${JSON.stringify($ClientsSelected[0])}/`);
    }
  };
</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <Block style="margin-top: -5px">
    <RequestLayout {...$request} refetch={request.refetch}>
      <QueryResultsHeader
        length={$doctors.length}
        description="Showing search results from this clinic"
        on:select={() => {
          $ClientsSelected = $doctors.map((doctor) => ({ type: doctor.__typename, id: doctor.did.id }));
          navigate();
        }}
      />
      <DoctorsTabSelectableList
        doctors={$doctors ?? []}
        selected={ClientsSelected}
        on:navigate={navigate}
      />
    </RequestLayout>
  </Block>
</PageContent>
