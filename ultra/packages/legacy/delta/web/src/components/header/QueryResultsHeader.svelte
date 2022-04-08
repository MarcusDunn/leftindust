<script lang="ts">
  import { createEventDispatcher } from 'svelte';
  import { Icon, Link, BlockFooter } from 'framework7-svelte';

  const dispatch = createEventDispatcher();

  export let length: number;
  export let description = '';
  
</script>

<style lang="scss">
  div {
    display: flex;
    
    h4 {
      flex: 1;
      margin-bottom: 0;
    }

    & > :global(.link) {
      margin-bottom: 5px;
      font-size: 16px;

      & > :global(.f7-icons) {
        font-size: 22px;
        margin-right: 5px;
      }
    }

    & > :global(.block-footer) {
      margin-top: 0;
    }
  }
</style>

<div>
  <h4>{length} results found...</h4>
  <Link on:click={() => dispatch('select')}>
    <Icon f7="selection_pin_in_out" /> Select All
  </Link>
</div>

<div>
  {#if description}
    <BlockFooter>{description}</BlockFooter>
  {/if}
</div>

<br />