<script lang="ts">
  import type { SurveyTemplate } from '@/api/server';
  import RecordConsumer from './components/RecordConsumer/RecordConsumer.svelte';

  export let template: SurveyTemplate;
  export let surveyLinkId: string;
</script>

<RecordConsumer {surveyLinkId} bind:template />