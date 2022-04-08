<script lang="ts">
  import type { BasicSurveyTemplateFragment } from '../../../requests/fragments';

  import type { Selectable } from '../../../modules/SelectModule';

  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  import SurveyTags from '../../../components/tags/SurveyTags.svelte';

  export let survey: BasicSurveyTemplateFragment;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: survey.__typename,
    id: survey.id.id ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  title={survey.name}
  {value}
  {multiple}
  bind:selected={selected}
>
  <SurveyTags
    slot="text"
    sectionLength={survey.sections.length}
    fieldsLength={(() => {
      let number = 0;
      survey.sections.forEach((section) => {
        number += section.fields.length;
      });

      return number;
    })()}
    small
    outline
  />
</CheckboxItemUI>
