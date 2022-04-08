<script lang="ts">
  import { Link, Icon } from 'framework7-svelte';
  import type { CollapsableButtonIcons } from '.';

  export let opened = false;

  export let icons: CollapsableButtonIcons = {
    opened: { f7: 'chevron_up_circle_fill', color: 'gray' },
    closed: { f7: 'chevron_down_circle_fill', color: 'gray' },
  };

</script>

<Link class="accordion-item-toggle" on:click>
  {#if opened}
    <Icon {...icons.opened} />
  {:else}
    <Icon {...icons.closed} />
  {/if}
</Link>
