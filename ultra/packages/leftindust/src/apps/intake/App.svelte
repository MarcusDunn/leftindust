<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';
  import AppLayout from '@/features/App/components/AppLayout/AppLayout.svelte';
  import { AppViews, AppRootRoutes } from '@/features/App';

  export let f7params: Framework7Parameters;

</script>

<AppLayout
  items={[{
    title: '',
    id: AppViews.Main,
    url: AppRootRoutes.Home,
    active: true,
  }]}
  {f7params}
  sidebar={false}
  showLoginScreen={false}
/>

