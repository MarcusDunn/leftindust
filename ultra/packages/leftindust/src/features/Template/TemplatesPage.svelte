<script lang="ts">
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import { Block } from 'framework7-svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { openWizard } from '../Wizard';
  import { WidgetType } from '../Widgets';
  import { operationStore, query } from '@urql/svelte';
  import { defaultRangeInput, PartialSurveyTemplateByRangeQueryDocument, type PartialTemplateFragmentFragment } from '@/api/server';

  let templates: PartialTemplateFragmentFragment[];

  const request = operationStore(PartialSurveyTemplateByRangeQueryDocument, {
    range: defaultRangeInput,
  });

  query(request);
  
  $: templates = $request.data?.surveyTemplateByRange ?? [];

  $: console.log(templates);

</script>

<Page>
  <AppBar
    slot="fixed"
    right={[{
      title: $_('generics.create'),
      icon: { f7: 'plus_circle_fill', color: 'deeppurple' },
      onClick: () => openWizard('/wizard/template/'),
    }]}
  />
  <Block class="no-margin-top">
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
      />
    {/if}
  </Block>
</Page>
