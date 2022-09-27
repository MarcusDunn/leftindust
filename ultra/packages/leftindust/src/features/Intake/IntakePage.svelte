<script lang="ts">
  import getNativeAPI from '@/api/bridge';
  import { client, SurveyLinkByIdQueryDocument } from '@/api/server';

  import { f7ready } from 'framework7-svelte';

  import { onMount } from 'svelte';

  import Page from '../UI/components/Page/Page.svelte';
  import { openWizard } from '../Wizard';

  import { _ } from '@/language';

  const { Dialog } = getNativeAPI();

  onMount(() => {
    f7ready(() => {
      const url = new URLSearchParams(window.location.search);
      if (url.has('id')) {
        const id = url.get('id');

        console.log(id);

        client.query(SurveyLinkByIdQueryDocument, {
          surveyLinkId: { value: id },
        }).toPromise()
          .then(({ data }) => {
            setTimeout(() => {
              if (data) openWizard('/wizard/', {
                template: data.surveyLinkById?.surveyTemplate,
              });
            }, 200);
          }).catch((error) => {
            void Dialog.alert({
              message: 'Form Intake',
              detail: error.message,
              buttons: [$_('generics.ok')],
              defaultId: 0,
            });
          });
      } else {
        void Dialog.alert({
          message: 'Form Intake',
          detail: 'No form link was provided.',
          buttons: [$_('generics.ok')],
          defaultId: 0,
        });
      }
    });
  });
</script>

<Page />