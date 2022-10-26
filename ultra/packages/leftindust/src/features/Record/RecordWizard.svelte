<script lang="ts">
  import type { SurveyLinkId, SurveyTemplate } from '@/api/server';
  import RecordConsumer from './components/RecordConsumer/RecordConsumer.svelte';

  export let template: SurveyTemplate;
  export let id: SurveyLinkId;
</script>

<RecordConsumer bind:template {id} />