<script lang="ts">
  import type { SurveyLinkId, SurveyTemplate } from '@/api/server';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import RecordConsumer from './components/RecordConsumer/RecordConsumer.svelte';
  import RecordExpired from './components/RecordExpired/RecordExpired.svelte';

  export let template: SurveyTemplate;
  export let id: SurveyLinkId;
  export let expired = false;
</script>

{#if !expired}
  <RecordConsumer bind:template {id} />
{:else}
  <Page>
    <Appbar slot="fixed" />
    <br />
    <RecordExpired />
  </Page>
{/if}