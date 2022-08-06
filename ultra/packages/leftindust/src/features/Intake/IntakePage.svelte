<script lang="ts">
  import { f7ready } from 'framework7-svelte';

  import { onMount } from 'svelte';

  import Page from '../UI/components/Page/Page.svelte';
  import { openWizard } from '../Wizard';

  onMount(() => {
    f7ready(() => {
      setTimeout(() => {
        openWizard('/wizard/');
      }, 200);
    });
  });
</script>

<Page />