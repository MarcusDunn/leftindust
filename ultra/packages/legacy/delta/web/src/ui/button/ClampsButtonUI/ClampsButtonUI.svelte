<script lang="ts">
  export let text: string; 
  export let opened = false;
</script>

<span
  class="ultra-clamps-button"
  on:click={() => opened = !opened}
>
  {#if opened}
    {text}
  {:else}
  <span class="ultra-clamps-button-ellipsis">
    {text.charAt(0)}.
  </span>
  {/if}
</span>
