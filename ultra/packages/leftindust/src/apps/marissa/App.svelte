<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';

  import { _ } from '@/language';

  import { f7ready, List } from 'framework7-svelte';

  import { account } from '@/features/Account/store';

  import { AppViews, AppRootRoutes } from '@/features/App';
  import { openPopupUrl } from '@/features/View';

  import { appActiveTab } from '@/features/App/store';

  import AppLayout from '@/features/App/components/AppLayout/AppLayout.svelte';
  import UserCell from '@/features/User/components/UserCell/UserCell.svelte';

  export let f7params: Framework7Parameters;

  $: if ($account?.accountDetails?.isRegistered && !$account?.database.settings.setup)
    f7ready(() => setTimeout(() => openPopupUrl('/setup/'), 100));
</script>

<AppLayout
  items={[
    { title: $_('generics.thisClinic') },
    {
      title: $_('generics.clients'),
      id: AppViews.Clients,
      url: AppRootRoutes.Clients,
      icon: { f7: 'person_2' },
      color: 'purple',
      active: $appActiveTab === AppViews.Clients,
      onClick: () => $appActiveTab = AppViews.Clients,
      master: true,
    },
    { title: $_('generics.templates') },
    {
      title: $_('generics.allTemplates'),
      id: AppViews.Templates,
      url: AppRootRoutes.Templates,
      icon: { f7: 'square_grid_2x2' },
      color: 'deeppurple',
      active: $appActiveTab === AppViews.Templates,
      onClick: () => $appActiveTab = AppViews.Templates,
    },
  ]}
  {f7params}
>
  <div slot="footer">
    <List mediaList noChevron noHairlines>
      <UserCell
        user={$account}
        on:click={() => openPopupUrl('/settings/')}
      />
    </List>
  </div>
</AppLayout>