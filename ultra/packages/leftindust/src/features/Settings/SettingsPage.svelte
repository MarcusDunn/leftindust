<script lang="ts">
  import { _ } from '@/language';
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
    Block,
    Icon,
    List,
    ListItem,
  } from 'framework7-svelte';
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import { selectedSettingsTab } from './store';
  
  import { SettingsTabs } from '.';

  import AboutTab from './components/AboutTab/AboutTab.svelte';
  import AccountTab from './components/AccountTab/AccountTab.svelte';

  import Page from '@/features/UI/components/Page/Page.svelte';
  import Boxed from '../UI/components/Boxed/Boxed.svelte';
  import { BoxedSizes } from '../UI/components/Boxed';

  import { account } from '@/features/Account/store';
  import UserTags from '../User/components/UserTags/UserTags.svelte';
  import { signOut } from '../Account';
</script>

<Page>
  <AppBar slot="fixed" close={{ popup: true }} />
  <Block>
    <div class="display-flex" style="margin: 0 20px; align-items: center">
      <Boxed
        dimensions={BoxedSizes.Large}
        color="blue"
        fill
        round
      >
        {`${$account.name?.firstName} ${$account.name?.lastName}`.match(/\b(\w)/g)?.join('')}
      </Boxed>
      <div style="margin-left: 20px">
        <h2>{`${$account.name?.firstName} ${$account.name?.lastName}`}</h2>
        <UserTags {...$account} />
      </div>
      <div style="flex-grow: 1" />
      <Button
        fill
        round
        color="red"
        style="margin-left: 20px"
        on:click={() => signOut()}
      >
        <Icon f7="square_arrow_right_fill" />
        {$_('generics.signOut')}
      </Button>
    </div>
  </Block>
  <br />
  <br />
  <div class="display-flex">
    <div style="width: 400px;flex-shrink: 0;">
      <div style="margin: 0 30px">
        <List class="no-margin" mediaList inset>
          <ListItem
            title="Account"
            text="Manage your account and related user data"
            link
            on:click={() => $selectedSettingsTab = SettingsTabs.Account}
          >
            <Icon slot="media" f7="person_2" color="blue" style="font-size: 24px" />
          </ListItem>
          <ListItem
            title="Notifications"
            text="Manage the types of notifications you recieve"
            link
            on:click={() => $selectedSettingsTab = SettingsTabs.Notifications}
          >
            <Icon slot="media" f7="bell" color="red" style="font-size: 24px" />
          </ListItem>
          <ListItem
            title="Theme"
            text="Modify the apperance and look of your system"
            link
            on:click={() => $selectedSettingsTab = SettingsTabs.Theme}
          >
            <Icon slot="media" f7="paintbrush" color="teal" style="font-size: 24px" />
          </ListItem>
        </List>
        <br />
        <List class="no-margin" mediaList inset>
          <ListItem
            title="About"
            text="Learn more about leftindust and our services"
            link
            on:click={() => $selectedSettingsTab = SettingsTabs.About}
          >
            <Icon slot="media" f7="info_circle" color="primary" style="font-size: 24px" />
          </ListItem>
        </List>
      </div>
    </div>
    <div style="width: 100%">
      <Tabs>
        <Tab tabActive={$selectedSettingsTab === SettingsTabs.Account}>
          <AccountTab />
        </Tab>
        <Tab tabActive={$selectedSettingsTab === SettingsTabs.Notifications}>
        </Tab>
        <Tab tabActive={$selectedSettingsTab === SettingsTabs.Theme}>
        </Tab>
        <Tab tabActive={$selectedSettingsTab === SettingsTabs.About}>
          <AboutTab />
        </Tab>
      </Tabs>
    </div>
  </div>
</Page>
