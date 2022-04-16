<script lang="ts">
  import type { Popover } from 'framework7/types';
  import { openPopover } from '@/features/View';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import AppLauncherPopover from './AppLauncherPopover.svelte';
  import './AppLauncherButton.scss';

  let popover: Popover.Popover;
</script>

<AppLauncherPopover bind:instance={popover} />

<div class="apps-app_launcher_button">
  <MenuButton
    icon={{ f7: 'square_grid_4x3_fill' }}
    on:click={(event) => { openPopover(popover, event); }}
  />
</div>
