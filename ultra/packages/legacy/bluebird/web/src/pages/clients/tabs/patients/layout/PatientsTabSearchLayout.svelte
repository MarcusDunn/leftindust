<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  
  import {
    ClientsSearchQuery,
    ClientsSelected,
    PatientsSelectedAttachments,
    PatientsSelectedAttachmentsFragments,
  } from '@/pages/clients/store/ClientsStore';

  import PatientsGenericEngine, {
    PatientsGenericEngineDefaultExample,
  }  from '@framework/engines/patients/PatientsGenericEngine';
  
  import { PageContent, Block } from 'framework7-svelte';
  
  import InsetListUI from '@framework/ui/list/InsetListUI/InsetListUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';
  
  import QueryResultsHeader from '@framework/components/header/QueryResultsHeader.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import PatientsTabSelectableList from '../list/PatientsTabSelectableList.svelte';
  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
  import IcdItem from '@framework/components/item/icd/IcdItem.svelte';
  import type { FoundationIcdCodeInput } from '@framework/schemas/leftindust.schema';
  
  export let f7router: Router.Router;
  
  let timeout: ReturnType<typeof setTimeout>;

  const variables = (query: string, attachments: Selectable[]) => {
    const icds = attachments.map((attachment) => {
      if (attachment.type === 'IcdSimpleEntity' || attachment.type === 'IcdLinearizationEntity') {
        return { url: attachment.id };
      }
    }).filter((attachment): attachment is FoundationIcdCodeInput => !!attachment);

    return {
      example: {
        ...PatientsGenericEngineDefaultExample(query),
        icdCodes: icds.length > 0 ? {
          strict: true,
          includes: icds,
        } : undefined,
      },
    };
  };

  const { request, patients } = PatientsGenericEngine(variables($ClientsSearchQuery, $PatientsSelectedAttachments));
  
  const navigate = () => {
    if ($ClientsSelected.length > 1) {
      f7router.navigate(`/people/${JSON.stringify($ClientsSelected)}/`);
    } else {
      f7router.navigate(`/patient/${JSON.stringify($ClientsSelected[0])}/`);
    }
  };
  
  const remove = (attachment: Selectable) => {
    $PatientsSelectedAttachments = $PatientsSelectedAttachments.filter(({ id }) => id !== attachment.id);
    delete $PatientsSelectedAttachmentsFragments[attachment.id];
    $PatientsSelectedAttachmentsFragments = $PatientsSelectedAttachmentsFragments;
  };
  
  // This any typed function is nessesary to prevent prop type errors that cannot be fixed
  const getFragment = (attachment: Selectable): any => {
    return $PatientsSelectedAttachmentsFragments[attachment.id];
  };

  $: (() => {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      void request.setVariables(variables($ClientsSearchQuery, $PatientsSelectedAttachments));
    }, 100);
  })();
  
  $: fragmentsLength = Object.keys($PatientsSelectedAttachmentsFragments).length;
</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <Block style="margin-top: -5px">
    <CollapsableContentUI title="Filters">
      {#if fragmentsLength > 0}
        <p />
        {#if fragmentsLength === $PatientsSelectedAttachments.length}
          {#each $PatientsSelectedAttachments as attachment}
            {#if attachment.type === 'IcdSimpleEntity' || attachment.type === 'IcdLinearizationEntity'}
              <InsetListUI>
                <IcdItem
                  icd={getFragment(attachment)}
                  link={false}
                  on:delete={() => remove(attachment)}
                />
              </InsetListUI>
              <p />
            {/if}
          {/each}
        {/if}
      {:else}
        <CollapsableContentPlaceholderUI center>
          No filters have been applied
        </CollapsableContentPlaceholderUI>
      {/if}
    </CollapsableContentUI>
    <br />
    <RequestLayout {...$request} refetch={request.refetch}>
      <QueryResultsHeader
        length={$patients.length}
        description="Showing search results from this clinic"
        on:select={() => {
          $ClientsSelected = $patients.map((patient) => ({ type: patient.__typename, id: patient.id.value }));
          navigate();
        }}
      />
      <PatientsTabSelectableList
        patients={$patients ?? []}
        selected={ClientsSelected}
        on:navigate={navigate}
      />
    </RequestLayout>
  </Block>
</PageContent>
