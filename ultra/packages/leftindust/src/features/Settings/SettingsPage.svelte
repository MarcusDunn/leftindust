<script lang="ts">
  import { _ } from '@/language';
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
  } from 'framework7-svelte';
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import { selectedSettingsTab } from './store';
  
  import { SettingsTabs } from '.';

  import AboutTab from './components/AboutTab/AboutTab.svelte';

  import Page from '@/features/UI/components/Page/Page.svelte';
import AccountTab from './components/AccountTab/AccountTab.svelte';
</script>

<Page>
  <AppBar slot="fixed" close={{ popup: true }}>
    <div class="title">
      <div>
        <Segmented strong>
          <Button
            active={$selectedSettingsTab === SettingsTabs.Account}
            on:click={() => ($selectedSettingsTab = SettingsTabs.Account)}
          >
            {$_('generics.account')}
          </Button>
          <Button
            active={$selectedSettingsTab === SettingsTabs.About}
            on:click={() => ($selectedSettingsTab = SettingsTabs.About)}
          >
            {$_('generics.about')}
          </Button>
        </Segmented>
      </div>
    </div>
  </AppBar>
  <Tabs>
    <Tab tabActive={$selectedSettingsTab === SettingsTabs.Account}>
      <AccountTab />
    </Tab>
    <Tab tabActive={$selectedSettingsTab === SettingsTabs.About}>
      <AboutTab />
    </Tab>
  </Tabs>
</Page>
