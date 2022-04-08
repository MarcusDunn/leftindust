<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Writable } from 'svelte/store';
  import type { Plugin } from '../../';

  import { WIZARD_ACTIVE } from '../../../store';
  import language from '../../../languages/index';
  import { openWizard } from '../../../modules/NavigationModule';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';

  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  import SurveyTemplatesSpecificEngine from '../../../engines/surveys/SurveyTemplatesSpecificEngine';
  import SurveyTags from '../../../components/tags/SurveyTags.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable<'AssignedSurvey'>;
  export let properties: NonNullable<Plugin['properties']>;
  export let attachments: Writable<Selectable[]> | undefined = undefined;

  const { surveys } = SurveyTemplatesSpecificEngine({
    surveys: [{ id: selectable.id }],
  });

  $: survey = $surveys[0];

  const url = '/wizard/survey/';
</script>


<PluginWidgetUI
  color={properties.color}
  {dragger}
  loading={!survey}
  shadow={!attachments}
>
  <svelte:fragment slot="title">
    <div class="two-line-word-clamp">{survey.name}</div>
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <div>
      <SurveyTags
        sectionLength={survey.sections.length}
        fieldsLength={(() => {
          let number = 0;
          survey.sections.forEach((section) => {
            number += section.fields.length;
          });

          return number;
        })()}
      />
    </div>
  </svelte:fragment>

  <svelte:fragment slot="controls">
    <Row>
      <Col width="100">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== selectable.id);
            }}
          >
            <Icon f7="trash_fill" />
            {language().buttons.delete.text}
          </Button>
        {:else}
          {#if !$WIZARD_ACTIVE}
            <Button
              round
              fill
              color="deeppurple"
              on:click={() => openWizard(url, { selectable })}
            >
              Take Survey
            </Button>
          {/if}
        {/if}
      </Col>
    </Row>
  </svelte:fragment>
</PluginWidgetUI>
