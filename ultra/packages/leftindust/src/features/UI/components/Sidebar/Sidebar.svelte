<script lang="ts">
  import type { SidebarItem, SidebarTitle } from './index';
  import {
    Panel,
    View,
    Page,
    PageContent,
    f7,
    List,
    ListItem,
    Icon,
  } from 'framework7-svelte';

  import './Sidebar.scss';
  
  import { wizardOpen } from '../../../Wizard/store';
  import { getNativeAPI } from '@/api/bridge';
  import SidebarToggle from './SideBarToggle.svelte';

  export let items: (SidebarItem | SidebarTitle)[] = [];

  let width = window.innerWidth;
  let opened = false;

  $: mobile = width < 780;
  $: opened = !mobile || !$wizardOpen;
  
  let ref: Panel;
  
  $: (() => {
    if (!$wizardOpen) {
      f7.$('html').removeClass('with-panel');
      if (mobile) {
        f7.$('html').removeClass('with-panel-left-reveal');
      } else {
        f7.$('html').removeClass('with-panel-left-cover');
      }
    }
  })();

  const { System } = getNativeAPI();

</script>

<svelte:window bind:innerWidth={width} />

<SidebarToggle />

{#key mobile}
  <Panel
    class={`ui-sidebar ${System.platform() !== 'darwin' ? 'no-vibrancy' : ''}`}
    backdrop={mobile}
    left
    opened={(() => {
      if (!mobile) return opened;
      return false;
    })()}
    reveal={!mobile}
    cover={mobile}
    bind:this={ref}
  >
    <View>
      <Page pageContent={false}>
        <PageContent>
          <slot name="root" />
          <div class="ui-sidebar-content">
            {#each items as item}
              {#if 'icon' in item}
                <List noChevron noHairlines noHairlinesBetween>
                  <ListItem
                    class={`color-${item.color}`}
                    tabLink
                    link
                    tabLinkActive={item.active}
                    title={item.title}
                    on:click={() => item.onClick && item.onClick()}
                  >
                    <Icon slot="media" {...item.icon} />
                  </ListItem>
                </List>
              {:else}
                <h6>{item.title}</h6>
              {/if}
            {/each}
            <slot />
          </div>
          <div class="ui-sidebar-footer">
            <slot name="footer" />
          </div>
        </PageContent>
      </Page>
    </View>
  </Panel>
{/key}