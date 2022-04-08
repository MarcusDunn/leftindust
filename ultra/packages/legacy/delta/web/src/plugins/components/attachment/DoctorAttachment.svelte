<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { BasicDoctorFragment } from '../../../requests/fragments';

  import { SortableField } from '../../../schemas/leftindust.schema';

  import language from '../../../languages';
  import { DEFAULT_RANGE_INPUT } from '../../../requests';
  import { ACCOUNT } from '../../../store';

  import DoctorsGenericEngine, {
    DoctorsGenericEngineDefaultExample,
  } from '../../../engines/doctors/DoctorsGenericEngine';
  import DoctorsSpecificEngine from '../../../engines/doctors/DoctorsSpecificEngine';

  import { Col, Row, Searchbar } from 'framework7-svelte';

  import PageUI from '../../../ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '../../../ui/controller/AppBarUI/AppBarUI.svelte';
  import InsetListUI from '../../../ui/list/InsetListUI/InsetListUI.svelte';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import CollapsableContentPlaceholderUI
    from '../../../ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import MasterListLayout from '../../../components/layout/MasterListLayout.svelte';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import DoctorCheckboxItem from '../../../components/item/doctor/DoctorCheckboxItem.svelte';

  export let attachments: Writable<string[]>;
  export let multiple: boolean;
  export let submit: () => void;
  export let back: boolean;

  let doctors: BasicDoctorFragment[] = [];

  let query = '';
  let timeout: ReturnType<typeof setTimeout>;

  let variables = {
    range: DEFAULT_RANGE_INPUT,
    sortBy: SortableField.LastName,
  };

  const { request } = DoctorsGenericEngine(variables);
  const { request: recentsRequest, doctors: recents } = DoctorsSpecificEngine({
    dids: ($ACCOUNT.database.recents.Doctor ??= []).map((id) => ({ id })),
  });

  $: doctors = $request?.data?.doctors ?? [];
  $: (() => {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      void request.setVariables({
        ...(query ? {
          example: {
            ...DoctorsGenericEngineDefaultExample(query),
          },
        } : variables),
      });
    }, 100);
  })();

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
			placeholder={language().clients.doctors.search.text}
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
  <br />
  <p />
  <MasterListLayout>
    <RequestLayout {...$recentsRequest} refetch={recentsRequest.refetch} slot="recents">
      {#if $recents.length > 0}
        <Row class="no-gutter">
          {#each $recents as doctor}
            <Col width="33" style="margin-bottom: 10px">
              <InsetListUI>
                <DoctorCheckboxItem {doctor} bind:selected={$attachments} {multiple} />
              </InsetListUI>
            </Col>
          {/each}
        </Row>
        <br />
      {:else}
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noRecents.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if doctors.length > 0}
        <Row class="no-gutter">
          {#each doctors as doctor}
            <Col width="33" style="margin-bottom: 10px">
              <InsetListUI>
                <DoctorCheckboxItem {doctor} bind:selected={$attachments} {multiple} />
              </InsetListUI>
            </Col>
          {/each}
        </Row>
      {:else}
        <br />
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noResults.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
  </MasterListLayout>
</PageUI>
