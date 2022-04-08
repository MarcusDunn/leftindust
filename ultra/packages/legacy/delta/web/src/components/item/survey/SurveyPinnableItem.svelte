<script lang="ts">
  import type { BasicSurveyTemplateFragment } from '../../../requests/fragments';
  
  import { createEventDispatcher } from 'svelte';
  
  import PinnableItemUI from '../../../ui/item/PinnableItemUI/PinnableItemUI.svelte';
  
  import SurveyTags from '../../../components/tags/SurveyTags.svelte';

  const dispatch = createEventDispatcher();

  export let survey: BasicSurveyTemplateFragment;
  export let pinned: boolean;
  

</script>

<PinnableItemUI
  title={survey.name}
  {pinned}
  on:pin={({ detail }) => dispatch('pin', detail)}
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
</PinnableItemUI>