<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { SelectableRouteParams } from '@/routes';


  import { PluginTypes } from '@framework/plugins';

  import PatientEngine from '@framework/engines/patients/PatientEngine';
  import { WIZARD_ACTIVE } from '@framework/store';
  import { ClientsSelected } from '../../store/ClientsStore';
  import VisitsReferencedEngine from '@framework/engines/visits/VisitsReferencedEngine';
  
  import { Block } from 'framework7-svelte';

  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';

  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  const { patient } = PatientEngine({ pids: [{ id: selectable.id }] });

  const { request, visits } = VisitsReferencedEngine({
    selectable,
  });
</script>

<PageUI on:pageAfterIn={() => !$WIZARD_ACTIVE && ($ClientsSelected = [selectable])}>
  <svelte:fragment slot="fixed">
    <AppBarUI
      navigation={{ history: true }}
      {f7router}
    />
  </svelte:fragment>
  <Block class="no-margin-top">
    <Block>
      <RequestLayout {...$request} refetch={request.refetch} large middle>
        {#if $patient?.firstName}
          <h2>{$patient.firstName}'s Visits</h2>
        {/if}
        <br />
        <SpecificPluginGrid
          props={$visits.map((visit) => {
            if (visit.__typename) {
              return {
                id: visit.__typename,
                selectable: {
                  id: visit.vid.id,
                  type: visit.__typename,
                },
              };
            }
          })}
          type={PluginTypes.Widget}
        />
      </RequestLayout>
    </Block>
  </Block>
</PageUI>
