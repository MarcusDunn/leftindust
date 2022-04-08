<script lang="ts">
  import {
    Panel,
    View,
    Page,
    PageContent,
    f7,
  } from 'framework7-svelte';
  
  import { WIZARD_ACTIVE } from '../../../store';

  import { getNativeAPI } from '../../../bridge';
  import SidebarToggleUI from './SideBarToggleUI.svelte';

  let width = window.innerWidth;
  let opened = false;

  $: mobile = width < 780;
  $: opened = !mobile || !$WIZARD_ACTIVE;
  
  $: (() => {
    if (!$WIZARD_ACTIVE) {
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

<SidebarToggleUI />

{#key mobile}
  <Panel
    class={`ultra-sidebar ${System.platform() !== 'darwin' ? 'ultra-no-vibrancy' : ''}`}
    backdrop={mobile}
    left
    opened={(() => {
      if (!mobile) return opened;
      return false;
    })()}
    reveal={!mobile}
    cover={mobile}
  >
    <View>
      <Page pageContent={false}>
        <PageContent>
          <div class="ultra-sidebar-content">
            <slot />
          </div>
          <div class="ultra-sidebar-footer">
            <slot name="footer" />
          </div>
        </PageContent>
      </Page>
    </View>
  </Panel>
{/key}