<script lang="ts">
  import { Block, BlockFooter, Preloader } from 'framework7-svelte';

  import IconButtonUI from '../../controller/IconButtonUI/IconButtonUI.svelte';

  import { createEventDispatcher } from 'svelte';

  const dispatch = createEventDispatcher();

  export let title = '';
  export let subtitle = '';
  export let header = '';
  export let description = '';

  export let loading = false;

  export let color: string;

  export let dragger: (() => void) | undefined = undefined;
  export let shadow = false;

  export let menu = false;

</script>

<Block
  class={
    `ultra-plugin-widget
    ${shadow ? 'elevation-4' : ''}
 `}
  strong
  inset
> 
  {#if loading}
    <div class="ultra-plugin-widget-loading">
      <Preloader />
    </div>
  {:else}
    <div class="ultra-plugin-widget-inner">
      <BlockFooter class={`ultra-plugin-widget-header color-theme-${color}`}>
        <div
          class={`ultra-plugin-widget-dragger ${dragger ? 'ultra-plugin-widget-dragger-enabled' : ''}`}
          on:pointerdown={dragger}
        >
          {header}
        </div>
      </BlockFooter>
      <div class="ultra-plugin-widget-title">
        {#if $$slots['before-title']}
          <div class="ultra-plugin-widget-title-before">
            <slot name="before-title" />
          </div>
        {/if}
        <div>
          <h4 class="one-line-word-clamp">
            {#if !$$slots.title}
              {title}
            {:else}
              <slot name="title" />
            {/if}
          </h4>
          
          {#if !$$slots.subtitle}
            {#if subtitle}
              <div class="ultra-plugin-widget-subtitle">{subtitle}</div>
            {/if}
          {:else}
            <div class="ultra-plugin-widget-subtitle"><slot name="subtitle" /></div>
          {/if}
        </div>
      </div>

      <BlockFooter class="ultra-plugin-widget-description">
        {#if !$$slots.description}
          {description}
        {:else}
          <slot name="description" />
        {/if}
      </BlockFooter>
      <slot name="after-title" />
    </div>
    <div class={`ultra-plugin-widget-content color-theme-${color}`}>
      <slot />
    </div>
    <div class={`ultra-plugin-widget-footer color-theme-${color}`}>
      <slot name="controls" />
      <span class="flex-grow" />
      {#if menu}
        <IconButtonUI
          selectable={false}
          icon={{
            f7: 'ellipsis_circle_fill',
            color,
          }}
          on:click={({ detail }) => dispatch('menu', detail)}
        />
      {/if}
    </div>
  {/if}
</Block>
