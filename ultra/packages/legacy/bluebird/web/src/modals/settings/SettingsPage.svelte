<script lang="ts">
  import language from '@framework/languages/index';
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
  } from 'framework7-svelte';
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';

  import AccountTab from '@/modals/settings/tabs/account/AccountTab.svelte';
  import AboutTab from '@/modals/settings/tabs/about/AboutTab.svelte';
  
  import { SettingsTabs, SelectedSettingsTab } from '@/modals/settings/store';

  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
</script>

<PageUI>
  <AppBarUI slot="fixed" navigation={{ close: true }}>
    <div class="title">
      <div>
        <Segmented strong>
          <Button
            active={$SelectedSettingsTab === SettingsTabs.Account}
            on:click={() => ($SelectedSettingsTab = SettingsTabs.Account)}
          >
            {language().user.settings.account.title.text}
          </Button>
          <Button
            active={$SelectedSettingsTab === SettingsTabs.About}
            on:click={() => ($SelectedSettingsTab = SettingsTabs.About)}
          >
            {language().user.settings.about.title.text}
          </Button>
        </Segmented>
      </div>
    </div>
  </AppBarUI>
  <Tabs>
    <Tab tabActive={$SelectedSettingsTab === SettingsTabs.Account}>
      <AccountTab />
    </Tab>
    <Tab tabActive={$SelectedSettingsTab === SettingsTabs.About}>
      <AboutTab />
    </Tab>
  </Tabs>
</PageUI>
