<script lang="ts">
  import type { Router } from 'framework7/types';

  import type { AppbarClose, AppbarButton } from '.';

  import './Appbar.scss';

  import {
    f7,
    Navbar,
    NavLeft,
    NavRight,
    Subnavbar,
  } from 'framework7-svelte';

  import { _ } from '@/language';

  import MenuButton from '../MenuButton/MenuButton.svelte';
  import HistoryButtons from '@/features/History/components/HistoryButtons/HistoryButtons.svelte';
  import AppbarButtons from './AppbarButtons.svelte';

  export let f7router: Router.Router | undefined = undefined;

  export let close: AppbarClose = false;

  export let history = false;
  export let autoScrollIndicator = true;

  export let left: AppbarButton[] = [];
  export let right: AppbarButton[] = [];

</script>

<Navbar class={`ui-appbar ${!autoScrollIndicator ? 'navbar-scroll' : ''}`}>
  {#if $$slots.left || left.length > 0 || close || history}
    <NavLeft>
      {#if (close && typeof close === 'boolean')
        || (close && 'popover' in close && close.popover)
        || (close && 'popup' in close && close.popup)
      }
        <MenuButton
          title={$_('generics.close')}
          icon={{ f7: 'xmark_circle_fill' }}
          on:click={() => {
            if (
              typeof close === 'boolean'
              || (close && 'popup' in close && close.popup)
            ) {
              f7.popup.close();
            }

            if (
              typeof close === 'boolean'
              || (close && 'popover' in close && close.popover)
            ) {
              f7.popover.close();
            } 
          }}
        />
      {/if}
      
      {#if history}
        <HistoryButtons {f7router} />
      {/if}

      <AppbarButtons buttons={left} />

      <slot name="left" />
    </NavLeft>
  {/if}
  
  <slot />
  {#if $$slots.center}
    <div class="title">
      <slot name="center" />
    </div>
  {/if}

  {#if $$slots.right || right.length > 0}
    <NavRight>
      <AppbarButtons buttons={right} />
      <slot name="right" />
    </NavRight>
  {/if}

  {#if $$slots.subnavbar}
    <Subnavbar>
      <slot name="subnavbar" />
    </Subnavbar>
  {/if}
</Navbar>
