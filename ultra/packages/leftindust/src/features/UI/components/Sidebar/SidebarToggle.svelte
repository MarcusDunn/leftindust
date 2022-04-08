<script lang="ts">
  import { f7 } from 'framework7-svelte';
  import MenuButton from '../MenuButton/MenuButton.svelte';
  
  import './SidebarToggle.scss';

</script>

<div
  class="ui-sidebar_toggle"
  on:click={() => {
    f7.panel.toggle('.ui-sidebar');
  }}
>
  <MenuButton icon={{ f7: 'sidebar_left' }} />
</div>
