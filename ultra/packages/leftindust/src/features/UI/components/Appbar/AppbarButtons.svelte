<script lang="ts">
  import type { AppbarButton } from '.';
  import type { Popover } from 'framework7/types';

  import { _ } from '@/language';
  import { openPopover } from '@/features/View';
  
  import MenuButton from '../MenuButton/MenuButton.svelte';
  import Menu from '../Menu/Menu.svelte';

  export let buttons: AppbarButton[];
  
  let menuRef: Popover.Popover;

  let width = window.innerWidth;
  let mobile = false;
  
  let condensingNeeded = buttons.some(({ condense }) => condense);

  $: mobile = width < 780;
</script>

<svelte:window bind:innerWidth={width} />

<Menu
  items={buttons
    .filter(({ condense }) => condense)
    .map(({ title, icon, onClick }) => ({ title, icon, onClick }))
  }
  bind:instance={menuRef}
/>

{#each buttons as button}  
  {#if !button.condense || (button.condense && !mobile)}
    <MenuButton
      title={button.title}
      icon={button.icon}
      on:click={() => button.onClick && button.onClick()}
    />
  {/if}
{/each}

{#if mobile && condensingNeeded}
  <MenuButton
    title={$_('generics.more')}
    icon={{ f7: 'ellipsis_circle_fill', color: 'purple' }}
    on:click={(event) => openPopover(menuRef, event)}
  />
{/if}
