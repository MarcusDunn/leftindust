<script lang="ts">
  import type { SurveyTemplate } from '@/api/server';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import RecordSections from './components/RecordSections/RecordSections.svelte';

  import { _ } from '@/language';
  import RecordFooter from './components/RecordFooter/RecordFooter.svelte';
  import RecordPoweredBy from './components/RecordPoweredBy/RecordPoweredBy.svelte';

  const template: SurveyTemplate = {
    id: { value: 0 },
    title: '',
    sections: [],
    calculations: [],
  };

  let index = 0;
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar />
    <RecordFooter />
    <RecordPoweredBy />
  </svelte:fragment>

  <RecordSections sections={template.sections} bind:index />
</Page>