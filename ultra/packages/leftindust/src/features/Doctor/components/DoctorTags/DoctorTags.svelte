<script lang="ts">
  import { Chip, Icon } from 'framework7-svelte';

  export let title: string | undefined = undefined;

  export let outline = false;
  export let small = false;

</script>

<span class="one-line-word-clamp">
  {#if title}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={title}
      {outline}
      mediaBgColor="primary"
    >
      <span slot="media"><Icon f7="briefcase" /></span>
    </Chip>
  {/if}
</span>
