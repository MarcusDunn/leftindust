<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';
  
  import { AppViews, AppRootRoutes } from '@/features/App';
  import AppLayout from '@/features/App/components/AppLayout/AppLayout.svelte';

  import routes from './routes';

  export let f7params: Framework7Parameters;
</script>

<AppLayout
  items={[
    {
      title: '',
      id: AppViews.Hub,
      url: AppRootRoutes.Home,
      active: true,
    },
  ]}
  sidebar={false}
  f7params={{
    ...f7params,
    routes,
  }}
/>
