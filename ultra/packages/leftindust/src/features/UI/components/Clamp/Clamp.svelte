<script lang="ts">
  import './Clamp.scss';
  
  export let text: string; 
  export let opened = false;
</script>

<span
  class="ui-clamp"
  on:click={() => opened = !opened}
>
  {#if opened}
    {text}
  {:else}
  <span class="ui-clamp-ellipsis">
    {text.charAt(0)}.
  </span>
  {/if}
</span>
