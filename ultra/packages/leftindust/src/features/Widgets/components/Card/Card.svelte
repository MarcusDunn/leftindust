<script lang="ts">
  import { Block, BlockFooter, Preloader } from 'framework7-svelte';
  import type { Color } from '@/features/UI';
  import { scale } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';

  import { createEventDispatcher } from 'svelte';

  import IconButton from '@/features/UI/components/IconButton/IconButton.svelte';

  const dispatch = createEventDispatcher();

  import './Card.scss';

  export let title = '';
  export let subtitle = '';
  export let header = '';
  export let description = '';

  export let loading = false;

  export let color: Color = 'primary';

  export let dragger: (() => void) | undefined = undefined;
  export let shadow = false;

  export let menu = false;
</script>

<div class="widget-card" out:scale={{ duration: 600, easing: quintOut }}>
  <Block
    class={
      `widget-card-container
      ${shadow ? 'elevation-4' : ''}
   `}
    strong
    inset
  > 
    {#if loading}
      <div class="widget-card-loading">
        <Preloader />
      </div>
    {:else}
      <div class="widget-card-inner">
        <BlockFooter class={`widget-card-header color-theme-${color}`}>
          <div
            class={`widget-card-dragger ${dragger ? 'widget-card-dragger-enabled' : ''}`}
            on:pointerdown={dragger}
          >
            {#if !$$slots.header}
              {header}
            {:else}
              <slot name="header" />
            {/if}
          </div>
        </BlockFooter>
        <div class="widget-card-title">
          {#if $$slots['before-title']}
            <div class="widget-card-title-before">
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
                <div class="widget-card-subtitle">{subtitle}</div>
              {/if}
            {:else}
              <div class="widget-card-subtitle"><slot name="subtitle" /></div>
            {/if}
          </div>
        </div>
  
        <BlockFooter class="widget-card-description">
          {#if !$$slots.description}
            {description}
          {:else}
            <slot name="description" />
          {/if}
        </BlockFooter>
        <slot name="after-title" />
      </div>
      <div class={`widget-card-content color-theme-${color}`}>
        <slot />
      </div>
      <div class={`widget-card-footer color-theme-${color}`}>
        <slot name="controls" />
        <span class="flex-grow" />
        {#if menu}
          <IconButton
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
</div>
