<script lang="ts">
  import type { Popover } from 'framework7/types';
  import Menu from '@/features/UI/components/Menu/Menu.svelte';
  import { createEventDispatcher } from 'svelte';
  import { _ } from '@/language';

  const dispatch = createEventDispatcher();

  export let instance: Popover.Popover;
</script>

<Menu
  items={[
    {
      title: $_('generics.share'),
      icon: {
        f7: 'arrowshape_turn_up_right_fill',
      },
      onClick: () => dispatch('share'),
    },
    {
      title: $_('generics.quicklook'),
      icon: {
        f7: 'eye_fill',
      },
      onClick: () => dispatch('quicklook'),
    },
  ]}
  bind:instance={instance}
/>