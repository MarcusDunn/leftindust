<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { DoctorsFragment, PatientsFragment } from '@/api/server';

  import { Block } from 'framework7-svelte';

  import Page from '../UI/components/Page/Page.svelte';
  import type { Data } from '@/api/server';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import { clientsSelected } from '../Clients/store';
  import { wizardOpen } from '../Wizard/store';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  const people: Data<DoctorsFragment['__typename'] | PatientsFragment['__typename']>[] = JSON.parse(f7route.params.data ?? '{}');
</script>

<Page on:pageAfterIn={() => !$wizardOpen && ($clientsSelected = people)}>
  <svelte:fragment slot="fixed">
    <Appbar
      history
      {f7router}
    >
    </Appbar>
  </svelte:fragment>

  <Block class="no-margin-top">
    <Block style="margin-top: 30px">
      <h2>{people.length} Selected</h2>
      <br />
    </Block>
  </Block>
</Page>
