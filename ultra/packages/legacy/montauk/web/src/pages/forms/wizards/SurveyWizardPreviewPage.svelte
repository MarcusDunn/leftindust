<script>
import AppBarUI from '@delta/library/src/ui/controller/AppBarUI/AppBarUI.svelte';
import PageUI from '@delta/library/src/ui/layout/PageUI/PageUI.svelte';

import SurveyLayout from '@delta/library/src/components/layout/SurveyLayout.svelte';
import { SurveyWizardActiveSectionIndex, SurveyWizardForm } from './store';

</script>
<PageUI>
  <AppBarUI slot="fixed" />
  <SurveyLayout
    template={$SurveyWizardForm}
    preview={$SurveyWizardActiveSectionIndex}
  />
</PageUI>
