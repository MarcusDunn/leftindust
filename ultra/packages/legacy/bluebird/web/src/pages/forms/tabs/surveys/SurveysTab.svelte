<script lang="ts">
  import type { Router } from 'framework7/types';

  import { PageContent, Block } from 'framework7-svelte';
  import { DEFAULT_RANGE_INPUT } from '@framework/requests';
  import { PluginTypes } from '@framework/plugins';


  import SurveyTemplatesGenericEngine from '@framework/engines/surveys/SurveyTemplatesGenericEngine';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';

  const { request, surveys } = SurveyTemplatesGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  });

  export let f7router: Router.Router;
  export let f7route: Router.Route;

</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <RequestLayout {...$request} refetch={request.refetch}>
    <Block class="no-margin-top">
      <SpecificPluginGrid
        props={$surveys.map((selectable) => {
          if (selectable.__typename) {
            return {
              id: selectable.__typename,
              selectable: {
                id: selectable.id.id,
                type: selectable.__typename,
              },
            };
          }
        })}
        type={PluginTypes.Widget}
      />
    </Block>
  </RequestLayout>
</PageContent>
