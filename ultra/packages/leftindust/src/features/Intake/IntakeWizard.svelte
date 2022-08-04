<script lang="ts">
  import type { Template } from '../Templates';
  import { Button, Col, Progressbar, Row } from 'framework7-svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import IntakeSections from './components/IntakeSections/IntakeSections.svelte';

  import { _ } from '@/language';

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
      <div class="display-flex" style="height: 70px; align-items: center; padding: 0 30px;">
        <div style="width: 400px">
          <Progressbar color="purple" progress={20} />
        </div>
        <div class="flex-grow" />
        <Row style="width: 290px;">
          <Col width="50">
            <Button color="purple" round outline>{$_('generics.back')}</Button>
          </Col>
          <Col width="50">
            <Button color="purple" round fill>{$_('generics.next')}</Button>
          </Col>
        </Row>
      </div>
    </Footer>
  </svelte:fragment>
  
  <IntakeSections sections={template.sections} bind:index />
</Page>