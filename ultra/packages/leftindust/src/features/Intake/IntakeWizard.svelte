<script lang="ts">
  import type { Template } from '../Templates';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import IntakeSections from './components/IntakeSections/IntakeSections.svelte';

  import { _ } from '@/language';
  import IntakeFooter from './components/IntakeFooter/IntakeFooter.svelte';
  import IntakePoweredBy from './components/IntakePoweredBy/IntakePoweredBy.svelte';

  const template: Template = {
    title: '',
    sections: [],
  };

  let index = 0;
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar />
    <IntakeFooter />
    <IntakePoweredBy />
  </svelte:fragment>

  <IntakeSections sections={template.sections} bind:index />
</Page>