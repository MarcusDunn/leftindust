<style lang="scss" global>
  @import './IntakeFooter.scss';
</style>

<script lang="ts">
  import Footer from '@/features/UI/components/Footer/Footer.svelte';
  import SegmentedText from '@/features/UI/components/SegmentedText/SegmentedText.svelte';
  import { Progressbar, Row, Col, Button } from 'framework7-svelte';

  import { _ } from '@/language';

  let width = window.innerWidth;

  const color = 'deeppurple';
</script>

<svelte:window bind:innerWidth={width} />

<Footer class="intake-intake_footer">
  <div class="intake-intake_footer-content">
    <div class="intake-intake_footer-progress">
      <Progressbar {color} progress={66} />
      <SegmentedText
        text={['Introduction', 'Symptoms', 'Stiffness', 'Pain', 'Function, daily living']}
        {color}
        style="margin-top: 15px;"
      />
    </div>
    <div class="flex-grow" />
    <div class="intake-intake_footer-controls">
      <Row>
        <Col width="50">
          <Button {color} round outline>{$_('generics.back')}</Button>
        </Col>
        <Col width="50">
          <Button {color} round fill>{$_('generics.next')}</Button>
        </Col>
      </Row>
    </div>
  </div>
</Footer>