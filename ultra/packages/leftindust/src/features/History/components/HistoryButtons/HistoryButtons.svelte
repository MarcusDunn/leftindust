<script lang="ts">
  import type { Router } from 'framework7/types';
  import { goForwardInHistory, goBackInHistory } from '../..';

  import { currentHistoryIndexes, history } from '../../store';

  import { _ } from '@/language';

  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { masterDetailBreakpoint } from '@/features/View';

  export let f7router: Router.Router | undefined = undefined;

  const viewName = f7router?.view.name;

  let width = window.innerWidth;
  let mobile = false;

  $: mobile = width < 780;
</script>

<svelte:window bind:innerWidth={width} />

{#key $currentHistoryIndexes && width}
  <MenuButton
    title={$_('generics.back')}
    icon={{ f7: 'chevron_left', color: 'gray' }}
    on:click={() => f7router && goBackInHistory(f7router)}
    disabled={!f7router
      || $history[f7router.view.name]?.length === 1
      || (!!viewName && $currentHistoryIndexes[viewName] === 1 && $history[f7router.view.name][0].master && window.innerWidth > masterDetailBreakpoint - 131)}
  />

  {#if !mobile}
    <MenuButton
      title={$_('generics.next')}
      icon={{ f7: 'chevron_right', color: 'gray' }}
      on:click={() => f7router && goForwardInHistory(f7router)}
      disabled={!f7router || !(f7router && viewName && $currentHistoryIndexes[viewName] < $history[f7router.view.name]?.length - 1)}
    />
  {/if}
{/key}