<script lang="ts">
  import type { PartialTemplateFragmentFragment } from '@/api/server';
  import type { RecursivePartial } from '@/types';
  import { Chip, Icon } from 'framework7-svelte';

  export let outline = false;
  export let small = false;

  export let section: RecursivePartial<PartialTemplateFragmentFragment['sections'][number]> | undefined;
</script>

{#if section?.inputs}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={`${section?.inputs.length} inputs`}
    mediaBgColor="blue"
    {outline}
  >
    <span slot="media"><Icon f7="square_split_1x2_fill" /></span>
  </Chip>
{/if}