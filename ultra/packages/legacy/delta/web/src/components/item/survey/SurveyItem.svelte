<script lang="ts">
  import type { BasicSurveyTemplateFragment } from '../../../requests/fragments';

  import SurveyTags from '../../../components/tags/SurveyTags.svelte';
  import { ListItem } from 'framework7-svelte';

  export let link = false;
  export let survey: BasicSurveyTemplateFragment;

</script>

<ListItem title={survey.name} {link}>
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
</ListItem>
