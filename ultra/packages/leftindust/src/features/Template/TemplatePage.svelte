<script lang="ts">
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import type { Router } from 'framework7/types';
  import { operationStore, query } from '@urql/svelte';
  import { PartialSurveyTemplateByIdQueryDocument, type Data, type PartialTemplateFragmentFragment } from '@/api/server';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  
  export let quicklook = false;

  let template: PartialTemplateFragmentFragment | undefined;
  
  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(PartialSurveyTemplateByIdQueryDocument, {
    surveyTemplateId: { value: data.id },
  });

  $: template = $request.data?.surveyTemplateById;

  query(request);
</script>

<Page>
  <AppBar
    slot="fixed"
    close={{ popup: quicklook }}
    history={!quicklook}
    {f7router}
  />
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if template}
      <Profile>
        <h2 slot="title">{template.title}</h2>
      </Profile>
    {/if}
  </Request>
</Page>
