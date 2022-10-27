<script lang="ts">
  import type { PartialCompleteSurveyFragmentFragment } from '@/api/server';

  import { Chip, Icon } from 'framework7-svelte';

  export let sections: PartialCompleteSurveyFragmentFragment['sections'] | undefined = undefined;
  export let surveyTemplate:  PartialCompleteSurveyFragmentFragment['surveyTemplate'] | undefined = undefined;
  
  export let outline = false;
  export let small = false;
</script>

{#if sections}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={`${sections?.length} section(s)`}
    mediaBgColor="purple"
    {outline}
  >
    <span slot="media"><Icon f7="square_on_square_fill" /></span>
  </Chip>
{/if}

{#if surveyTemplate}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={`${surveyTemplate?.sections?.map((section) => section.inputs)?.length} input(s)`}
    mediaBgColor="blue"
    {outline}
  >
    <span slot="media"><Icon f7="rectangle_grid_1x2_fill" /></span>
  </Chip>
{/if}