<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';

  import firebase from 'firebase/app';
  import 'firebase/auth';

  import {
    App,
    View,
    LoginScreen,
    Popup,
    f7ready,
  } from 'framework7-svelte';

  import { account, signInStatus } from '@/features/Account/store';
  import { realtime } from '@/api/server';

  import { AppViews, AppPopups, AppRootRoutes } from '@/features/App';

  import { getFirebaseUserDatabaseAndSignIn } from '@/features/Account';
  import { observeWindowErrors } from '@/features/Errors';

  import { observeHistory } from '@/features/History';
  import Dragbar from '@/features/UI/components/Dragbar/Dragbar.svelte';
  import AppLayout from '@/features/App/components/AppLayout/AppLayout.svelte';

  import routes from './routes';

  export let f7params: Framework7Parameters;
  const { id, theme, autoDarkTheme } = f7params;

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

</script>

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
  {/if}
</App>
