<script lang="ts">
  import Page from '@/features/UI/components/Page/Page.svelte';
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';

  import type { MenuNode } from '../..';
  import type { Router } from 'framework7/types';

  export let nodes: MenuNode[];
  export let f7router: Router.Router;
</script>

<Page>
  <Appbar slot="fixed">
    <MenuButton
      title={$_('generics.back')}
      icon={{ f7: 'chevron_left_circle_fill', color: 'gray' }}
      on:click={() => f7router?.back()}
    /> 
  </Appbar>
</Page>