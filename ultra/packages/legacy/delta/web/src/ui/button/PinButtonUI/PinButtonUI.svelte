<script lang="ts">
  import { createEventDispatcher } from 'svelte';
  import { Link, Icon } from 'framework7-svelte';

  const dispatch = createEventDispatcher();

  export let pinned = false;

</script>

<div class="ultra-pin-button">
  <Link on:click={() => {
    dispatch('pin', !pinned);
    pinned = !pinned;
  }}>
    {#if pinned}
      <Icon f7="pin_slash_fill" />
    {:else}
      <Icon f7="pin_fill" />
    {/if}
  </Link>
</div>