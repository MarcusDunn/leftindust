<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';
  import type { Popover } from 'framework7/types';
  
  import { Button, Col, Row } from 'framework7-svelte';
  
  import { writable } from 'svelte/store';
  import language from '../../../languages/index';
  import { openPopover } from '../../../modules/NavigationModule';
  
  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';

  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';
  import SurveyTemplatesReferencedEngine
    from '../../../engines/surveys/SurveyTemplatesReferenceEngine';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import SurveyItem from '../../../components/item/survey/SurveyItem.svelte';
  import AttachmentsModal from '../../../components/modal/AttachmentsModal.svelte';
  import SurveyTemplateAssignEngine from '../../../engines/surveys/SurveyTemplateAssignEngine';

  export let selectable: Selectable;
  export let dragger: () => void | undefined;
  export let properties: NonNullable<Plugin['properties']>;
  export let quicklook = false;

  const { request, surveys } = SurveyTemplatesReferencedEngine({ selectable });

  const attachments = writable<Selectable[]>([]);
  
  let attachmentsPopover: Popover.Popover;
  
  const assignSurvey = () => {
    if ($attachments.length > 0) {
      $attachments.forEach((attachment) => {
        void SurveyTemplateAssignEngine({
          patients: [{ id: selectable.id }],
          survey: { id: attachment.id },
        });
      });
      
      $attachments = [];
    }
  };
  
  $: $attachments = $surveys.map((survey) => ({ type: 'GraphQLFormTemplate', id: survey.surveyTemplate.id.id }));
</script>

<AttachmentsModal
  types={['GraphQLFormTemplate']}
  bind:instance={attachmentsPopover}
  on:change={() => assignSurvey()}
/>

<PluginBundleUI
  title={properties.title}
  icon={properties.icon}
  color={properties.color}
  {dragger}
  shadow
>
  <RequestLayout {...$request} refetch={request.refetch} middle>
    {#if $surveys.length > 0}
      <PinnableListUI>
        {#each $surveys.filter((_, index) => index <= 3) as survey}
          <SurveyItem
            survey={survey.surveyTemplate}
          />
        {/each}
      </PinnableListUI>
    {:else}
      <div style="margin-top: 40px">
        <PluginPlaceholderLayout
          title={language().survey.placeholders.noSurveys.text}
          description={language().survey.placeholders.addSurveyPlaceholder(
            `${language().positions.bottom.text} ${language().positions.left.text}`,
          ).text}
          link={{
            label: language().survey.placeholders.learnMore.text,
            href: '#',
          }}
        />
      </div>
    {/if}
  </RequestLayout>

  <Row slot="controls">
    <Col width="50">
      <div on:click={(event) => openPopover(attachmentsPopover, event)}>      
        <Button
          class={`${quicklook ? 'disabled' : ''}`}
          color={properties.color}
          fill
          round
        >
          Add Survey
        </Button>
      </div>
    </Col>
    <Col width="50">
      <Button
        color={properties.color}
        round
        outline
        disabled
      >
        {language().buttons.viewAll.text}
      </Button>
    </Col>
  </Row>
</PluginBundleUI>
