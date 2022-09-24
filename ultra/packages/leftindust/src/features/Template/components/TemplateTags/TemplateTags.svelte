<script lang="ts">
  import type { SurveyTemplate } from '@/api/server';
  import type { RecursivePartial } from '@/types';
  import { Chip, Icon } from 'framework7-svelte';

  export let outline = false;
  export let small = false;

  export let template: RecursivePartial<SurveyTemplate> | undefined;
</script>

{#if template?.sections}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={`${template?.sections.length} sections`}
    mediaBgColor="purple"
    {outline}
  >
    <span slot="media"><Icon f7="square_fill_on_square_fill" /></span>
  </Chip>
{/if}

{#if template?.calculations}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={`${template?.calculations.length} calculations`}
    mediaBgColor="teal"
    {outline}
  >
    <span slot="media"><Icon f7="sum" /></span>
  </Chip>
{/if}