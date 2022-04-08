<script lang="ts">
  import type { Writable } from 'svelte/store';

  import type { ReadableQuery } from 'svelte-apollo-client';
  import type { BasicIcdFragment } from '../../../requests/fragments';
  import type { IcdSearchQueryResult } from '../../../requests/queries';
  
  import language from '../../../languages';
  
  import IcdSearchEngine from '../../../engines/icd/IcdSearchEngine';

  import {
    Block,
    Row,
    Col,
    Searchbar,
  } from 'framework7-svelte';

  import AppBarUI from '../../../ui/controller/AppBarUI/AppBarUI.svelte';
  import InsetListUI from '../../../ui/list/InsetListUI/InsetListUI.svelte';
  import PageUI from '../../../ui/layout/PageUI/PageUI.svelte';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import CollapsableContentPlaceholderUI
    from '../../../ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import IcdCheckboxItem from '../../../components/item/icd/IcdCheckboxItem.svelte';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';

  export let attachments: Writable<string[]>;
  export let multiple: boolean;
  export let submit: () => void;
  export let back: boolean;

  let request: ReadableQuery<IcdSearchQueryResult>;

  let icds: BasicIcdFragment[];
  
  let query = '';
  let timeout: ReturnType<typeof setTimeout>;

  const search = (text: string): void => {
    if (query) {
      if (request) {
        icds = [];
        clearTimeout(timeout);
        timeout = setTimeout(() => {
          void request.refetch({ query: text });
        }, 150); 

        request.subscribe((result) => {
          icds = result.data?.searchIcd.destinationEntities ?? [];
        });
      } else {
        const engine = IcdSearchEngine({ query });
        request = engine.request;

        search(text);
      }
    }
  };

  $: search(query);

</script>

<PageUI>
  <AppBarUI
    slot="fixed"
    navigation={{ back, close: { popover: !back } }}
  >
    <Searchbar
      slot="center"
			class="color-purple"
			customSearch
			inline
			disableButton={false}
			placeholder={language().icds.search.text}
      style="width: 275px"
      bind:value={query}
		/>
    <svelte:fragment slot="right">
      <MenuButtonUI
        title={language().buttons.done.text}
        icon={{ f7: 'checkmark_circle_fill', color: 'purple' }}
        on:click={submit}
      />
    </svelte:fragment>
  </AppBarUI>
  <br />
  {#if query}
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if icds.length > 0}
        <Block>
          <Row class="no-gutter">
            {#each icds as icd (icd.id)}
              <Col width="33" style="margin-bottom: 10px">
                <InsetListUI>
                  <IcdCheckboxItem 
                    icd={{
                      ...icd,
                      __typename: 'IcdLinearizationEntity',
                    }}
                    {multiple}
                    bind:selected={$attachments}
                  />
                </InsetListUI>
              </Col>
            {/each}
          </Row>
        </Block>
      {:else}
        <br />
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noResults.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
  {:else}
    <br />
    <CollapsableContentPlaceholderUI center>
      {language().icds.placeholders.search.text}
    </CollapsableContentPlaceholderUI>
  {/if}
</PageUI>