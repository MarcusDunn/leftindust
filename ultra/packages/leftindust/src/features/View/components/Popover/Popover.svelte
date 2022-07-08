<script lang="ts">
  import type { Popover as PopoverType } from 'framework7/types';
  
  import { Popover } from 'framework7-svelte';
  
  import { onMount } from 'svelte';

  export let instance: PopoverType.Popover | undefined = undefined;

  let modalRef: Popover;

  onMount(() => {
    instance = (<{ instance: () => PopoverType.Popover }>(modalRef as unknown)).instance();
  });
</script>

<Popover
  class="view-popover"
  bind:this={modalRef}
  {...$$restProps}
>
  <slot />
</Popover>