<script lang="ts">
  import language from '@framework/languages/index';

  import { Page } from 'framework7-svelte';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import NoticeUI from '@framework/ui/layout/NoticeUI/NoticeUI.svelte';
  import ErrorButtonsController from '@framework/components/controller/ErrorButtonsController.svelte';

</script>

<Page>
  <AppBarUI />
  <NoticeUI
    title={language().errors.internalError.text}
    text={language().errors.internalErrorInstructions.text}
    icon={{ f7: 'exclamationmark_triangle_fill', color: 'orange' }}
    large
    middle
  >
    <ErrorButtonsController />
  </NoticeUI>
</Page>
