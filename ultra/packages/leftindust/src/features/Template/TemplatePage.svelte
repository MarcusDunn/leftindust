<script lang="ts">
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import type { Router } from 'framework7/types';
  import { operationStore, query } from '@urql/svelte';
  import { SurveyTemplateByIdQueryDocument, type Data, type PartialTemplateFragmentFragment, type TemplateFragmentFragment } from '@/api/server';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import TemplateTags from './components/TemplateTags/TemplateTags.svelte';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '../UI/components/Collapsable/CollapsableContent.svelte';
  import RecordCalculation from '../Record/components/RecordCalculation/RecordCalculation.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  
  export let quicklook = false;

  let template: TemplateFragmentFragment | undefined;
  
  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(SurveyTemplateByIdQueryDocument, {
    surveyTemplateId: { value: data.id },
  });

  $: template = $request.data?.surveyTemplateById;

  query(request);
</script>

<Page>
  <Appbar
    slot="fixed"
    close={{ popup: quicklook }}
    history={!quicklook}
    {f7router}
  />
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if template}
      <Profile>
        <h2 slot="title">{template.title}</h2>
        <TemplateTags {template} slot="tags" />
        {#if template.subtitle}
          <p>{template.subtitle}</p>
        {/if}
      </Profile>
      <Block style="margin-left: 35px;margin-right: 35px">
        <CollapsableContent title="Preview">
        
        </CollapsableContent>
        <p />
        <CollapsableContent title="Calculations">
          {#each template.calculations as calculation}
            <RecordCalculation {calculation} />
          {/each}
        </CollapsableContent>
      </Block>
    {/if}
  </Request>
</Page>
