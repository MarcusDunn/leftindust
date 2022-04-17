<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';

  import {
    App,
    View,
    LoginScreen,
    Popup,
    f7ready,
  } from 'framework7-svelte';

  import { account, signInStatus } from '@/features/Account/store';
  import { auth, realtime } from '@/api/server';

  import { onAuthStateChanged } from 'firebase/auth';
  import { AppViews, AppPopups, AppRootRoutes } from '@/features/App';

  import { getFirebaseUserDatabaseAndSignIn } from '@/features/Account';
  import { observeWindowErrors } from '@/features/Errors';

  import { observeHistory } from '@/features/History';
  import Dragbar from '@/features/UI/components/Dragbar/Dragbar.svelte';

  import routes from './routes';
  import { ref, set } from 'firebase/database';

  export let f7params: Framework7Parameters;
  const { id, theme, autoDarkMode } = f7params;

  onAuthStateChanged(auth, (user) => {
    if (user) {
      getFirebaseUserDatabaseAndSignIn(user);
    } else {
      $signInStatus.signedIn = false;
    }
  });

  // Everytime user account information changes, update the firebase db
  $: if ($account) void set(ref(realtime, `users/${$account.uid}`), $account.database);

  f7ready(() => {
    observeWindowErrors();
    observeHistory();
  });

</script>

<App {...{ id, theme, routes, autoDarkMode }}>
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
