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
    f7,
    f7ready,
  } from 'framework7-svelte';

  import { ACCOUNT, SIGN_IN } from '@framework/store/index';
  import { realtime } from '@framework/server';
  import { openPopupUrl } from '@framework/modules/NavigationModule';
  import {
    AppViews,
    AppViewRoutes,
    APP_ACTIVE_TAB,
    AppPopups,
    MASTER_DETAIL_BREAKPOINT,
  } from '@framework/modules/AppModule';
  
  import { observeHistory } from '@framework/modules/HistoryModule';
  
  import { getFirebaseUserDatabaseAndSignIn } from '@framework/modules/UserModule';
  
  import UserItem from '@framework/components/item/user/UserItem.svelte';

  import DragBarUI from '@framework/ui/controller/DragBarUI/DragBarUI.svelte';
  import SideBarUI from '@framework/ui/controller/SideBarUI/SideBarUI.svelte';

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

  $: f7ready(() => f7.tab.show(`#${$APP_ACTIVE_TAB}`));

  // Everytime user account information changes, update the firebase db
  $: if ($ACCOUNT) 
    void realtime.ref(`users/${$ACCOUNT.uid}`).set($ACCOUNT.database);

  $: if ($ACCOUNT?.isRegistered && !$ACCOUNT?.database.settings.setup)
    f7ready(() => setTimeout(() => openPopupUrl('/setup/'), 100));

  $: observeHistory();
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

    <Popup
      id={AppPopups.Wizard}
      closeByBackdropClick={false}
      tabletFullscreen
      push
    >
      <View id={AppViews.Wizard} iosSwipeBack={false} />
    </Popup>
    
    <Views tabs>
      <View
        id={AppViews.Dashboard}
        name={AppViews.Dashboard}
        url={AppViewRoutes.Dashboard}
        class="tab-active"
        {...view}
      />
      <View
        id={AppViews.Calendar}
        name={AppViews.Calendar}
        url={AppViewRoutes.Calendar}
        {...view}
      />
      <View
        id={AppViews.Clients}
        name={AppViews.Clients}
        class="view-master-detail"
        url={AppViewRoutes.Clients}
        {...view}
        masterDetailBreakpoint={MASTER_DETAIL_BREAKPOINT}
      />
      <View
        id={AppViews.Users}
        name={AppViews.Users}
        url={AppViewRoutes.Users}
        class="view-master-detail"
        masterDetailBreakpoint={MASTER_DETAIL_BREAKPOINT}
        {...view}
      />
      <View
        id={AppViews.Forms}
        name={AppViews.Forms}
        url={AppViewRoutes.Forms}
        {...view}
      />
      <View
        id={AppViews.Wizard}
        name={AppViews.Wizard}
        class={`${$APP_ACTIVE_TAB === AppViews.Wizard ? 'tab-active' : ''}`}
        {...view}
      />
    </Views>

    <SideBarUI>
      <Block>
        <h6>{language().app.sidebar.clinicHeader.text}</h6>
        <List noChevron noHairlines noHairlinesBetween>
          <ListItem
            class="color-purple"
            tabLink
            link
            tabLinkActive={$APP_ACTIVE_TAB === AppViews.Dashboard}
            title={language().app.sidebar.dashboard.text}
            on:click={() => ($APP_ACTIVE_TAB = AppViews.Dashboard)}
          >
            <Icon slot="media" f7="rectangle_3_offgrid_fill" />
          </ListItem>
          <ListItem
            class="color-purple"
            tabLink
            link
            tabLinkActive={$APP_ACTIVE_TAB === AppViews.Calendar}
            title={language().app.sidebar.calendar.text}
            on:click={() => ($APP_ACTIVE_TAB = AppViews.Calendar)}
          >
            <Icon slot="media" f7="calendar" />
          </ListItem>
          <ListItem
            class="color-purple"
            tabLink
            link
            tabLinkActive={$APP_ACTIVE_TAB === AppViews.Clients}
            title={language().app.sidebar.clients.text}
            on:click={() => ($APP_ACTIVE_TAB = AppViews.Clients)}
          >
            <Icon slot="media" f7="person_2_fill" />
          </ListItem>
        </List>
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
        <h6>{language().app.sidebar.administrationHeader.text}</h6>
        <List noChevron noHairlines noHairlinesBetween>
          <ListItem
            class="color-blue"
            tabLink
            link
            tabLinkActive={$APP_ACTIVE_TAB === AppViews.Users}
            title={language().app.sidebar.users.text}
            on:click={() => ($APP_ACTIVE_TAB = AppViews.Users)}
          >
            <Icon slot="media" f7="person_2_square_stack_fill" />
          </ListItem>
        </List>
      </Block>
      <div slot="footer">
        <List mediaList noChevron noHairlines>
          <UserItem
            user={$ACCOUNT}
            on:click={() => openPopupUrl('/settings/')}
          />
        </List>
      </div>
    </SideBarUI>
  {/if}
</App>
