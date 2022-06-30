<script lang="ts">
  import type { Popover as PopoverType } from 'framework7/types';
  import Popover from '@/features/View/components/Popover/Popover.svelte';

  export let instance: PopoverType.Popover;
</script>

<Popover bind:instance>

</Popover>