<script lang="ts">
  import { client, SurveyLinkByIdQueryDocument } from '@/api/server';

  import { f7ready } from 'framework7-svelte';

  import { onMount } from 'svelte';

  import Page from '../UI/components/Page/Page.svelte';
  import { openWizard } from '../Wizard';

  const returnToHome = () => window.location.href = 'https://leftindust.com';

  onMount(() => {
    f7ready(() => {
      const url = new URLSearchParams(window.location.search);
      if (url.has('id')) {
        const id = url.get('id');

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
            console.error(error);
          });
      } else {
        returnToHome();
      }
    });
  });
</script>

<Page />