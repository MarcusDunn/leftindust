<script lang="ts">
  import { CompleteSurveyByIdQueryDocument, type CompleteSurveyFragmentFragment, type Data } from '@/api/server';
  import { operationStore } from '@urql/svelte';
  import type { Router } from 'framework7/types';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { _ } from '@/language';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '../UI/components/Collapsable/CollapsableContent.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  export let quicklook = false;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  let record: CompleteSurveyFragmentFragment | undefined;

  const request = operationStore(CompleteSurveyByIdQueryDocument, {
    completeSurveyId: { value: data.id },
  });

  $: record = $request.data?.completeSurveyById;
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar
      close={{ popover: quicklook }}
      history={!quicklook}
      right={!quicklook ? [
        {
          title: $_('generics.edit'),
          icon: { f7: 'pencil_outline', color: 'gray' },
          condense: true,
        },
      ] : []}
      {f7router}
    />
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if record}
      <Profile>
        <h2 slot="title">{record.surveyTemplate.title}</h2>
      </Profile>
      <Block>
        {#each record.sections as section, sectionIndex}
          {#each section.inputs as input, inputIndex}
            <CollapsableContent
              title={record.surveyTemplate.sections[sectionIndex].inputs[inputIndex].label}
            >
              <Block class="no-margin" strong inset>
                {input}
              </Block>
            </CollapsableContent>
            <br />
          {/each}
        {/each}
      </Block>
    {/if}
  </Request>
</Page>