<script lang="ts">
  import { createEventDispatcher } from 'svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';

  const dispatch = createEventDispatcher();
</script>

<div class="ultra-delete-button">
  <MenuButtonUI
    icon={{ f7: 'minus_circle_fill', color: 'red' }}
    on:click={() => dispatch('delete')}
  />
</div>