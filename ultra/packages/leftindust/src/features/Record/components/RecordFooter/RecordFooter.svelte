<style lang="scss" global>
  @import './RecordFooter.scss';
</style>

<script lang="ts">
  import Footer from '@/features/UI/components/Footer/Footer.svelte';
  import SegmentedText from '@/features/UI/components/SegmentedText/SegmentedText.svelte';
  import { Progressbar, Row, Col, Button, f7 } from 'framework7-svelte';

  import type { RecordForm, RecordValues } from '../..';
  import { _ } from '@/language';
  import type { SurveyTemplate } from '@/api/server';

  export let template: SurveyTemplate;
  export let currentSectionIndex: number;
  export let forms: RecordForm[];

  export let complete: boolean;

  export let values: RecordValues;

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

<Footer class="record-record_footer" style={`display: ${!complete ? 'block' : 'none'} !important`}>
  <div class="record-record_footer-content">
    {#if template.sections.length > 1}
      <div class="record-record_footer-progress">
        <Progressbar {color} progress={0} bind:this={progressbar} />
        {#if !complete}
          <SegmentedText
            text={template.sections.map(({ title }) => title)}
            index={currentSectionIndex}
            {color}
            style="margin-top: 15px;"
          />
        {/if}
      </div>
    {/if}
    <div class="flex-grow" />
    <div class="record-record_footer-controls">
      {#if !complete}
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
              disabled={currentSectionIndex < 1}
            >
              {$_('generics.back')}
            </Button>
          </Col>
          <Col width="50">
            {#if currentSectionIndex < (template.sections.length - 1)}
              <Button
                {color}
                round
                fill
                on:click={() => {
                  forms[currentSectionIndex].ref?.requestSubmit();
                  console.log(forms[currentSectionIndex]);
                }}
              >
                {$_('generics.next')}
              </Button>
            {:else}
              <Button
                {color}
                round
                fill
                on:click={() => {
                  forms[currentSectionIndex].ref?.requestSubmit();
                }}
              >
                {$_('generics.complete')}
              </Button>
            {/if}
          </Col>
        </Row>
      {/if}
    </div>
  </div>
</Footer>