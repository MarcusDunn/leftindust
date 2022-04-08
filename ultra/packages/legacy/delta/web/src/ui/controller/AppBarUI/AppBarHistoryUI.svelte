<script lang="ts">
  import type { Router } from 'framework7/types';
  import {
    goForwardInHistory,
    goBackInHistory,
    currentHistoryIndexes,
    history,
  } from '../../../modules/HistoryModule';

  import language from '../../../languages';

  import MenuButtonUI from '../../button/MenuButtonUI/MenuButtonUI.svelte';
  import { MASTER_DETAIL_BREAKPOINT } from '../../../modules/AppModule';
  import { onMount } from 'svelte';

  export let f7router: Router.Router | undefined = undefined;

  let windowWidth = window.innerWidth;
  const viewName = f7router?.view.name;

  onMount(() => {
    window.addEventListener('resize', () => {
      windowWidth = window.innerWidth;
    });
  });
</script>

{#key $currentHistoryIndexes && windowWidth}
  <MenuButtonUI
    title={language().buttons.back.text}
    icon={{ f7: 'chevron_left', color: 'gray' }}
    on:click={() => f7router && goBackInHistory(f7router)}
    disabled={!f7router
    || $history[f7router.view.name]?.length === 1
      || (!!viewName && $currentHistoryIndexes[viewName] === 1 && $history[f7router.view.name][0].master && window.innerWidth > MASTER_DETAIL_BREAKPOINT - 131)}
  />

  <MenuButtonUI
    title={language().buttons.next.text}
    icon={{ f7: 'chevron_right', color: 'gray' }}
    on:click={() => f7router && goForwardInHistory(f7router)}
    disabled={!f7router || !(f7router && viewName && $currentHistoryIndexes[viewName] < $history[f7router.view.name]?.length - 1)}
  />
{/key}