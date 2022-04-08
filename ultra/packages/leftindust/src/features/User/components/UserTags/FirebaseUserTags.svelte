<script lang="ts">
  import { Chip } from 'framework7-svelte';

  export let uid: string | undefined = undefined;

  export let outline = false;
  export let small = false;

</script>

<span class="one-line-word-clamp">
  {#if uid}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={uid}
      {outline}
      style="margin-left: 0px"
    />
  {/if}
</span>
