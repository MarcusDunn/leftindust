<script lang="ts" context="module">
  export type AppBarUINavigation = {
    close?: boolean | {
      popover?: boolean;
      popup?: boolean;
    };
    history?: boolean;
    back?: boolean;
  };

</script>

<script lang="ts">
  import type { Router } from 'framework7/types';

  import {
    f7,
    Navbar,
    NavLeft,
    NavRight,
    Subnavbar,
  } from 'framework7-svelte';


  import language from '../../../languages';

  import MenuButtonUI from '../../button/MenuButtonUI/MenuButtonUI.svelte';
  import AppBarHistoryUI from './AppBarHistoryUI.svelte';

  export let navigation: AppBarUINavigation = {
    close: false,
    history: false,
    back: false,
  };

  export let f7router: Router.Router | undefined = undefined;

  export let autoScrollIndicator = true;

</script>

<Navbar class={`ultra-appbar ${!autoScrollIndicator ? 'navbar-scroll' : ''}`}>
  {#if $$slots.left || navigation.close || navigation.back || navigation.history}
    <NavLeft>
      {#if (navigation.close && typeof navigation.close === 'boolean')
        || (navigation.close && 'popover' in navigation.close && navigation.close.popover)
        || (navigation.close && 'popup' in navigation.close && navigation.close.popup)
      }
        <MenuButtonUI
          title={language().buttons.close.text}
          icon={{ f7: 'xmark_circle_fill' }}
          on:click={() => {
            if (
              typeof navigation.close === 'boolean'
              || (navigation.close && 'popup' in navigation.close && navigation.close.popup)
            ) {
              f7.popup.close();
            }

            if (
              typeof navigation.close === 'boolean'
              || (navigation.close && 'popover' in navigation.close && navigation.close.popover)
            ) {
              f7.popover.close();
            } 
          }}
        />
      {/if}
      {#if navigation.back}
        <MenuButtonUI
          title={language().buttons.back.text}
          icon={{ f7: 'chevron_left_circle_fill', color: 'gray' }}
          on:click={() => f7.view.current.router.back() }
        />
      {/if}
      {#if navigation.history}
        <AppBarHistoryUI {f7router} />
      {/if}
      <slot name="left" />
    </NavLeft>
  {/if}
  <slot />
  {#if $$slots.center}
    <div class="title">
      <slot name="center" />
    </div>
  {/if}
  {#if $$slots.right}
    <NavRight>
      <slot name="right" />
    </NavRight>
  {/if}

  {#if $$slots.subnavbar}
    <Subnavbar>
      <slot name="subnavbar" />
    </Subnavbar>
  {/if}
</Navbar>
