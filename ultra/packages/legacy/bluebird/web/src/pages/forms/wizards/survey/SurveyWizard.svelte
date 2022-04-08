<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';

  import SurveyTemplateEngine from '@framework/engines/surveys/SurveyTemplateEngine';

  import WizardUI from '@framework/ui/layout/WizardUI/WizardUI.svelte';

  import SurveyLayout from '@framework/components/layout/SurveyLayout.svelte';

  export let selectable: Selectable;

  const { survey } = SurveyTemplateEngine({
    surveys: [{ id: selectable.id }],
  });

</script>

<WizardUI
  overflow={false}
  color="deeppurple"
  submit={false}
>
  {#if $survey}
    <br />
    <br />
    <br />
    <SurveyLayout template={$survey} />
  {/if}
</WizardUI>
