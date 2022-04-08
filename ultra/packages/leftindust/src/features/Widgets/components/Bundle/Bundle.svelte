<script lang="ts">
  import type { Framework7Icon } from '@/features/UI';
  import { account } from '@/features/Account/store';

  import './Bundle.scss';

  import { Block, Icon } from 'framework7-svelte';

  export let title: string;
  export let icon: Framework7Icon | undefined = undefined;
  export let color: string;

  export let dragger: (() => void) | undefined = undefined;
  export let shadow = false;

</script>

{#key $account}
  <Block
    class={
      `widget-bundle
      ${shadow ? 'elevation-4' : ''}
  `}
    strong
    inset
  >
    <div class="widget-bundle-inner">
      <div
        class={`widget-bundle-dragger ${dragger ? 'widget-bundle-dragger-enabled' : ''}`}
        on:pointerdown={dragger}
      >
        <h4 class={`text-color-${color}`}>
          {#if icon}
            <Icon {...icon} />
          {/if}
          {title}
        </h4>
      </div>
      <div class="widget-bundle-content">
        <slot />
      </div>
      <div class="widget-bundle-footer">
        <slot name="controls" />
      </div>
    </div>
  </Block>
{/key}