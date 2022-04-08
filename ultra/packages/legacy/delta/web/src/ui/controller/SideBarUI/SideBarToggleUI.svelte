<script lang="ts">
  import { f7 } from 'framework7-svelte';
  import MenuButtonUI from '../../button/MenuButtonUI/MenuButtonUI.svelte';

</script>

<div
  class="ultra-sidebar-toggle"
  on:click={() => {
    f7.panel.toggle('.ultra-sidebar');
  }}
>
  <MenuButtonUI icon={{ f7: 'sidebar_left' }} />
</div>
