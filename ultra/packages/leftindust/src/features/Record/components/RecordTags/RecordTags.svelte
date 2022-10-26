<script lang="ts">
  import type { PartialCompleteSurveyFragmentFragment } from '@/api/server';

  import { Chip, Icon } from 'framework7-svelte';

  export let sections: PartialCompleteSurveyFragmentFragment['sections'] | undefined = undefined;

  export let outline = false;
  export let small = false;
</script>

<Chip
  class={`${small ? 'chip-small' : ''}`}
  text={`${sections?.length} section(s)`}
  mediaBgColor="purple"
  {outline}
>
  <span slot="media"><Icon f7="square_on_square_fill" /></span>
</Chip>