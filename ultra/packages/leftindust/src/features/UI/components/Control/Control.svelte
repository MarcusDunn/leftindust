<script lang="ts">
  import { Icon } from 'framework7-svelte';
  import type { Framework7Icon } from '../..';

  export let title: string;
  export let text: string;

  export let icon: Framework7Icon | undefined = undefined;
</script>

<div
  style="display: flex; align-items: center; width: 100%"
>
  <Icon {...icon} style="font-size: 26px; margin-right: 25px" />
  <div>
    <h3 class="no-margin">{title}</h3>
    <p style="margin: 0; font-size: 12px">{text}</p>
  </div>
  <div style="flex-grow: 1" />
  <slot />
</div>