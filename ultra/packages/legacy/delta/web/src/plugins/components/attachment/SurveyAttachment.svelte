<script lang="ts">
  import type { Writable } from 'svelte/store';

  import language from '../../../languages';
  import { DEFAULT_RANGE_INPUT } from '../../../requests';

  import { Block, Col, Row } from 'framework7-svelte';

  import PageUI from '../../../ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '../../../ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import InsetListUI from '../../../ui/list/InsetListUI/InsetListUI.svelte';
  import CollapsableContentPlaceholderUI
    from '../../../ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import SurveyTemplatesGenericEngine from '../../../engines/surveys/SurveyTemplatesGenericEngine';
  import SurveyCheckboxItem from '../../../components/item/survey/SurveyCheckboxItem.svelte';

  export let attachments: Writable<string[]>;
  export let multiple: boolean;
  export let submit: () => void;
  export let back: boolean;

  const { request, surveys } = SurveyTemplatesGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  });
</script>

<PageUI>
  <AppBarUI
    slot="fixed"
    navigation={{ back, close: !back }}
  >
    <svelte:fragment slot="right">
      <MenuButtonUI
        title={language().buttons.done.text}
        icon={{ f7: 'checkmark_circle_fill', color: 'purple' }}
        on:click={submit}
      />
    </svelte:fragment>
  </AppBarUI>
  <Block>
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if $surveys.length > 0}
        <Row class="no-gutter">
          {#each $surveys as survey}
            <Col width="33" style="margin-bottom: 10px">
              <InsetListUI>
                <SurveyCheckboxItem {survey} bind:selected={$attachments} {multiple} />
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
  </Block>
</PageUI>
