<style lang="scss" global>
  @import './RecordFooter.scss';
</style>

<script lang="ts">
  import Footer from '@/features/UI/components/Footer/Footer.svelte';
  import SegmentedText from '@/features/UI/components/SegmentedText/SegmentedText.svelte';
  import { Progressbar, Row, Col, Button, f7 } from 'framework7-svelte';

  import type { RecordForm } from '../..';
  import { _ } from '@/language';
  import type { SurveyTemplate } from '@/api/server';

  export let template: SurveyTemplate;
  export let currentSectionIndex: number;
  export let forms: RecordForm[];

  let progressbar: Progressbar | undefined;

  $: if (template.sections.length > 1) {
    f7.progressbar.set(
      progressbar?.$$.ctx[1],
      (currentSectionIndex / template.sections.length) * 100,
    );
  }

  let width = window.innerWidth;

  const color = 'deeppurple';
</script>

<svelte:window bind:innerWidth={width} />

<Footer class="record-record_footer">
  <div class="record-record_footer-content">
    {#if template.sections.length > 1}
      <div class="record-record_footer-progress">
        <Progressbar {color} progress={0} bind:this={progressbar} />
        <SegmentedText
          text={template.sections.map(({ title }) => title)}
          index={currentSectionIndex}
          {color}
          style="margin-top: 15px;"
        />
      </div>
    {/if}
    <div class="flex-grow" />
    <div class="record-record_footer-controls">
      <Row>
        <Col width="50">
          <Button
            {color}
            round
            outline
            on:click={() => {
              if (currentSectionIndex > 0) {
                currentSectionIndex--;
              }
            }}
          >
            {$_('generics.back')}
          </Button>
        </Col>
        <Col width="50">
          <Button
            {color}
            round
            fill
            on:click={() => {
              forms[currentSectionIndex].ref?.requestSubmit();
            }}
          >
            {$_('generics.next')}
          </Button>
        </Col>
      </Row>
    </div>
  </div>
</Footer>