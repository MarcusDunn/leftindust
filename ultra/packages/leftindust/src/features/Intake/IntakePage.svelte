<script lang="ts">
  import { client, SurveyLinkByIdQueryDocument } from '@/api/server';

  import { f7ready } from 'framework7-svelte';

  import { onMount } from 'svelte';

  import Page from '../UI/components/Page/Page.svelte';
  import { openWizard } from '../Wizard';

  import { _ } from '@/language';
  import { openDialog } from '../UI/components/Dialog';

  onMount(() => {
    f7ready(() => {
      const url = new URLSearchParams(window.location.search);
      if (url.has('id')) {
        const id = url.get('id');

        client({ authentication: false }).query(SurveyLinkByIdQueryDocument, {
          surveyLinkId: { value: id },
        }).toPromise()
          .then(({ data }) => {
            setTimeout(() => {
              if (data) openWizard('/wizard/', {
                template: data.surveyLinkById?.surveyTemplate,
                id: data.surveyLinkById?.id,
                expired: !!data.surveyLinkById?.completedSurvey,
              });
            }, 200);
          }).catch((error) => {
            openDialog({
              title:  'Form Intake',
              text: error.message,
              buttons: [
                {
                  label: $_('generics.ok'),
                  primary: true,
                },
              ],
            });
          });
      } else {
        openDialog({
          title:  'Form Intake',
          text: 'No form link was provided.',
          buttons: [
            {
              label: $_('generics.ok'),
              primary: true,
            },
          ],
        });
      }
    });
  });
</script>

<Page />