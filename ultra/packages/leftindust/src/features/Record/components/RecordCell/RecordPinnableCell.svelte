<script lang="ts">
  import type { PartialCompleteSurveyFragmentFragment } from '@/api/server';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
  
  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import { createEventDispatcher } from 'svelte';
  import RecordTags from '../RecordTags/RecordTags.svelte';

  const dispatch = createEventDispatcher();

  export let record: PartialCompleteSurveyFragmentFragment;
  export let link = true;
  export let pinned = false;
</script>

<Cell
  title={record.surveyTemplate.title}
  {link}
  on:click
>
  <PinButton slot="root-start" {pinned} on:pin={({ detail }) => dispatch('pin', detail)} />
  <RecordTags slot="text" {...record} small outline />
</Cell>
