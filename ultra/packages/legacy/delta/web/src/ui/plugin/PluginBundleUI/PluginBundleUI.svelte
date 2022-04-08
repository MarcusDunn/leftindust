<script lang="ts">
  import type { Framework7Icon } from '../../../modules/UIModule';

  import { Block, Icon } from 'framework7-svelte';
  import IconButtonUI from '../../controller/IconButtonUI/IconButtonUI.svelte';

  export let title: string;
  export let icon: Framework7Icon | undefined = undefined;
  export let color: string;

  export let dragger: (() => void) | undefined = undefined;
  export let shadow = false;

  let optionsOpen = false;

</script>

<Block
  class={
    `ultra-plugin-bundle
    ${optionsOpen ? 'ultra-plugin-bundle-options-open' : ''}
    ${shadow ? 'elevation-4' : ''}
 `}
  strong
  inset
>
  <div class="ultra-plugin-bundle-inner">
    <div
      class={`ultra-plugin-bundle-dragger ${dragger ? 'ultra-plugin-bundle-dragger-enabled' : ''}`}
      on:pointerdown={dragger}
    >
      <h4 class={`text-color-${color}`}>
        {#if icon}
          <Icon {...icon} />
        {/if}
        {#if icon ?? false}
          {title}
        {/if}
      </h4>
    </div>
    <div class="ultra-plugin-bundle-content">
      <slot />
    </div>
    <div class="ultra-plugin-bundle-options">
      <slot name="options" />
    </div>
    <div class="ultra-plugin-bundle-footer">
      <slot name="controls" />
    </div>
  </div>
</Block>
