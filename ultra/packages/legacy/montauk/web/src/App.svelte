<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';

  import language from '@framework/languages/index';

  import firebase from 'firebase/app';
  import 'firebase/auth';

  import {
    App,
    View,
    LoginScreen,
    Views,
    Block,
    List,
    ListItem,
    Icon,
    Popup,
  } from 'framework7-svelte';

  import { ACCOUNT, SIGN_IN } from '@framework/store/index';
  import {
    AppViews,
    AppViewRoutes,
    APP_ACTIVE_TAB,
    AppPopups,
  } from '@/store/AppStore';

  import { getFirebaseUserDatabaseAndSignIn } from '@framework/modules/UserModule';

  import DragBarUI from '@framework/ui/controller/DragBarUI/DragBarUI.svelte';
  import SideBarUI from '@framework/ui/controller/SideBarUI/SideBarUI.svelte';
  import BoxedUI from '@framework/ui/surface/BoxedUI/BoxedUI.svelte';
  import { realtime } from '@framework/server';
  import { openPopup } from '@delta/library/src/modules/NavigationModule';

  export let f7params: Framework7Parameters;

  firebase.auth().onAuthStateChanged((user) => {
    if (user) {
      getFirebaseUserDatabaseAndSignIn(user);
    } else {
      $SIGN_IN.signedIn = false;
    }
  });

  const view = {
    tab: true,
    stackPages: true,
    iosSwipeBack: false,
    animate: false,
  };

  // Everytime user account information changes, update the firebase db
  $: if ($ACCOUNT)
    void realtime.ref(`users/${$ACCOUNT.uid}`).set($ACCOUNT.database);
</script>

<App {...{
  id: f7params.id,
  theme: f7params.theme,
  routes: f7params.routes,
  autoDarkTheme: f7params.autoDarkTheme,
}}>
  <DragBarUI />
  <LoginScreen opened={!$ACCOUNT?.isRegistered}>
    <View url="/login/" />
  </LoginScreen>

  {#if $ACCOUNT?.isRegistered}
    <Popup id={AppPopups.Default} closeByBackdropClick={false}>
      <View id={AppViews.Popup} iosSwipeBack={false} />
    </Popup>
    <Views tabs>
      <View
        id={AppViews.Forms}
        class={`${$APP_ACTIVE_TAB === AppViews.Forms ? 'tab-active' : ''}`}
        url={AppViewRoutes.Forms}
        {...view}
      />
      <View
        id={AppViews.Wizard}
        class={`${$APP_ACTIVE_TAB === AppViews.Wizard ? 'tab-active' : ''}`}
        {...view}
      />
    </Views>

    <SideBarUI>
      <Block>
        <h6>{language().app.sidebar.sharingHeader.text}</h6>
        <List noChevron noHairlines noHairlinesBetween>
          <ListItem
            class="color-deeppurple"
            tabLink
            link
            tabLinkActive={$APP_ACTIVE_TAB === AppViews.Forms}
            title={language().app.sidebar.forms.text}
            on:click={() => ($APP_ACTIVE_TAB = AppViews.Forms)}
          >
            <Icon slot="media" f7="doc_on_doc_fill" />
          </ListItem>
        </List>
      </Block>
      <div slot="footer">
        <List mediaList noChevron noHairlines>
          <ListItem
            title={`${$ACCOUNT.names.firstName} ${$ACCOUNT.names.lastName}`}
            text={$ACCOUNT.group?.name}
            link
            on:click={() => openPopup('/settings/')}
          >
            <BoxedUI slot="media" color="blue" fill round>
              {`${$ACCOUNT.names.firstName} ${$ACCOUNT.names.lastName}`
                .match(/\b(\w)/g)
                ?.join('')}
            </BoxedUI>
          </ListItem>
        </List>
      </div>
    </SideBarUI>
  {/if}
</App>
