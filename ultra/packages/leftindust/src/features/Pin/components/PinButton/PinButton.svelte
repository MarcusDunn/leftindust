<script lang="ts">
  import { createEventDispatcher } from 'svelte';
  import { Link, Icon } from 'framework7-svelte';

  import './PinButton.scss';

  const dispatch = createEventDispatcher();

  export let pinned = false;

</script>

<div class="pin-pin_button">
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