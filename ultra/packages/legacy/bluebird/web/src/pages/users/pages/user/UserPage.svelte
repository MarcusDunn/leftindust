<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Popover, Router } from 'framework7/types';
  import type { SelectableRouteParams } from '@/routes';
  
  import { EmailType } from '@framework/schemas/leftindust.schema';

  import language from '@framework/languages/index';

  import { ACCOUNT, WIZARD_ACTIVE } from '@framework/store/index';
  import { openPopover, openWizard } from '@framework/modules/NavigationModule';
  
  import { UsersSelected } from '../../store/UsersStore';
  
  import UserEngine from '@framework/engines/users/UserEngine';

  import { Button, Icon } from 'framework7-svelte';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import ProfileUI, { ProfileUIDrawer } from '@framework/ui/layout/ProfileUI/ProfileUI.svelte';
  import BoxedUI, { BoxedUISizes } from '@framework/ui/surface/BoxedUI/BoxedUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import ClampsButtonUI from '@framework/ui/button/ClampsButtonUI/ClampsButtonUI.svelte';
  import NoticeUI from '@framework/ui/layout/NoticeUI/NoticeUI.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import UserTags from '@framework/components/tags/UserTags.svelte';
  import GenericPersonTable from '@framework/components/table/GenericPersonTable.svelte';
  import FirebaseUserTags from '@framework/components/tags/FirebaseUserTags.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  export let quicklook = false;

  let createMenu: Popover.Popover;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  let { user, request } = UserEngine({
    uid: selectable.id,
  });

  $: $ACCOUNT.database.layout.pinned['User'][$user?.uid] ??= [];
</script>

<PageUI on:pageAfterIn={() => {
  if (!$WIZARD_ACTIVE && !quicklook) $UsersSelected = [selectable];
}}>
  <svelte:fragment slot="fixed">
    <AppBarUI
      navigation={{ close: quicklook, history: !quicklook }}
      {f7router}
    >
      <svelte:fragment slot="right">
        {#if !$WIZARD_ACTIVE && !quicklook && $user?.isRegistered}
          <MenuButtonUI
            title={language().buttons.edit.text}
            icon={{ f7: 'pencil_outline', color: 'gray' }}
          />
          <MenuButtonUI
            title={language().buttons.create.text}
            icon={{ f7: 'plus_circle_fill', color: 'blue' }}
            on:click={(event) => openPopover(createMenu, event)}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>
 
  <RequestLayout {...$request} refetch={request.refetch} large middle>
    {#if $user?.isRegistered}
      <ProfileUI drawer={{ ...ProfileUIDrawer }}>
        <h2 slot="title">
          {$user?.names.firstName}
          {#if $user.names?.middleName}
            <ClampsButtonUI text={$user.names?.middleName} />
          {/if}
          {$user.names?.lastName}
        </h2>
        <BoxedUI slot="media" constraints={BoxedUISizes.Large} color="blue" round fill>
          <span>{`${$user?.names.firstName.charAt(0)}${$user.names?.lastName.charAt(0)}`}</span>
        </BoxedUI>
        <UserTags slot="tags" {...$user} />
        <GenericPersonTable
          emails={[{
            type: EmailType.Other,
            email: $user.firebaseUserInfo.email ?? '',
          }]}
        />
      </ProfileUI>
    {:else}
      <ProfileUI drawer={{ ...ProfileUIDrawer }}>
        <h2 slot="title">{$user?.firebaseUserInfo.email}</h2>
        <FirebaseUserTags slot="tags" {...$user} />
      </ProfileUI>
    {/if}

    {#if !$user.isRegistered}
      <NoticeUI
        title="Pending Registeration"
        text={`
          ${$user.firebaseUserInfo.email} is not registered in this clinic.
          You can approve their request and permit them to log in to the clinic by creating a
          new user account using their email.
        `}
        icon={{ f7: 'person_crop_circle_badge_exclam', color: 'orange' }}
        large
        middle
      >
        <Button
          color="blue"
          fill
          round
          on:click={() => openWizard('/wizard/user/', {
            reference: {
              type: 'FirebaseInfo',
              id: $user?.uid,
            },
            email: $user.firebaseUserInfo?.email,
          })}
        >
          <Icon f7="plus_circle_fill" />
          Add to clinic
        </Button>
        <span class="flex-grow" />
        <Button
          color="red"
          fill
          round
        >
          Deny Registeration
        </Button>
      </NoticeUI>
    {/if}
  </RequestLayout>
  <br />
  <br />
</PageUI>
