<script lang="ts">
  import type {  Popover as PopoverType } from 'framework7/types';
  import type { Selectable } from '../../modules/SelectModule';
  
  import { onMount } from 'svelte';
  import { PluginTypes } from '../../plugins';
  import language from '../../languages';
  import { email } from '../../modules/ContactModule';
  
  import {
    Block,
    BlockFooter,
    Popover,
    View,
  } from 'framework7-svelte';
  
  import SpecificPluginGrid from '../grid/SpecificPluginGrid.svelte';
  import PageUI from '../../ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '../../ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  
  export let selectables: Selectable[];
  export let emails: string[];
  export let instance: PopoverType.Popover | undefined = undefined;
  
  let ref: Popover;

  onMount(() => {
    instance = ref.instance();
  });

</script>

<style lang="scss">
  $popoverWidth: 625px;
  $popoverHeight: 550px;

  :global(.ultra-component-missing-emails-modal) {
    width: $popoverWidth;
    height: $popoverHeight;

    & :global(.view) {
      height: $popoverHeight;
    }
  }
</style>

<Popover class="ultra-component-missing-emails-modal" bind:this={ref}>
  <View
    stackPages
    iosSwipeBack={false}
    animate={false}
  >
    <PageUI>
      <AppBarUI navigation={{ close: true }} slot="fixed">
        <svelte:fragment slot="right">
          <MenuButtonUI
            title={language().buttons.ok.text}
            icon={{ f7: 'checkmark_circle_fill', color: 'blue' }}
            on:click={() => email(emails)}
          />
        </svelte:fragment>
      </AppBarUI>
      <Block>
        <h3 style="font-size: 22px" class="no-margin-bottom">Unavailable Emails</h3>
        <BlockFooter>
          These clients do not have available email accounts on file, and will not be emailed
        </BlockFooter>
        <p />
        <SpecificPluginGrid
          props={selectables.map((selectable) => {
            if (selectable.type) {
              return {
                id: selectable.type,
                selectable: {
                  id: selectable.id,
                  type: selectable.type,
                },
                quicklook: true,
              };
            }
          })}
          type={PluginTypes.Widget}
          cols={[[Math.pow(10, 1000), 2], [550, 1]]}
          fixed
        />
      </Block>
    </PageUI>
  </View>
</Popover>