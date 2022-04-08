<script lang="ts">
  import type { Router, Popover } from 'framework7/types';
  import language from '@framework/languages/index';

  import {
    Tabs,
    Tab,
    Segmented,
    Button,
    Icon,
    Searchbar,
  } from 'framework7-svelte';

  import { WIZARD_ACTIVE } from '@framework/store';

  import { UsersSelectedTab, UsersTab, UsersSearchQuery } from './store/UsersStore';
    
  import { openPopover, openWizard } from '@framework/modules/NavigationModule';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';
  import PendingTab from './tabs/pending/PendingTab.svelte';
  import RegisteredTab from './tabs/registered/RegisteredTab.svelte';


  export let f7router: Router.Router;
  export let f7route: Router.Route;
    
  let createMenu: Popover.Popover;

</script>

<PageUI style="overflow: hidden">
	<svelte:fragment slot="fixed">
		<MenuUI
      items={[
        {
          title: language().user.title.text,
          text: language().user.placeholders.addUserDescription.text,
          icon: {
            f7: 'person_fill',
            color: 'blue',
          },
					onClick: () => openWizard('/wizard/user/'),
        },
      ]}
      bind:instance={createMenu}
    />
		<AppBarUI>
			<Searchbar
				class="color-blue"
				customSearch
				inline
				disableButton={false}
				placeholder={($UsersSelectedTab === UsersTab.Pending && language().user.pending.search.text
					|| $UsersSelectedTab === UsersTab.Registered && language().user.registered.search.text) || ''}
				bind:value={$UsersSearchQuery}
			/>
			<svelte:fragment slot="right">
				{#if !$WIZARD_ACTIVE}
					<MenuButtonUI
						title={language().buttons.create.text}
						icon={{ f7: 'plus_circle_fill', color: 'blue' }}
						on:click={(event) => { openPopover(createMenu, event); }}
					/>
				{/if}
			</svelte:fragment>
			<svelte:fragment slot="subnavbar">
				<Segmented strong tag="p">
					<Button
						active={$UsersSelectedTab === UsersTab.Pending}
						on:click={() => $UsersSelectedTab = UsersTab.Pending}
					>
						<Icon f7="person_crop_circle_badge_exclam" color="orange"/>
						{language().user.pending.title.text}
					</Button>
					<Button
						active={$UsersSelectedTab === UsersTab.Registered}
						on:click={() => $UsersSelectedTab = UsersTab.Registered}
					>
						<Icon f7="person_crop_circle_badge_checkmark" color="green"/>
						{language().user.registered.title.text}
					</Button>
				</Segmented>
			</svelte:fragment>
		</AppBarUI>
	</svelte:fragment>
	<Tabs style="height: 100%">
		<Tab tabActive={$UsersSelectedTab === UsersTab.Pending}>
			<PendingTab {f7route} {f7router} />
		</Tab>
		<Tab tabActive={$UsersSelectedTab === UsersTab.Registered}>
			<RegisteredTab {f7route} {f7router} />
		</Tab>
	</Tabs>
</PageUI>
