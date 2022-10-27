<script lang="ts">
  import { CompleteSurveyByIdQueryDocument, type CompleteSurveyFragmentFragment, type Data } from '@/api/server';
  import { operationStore, query } from '@urql/svelte';
  import type { Router } from 'framework7/types';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { _ } from '@/language';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '../UI/components/Collapsable/CollapsableContent.svelte';
  import RecordTags from './components/RecordTags/RecordTags.svelte';
  import Input from '../Input/Input.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  export let quicklook = false;
  let record: CompleteSurveyFragmentFragment | undefined;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(CompleteSurveyByIdQueryDocument, {
    completeSurveyId: { value: data.id },
  });

  query(request);

  $: record = $request.data?.completeSurveyById;
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar
      close={{ popover: quicklook }}
      history={!quicklook}
      {f7router}
    />
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if record}
      <Profile>
        <h2 slot="title">{record.surveyTemplate.title}</h2>
        <RecordTags slot="tags" {...record} />
      </Profile>
      <Block style="margin: 0 40px">
        {#each record.sections as section, sectionIndex}
          <CollapsableContent
            title={record.surveyTemplate.sections[sectionIndex].title}
          >
            {#each section.inputs as input, inputIndex}
              <p />
              <Input title={record.surveyTemplate.sections[sectionIndex].inputs[inputIndex].label}>
                <input
                  type="text"
                  placeholder="Value"
                  readonly
                  value={(() => {
                    if ('string' in input) {
                      return input['string'];
                    } else if ('number' in input) {
                      return input['number'];
                    } else if ('stringArray' in input) {
                      return input['stringArray'];
                    } else if ('numberArray' in input) {
                      return input['numberArray'];
                    }
                  })()}
                />
              </Input>
              <br />
            {/each}
          </CollapsableContent>
        {/each}
      </Block>
    {/if}
  </Request>
</Page>