<script lang="ts">
  import type { PartialCompleteSurveyFragmentFragment } from '@/api/server';
  
  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import RecordTags from '../RecordTags/RecordTags.svelte';

  // export let record: PartialCompleteSurveyFragmentFragment;
  export let link = true;
</script>

<Cell
  title="KOOS Knee Survey"
  header={new Date().toLocaleDateString()}
  {link}
  on:click
>
  <RecordTags slot="text" small outline />
</Cell>
