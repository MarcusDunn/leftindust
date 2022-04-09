<script lang="ts">
  import type { AppLayoutItem, AppLayoutSidebarTitle } from '.';
  import type { Framework7Parameters } from 'framework7/types';

  import { AppPopups, AppViews, AppRootRoutes } from '../../';
  import { getFirebaseUserDatabaseAndSignIn } from '@/features/Account';
  import { realtime } from '@/api/server';
  import { account, signInStatus } from '@/features/Account/store';
  
  import firebase from 'firebase';
  import { masterDetailBreakpoint } from '@/features/View';
  import {
    App,
    f7ready,
    LoginScreen,
    Popup,
    View,
    Views,
  } from 'framework7-svelte';
  import getNativeAPI from '@/api/bridge';
  import AppLauncherButton from '@/features/Apps/components/AppLauncher/AppLauncherButton.svelte';
  
  import Sidebar from '@/features/UI/components/Sidebar/Sidebar.svelte';
  import { observeWindowErrors } from '@/features/Errors';
  import { observeHistory } from '@/features/History';
  import Dragbar from '@/features/UI/components/Dragbar/Dragbar.svelte';

  const { System } = getNativeAPI();
  
  export let f7params: Framework7Parameters;

  export let items: (AppLayoutItem | AppLayoutSidebarTitle)[];
  export let sidebar = true;

  const { id, theme, routes, autoDarkTheme } = f7params;

  firebase.auth().onAuthStateChanged((user) => {
    if (user) {
      getFirebaseUserDatabaseAndSignIn(user);
    } else {
      $signInStatus.signedIn = false;
    }
  });

  // Everytime user account information changes, update the firebase db
  $: if ($account) 
    void realtime.ref(`users/${$account.uid}`).set($account.database);
    
  f7ready(() => {
    observeWindowErrors();
    observeHistory();
  });


  let width = window.innerWidth;
</script>

<svelte:window bind:innerWidth={width} />

<App {...{ id, theme, routes, autoDarkTheme }}>
  <Dragbar />
  <LoginScreen opened={!$account?.isRegistered}>
    <View url="/account/login/" />
  </LoginScreen>
  
  <Popup id={AppPopups.Error} closeByBackdropClick={false}>
    <View url={AppRootRoutes.LifeCycleError} iosSwipeBack={false} />
  </Popup>

  {#if $account?.isRegistered}
    <Popup id={AppPopups.Default} closeByBackdropClick={false}>
      <View id={AppViews.Popup} iosSwipeBack={false} />
    </Popup>

    <Popup
      id={AppPopups.Wizard}
      closeByBackdropClick={false}
      tabletFullscreen
      push
    >
      <View id={AppViews.Wizard} iosSwipeBack={false} />
    </Popup>

    <Views tabs>
      {#each items as item}
        {#if 'active' in item}
          <View
            id={item.id}
            name={item.id}
            url={item.url}
            class={`${item.active ? 'tab-active' : ''} ${item.master && width > 1170 ? 'view-master-detail' : ''}`}
            iosSwipeBack={false}
            animate={false}
            masterDetailBreakpoint={item.master ? masterDetailBreakpoint : undefined}
            tab
            stackPages
          />
        {/if}
      {/each}
    </Views>

    {#if sidebar}
      <Sidebar {items}>
        <svelte:fragment slot="root">
          {#if System.platform() === 'web'}
            <AppLauncherButton />
          {/if}
        </svelte:fragment>
        <slot />
        <svelte:fragment slot="footer">
          <slot name="footer" />
        </svelte:fragment>
      </Sidebar>
    {/if}
  {/if}
</App>