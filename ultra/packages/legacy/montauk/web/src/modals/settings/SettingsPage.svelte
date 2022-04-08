<script lang="ts">
  import language from '@framework/languages/index';
  import {
    Page,
    Segmented,
    Button,
    Tabs,
    Tab,
} from 'framework7-svelte';
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';

  import AccountTab from '@/modals/settings/tabs/account/AccountTab.svelte';

  import { SettingsTabs, SelectedSettingsTab } from '@/modals/settings/store/SettingsStore';

</script>

<Page>
  <AppBarUI navigation={{ close: true }}>
    <div class="title">
      <Segmented strong>
        <Button
          active={$SelectedSettingsTab === SettingsTabs.Account}
          on:click={() => ($SelectedSettingsTab = SettingsTabs.Account)}
        >
          {language().user.settings.account.title.text}
        </Button>
        <Button
          active={$SelectedSettingsTab === SettingsTabs.Options}
          on:click={() => ($SelectedSettingsTab = SettingsTabs.Options)}
        >
          {language().user.settings.options.title.text}
        </Button>
        <Button
          active={$SelectedSettingsTab === SettingsTabs.Theme}
          on:click={() => ($SelectedSettingsTab = SettingsTabs.Theme)}
        >
          {language().user.settings.theme.title.text}
        </Button>
      </Segmented>
    </div>
  </AppBarUI>
  <Tabs>
    <Tab tabActive={$SelectedSettingsTab === SettingsTabs.Account}>
      <AccountTab />
    </Tab>
  </Tabs>
</Page>
