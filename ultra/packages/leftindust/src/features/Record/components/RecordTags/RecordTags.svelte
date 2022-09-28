<script lang="ts">
  import type { PartialCompleteSurveyFragmentFragment } from '@/api/server';

  import { Chip, Icon } from 'framework7-svelte';

  export let sections: PartialCompleteSurveyFragmentFragment['sections'] | undefined = undefined;

  export let outline = false;
  export let small = false;
</script>

<Chip
  class={`${small ? 'chip-small' : ''}`}
  text={'45 responses'}
  mediaBgColor="primary"
  {outline}
>
  <span slot="media"><Icon f7="text_justifyleft" /></span>
</Chip>