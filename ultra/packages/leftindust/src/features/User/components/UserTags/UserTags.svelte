<script lang="ts">
  import type { Group } from '@/api/server/schema/leftindust.schema';
  import { Chip, Icon } from 'framework7-svelte';

  export let group: Group | undefined = undefined;

  export let outline = false;
  export let small = false;

</script>

<span class="one-line-word-clamp">
  {#if group}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={group.name}
      {outline}
      mediaBgColor="teal"
    >
      <span slot="media"><Icon f7="person_3_fill" /></span>
    </Chip>
  {/if}
</span>
