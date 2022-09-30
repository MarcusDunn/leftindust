<script lang="ts">
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import { Block } from 'framework7-svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { openWizard } from '../Wizard';
  import { WidgetType } from '../Widgets';
  import { operationStore, query } from '@urql/svelte';
  import { defaultRangeInput, PartialSurveyTemplateByRangeQueryDocument, type PartialTemplateFragmentFragment } from '@/api/server';
  import type { Router } from 'framework7/types';
  import CollapsableContentPlaceholder from '../UI/components/Collapsable/CollapsableContentPlaceholder.svelte';
  import Request from '../Server/components/Request/Request.svelte';

  export let f7router: Router.Router;
  
  let templates: PartialTemplateFragmentFragment[];

  const request = operationStore(PartialSurveyTemplateByRangeQueryDocument, {
    range: defaultRangeInput,
  });

  query(request);
  
  $: templates = $request.data?.surveyTemplateByRange ?? [];
</script>

<Page>
  <Appbar
    slot="fixed"
    right={[{
      title: $_('generics.create'),
      icon: { f7: 'plus_circle_fill', color: 'deeppurple' },
      onClick: () => openWizard('/wizard/template/', {
        callback: () => request.reexecute(),
      }),
    }]}
    history
    {f7router}
  />
  <Block style="height: calc(100% - 120px);overflow-y: scroll">
    <Request style="height: 100%" {...$request} refetch={request.reexecute} middle large>
      {#if templates.length > 0}
        <SpecificGrid
          props={templates.map((template) => ({
            id: template.__typename,
            data: {
              id: template.id.value,
              type: template.__typename,
            },
          }))}
          type={WidgetType.Card}
          fixed
        />
      {:else}
        <div style="
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          height: 100%;
        ">
          <CollapsableContentPlaceholder center>
            No templates found...
          </CollapsableContentPlaceholder>
        </div>
      {/if}
    </Request>
  </Block>
</Page>
