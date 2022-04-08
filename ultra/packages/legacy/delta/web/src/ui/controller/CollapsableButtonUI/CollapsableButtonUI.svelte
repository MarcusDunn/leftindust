<script lang="ts">
  import { Link, Icon } from 'framework7-svelte';
  import type { Framework7Icon } from '../../../modules/UIModule';

  type CollapsableButtonUIIcons = {
    opened: Framework7Icon;
    closed: Framework7Icon;
  };

  export let opened = false;

  export let icons: CollapsableButtonUIIcons = {
    opened: { f7: 'chevron_up_circle_fill', color: 'gray' },
    closed: { f7: 'chevron_down_circle_fill', color: 'gray' },
  };

</script>

<Link class="ultra-collapsable-button accordion-item-toggle" on:click>
  {#if opened}
    <Icon {...icons.opened} />
  {:else}
    <Icon {...icons.closed} />
  {/if}
</Link>
