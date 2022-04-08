<script lang="ts">
  import { Chip } from 'framework7-svelte';

  export let relationship: string | undefined = undefined;
  export let phone: string | undefined = undefined;

  export let outline = false;
  export let small = false;

</script>

<span class="one-line-word-clamp">
  {#if phone}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={phone}
      {outline}
      style="margin-left: 0px"
    />
  {/if}
  {#if relationship}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={relationship}
      outline={outline}
    />
  {/if}
</span>
