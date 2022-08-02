<script lang="ts">
  import type { Template } from '../Templates';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import IntakeSections from './components/IntakeSections/IntakeSections.svelte';

  const template: Template = {
    title: '',
    sections: [],
  };

  let index = 0;
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar />
    <Footer >
      <p>Test</p>
    </Footer>
  </svelte:fragment>
  
  <IntakeSections sections={template.sections} bind:index />
</Page>