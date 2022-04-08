<script lang="ts" context="module">
  export type SurveyUIInputs = 'text' | 'number' | 'sex' | 'serverity' | 'frequency' | 'agreeability' | 'section';

  export type SurveyUIValue = {
    type: string;
    title: string;
    value: string | undefined;
  };

  export type SurveyUIValueSection = {
    title: string;
    values: SurveyUIValue[];
  };

  export type SurveyUIQuestion = {
    title: string;
    description?: string;
    type: SurveyUIInputs;
  };

  export type SurveyUISection = {
    title: string;
    instructions?: string;
    questions: SurveyUIQuestion[];
  };

  export type SurveyUIBlob = {
    title: string;
    description?: string;
    instructions?: string;
    link?: string;
    sections: SurveyUISection[];
  };
</script>

<script lang="ts">
  import {
    Swiper,
    SwiperSlide,
    Progressbar,
    Row,
    Col,
    Button,
    f7,
    Icon,
    BlockFooter,
    Link,
  } from 'framework7-svelte';
  import { createEventDispatcher } from 'svelte';
  import type { Swiper as SwiperType } from 'swiper';
  import { frequencyScale, severityScale } from '../../../modules/InputsModule';

  import ContainerUI from '@framework/ui/layout/ContainerUI/ContainerUI.svelte';
  import ScaleLabeledUI from '../../input/ScaleUI/ScaleLabeledUI.svelte';
  import FooterUI from '../FooterUI/FooterUI.svelte';
  import language from '../../../languages';
  
  const dispatch = createEventDispatcher();

  export let blob: SurveyUIBlob;

  let swiper: SwiperType | undefined;
  let activeIndex = 0;
  let progressbar: undefined | Progressbar;
  let values: SurveyUIValueSection[];

  let complete = false;
  let completed = false;

  values = blob.sections.map(({ title, questions }) => ({
    title,
    values: questions.map(({ type, title }) => ({
      type,
      title,
      value: type === 'section' ? 'N/A' : undefined,
    })),
  }));

  const setSwiper = (event: CustomEvent<[SwiperType]>) => [swiper] = event.detail;

  $: f7.progressbar.set(progressbar?.$$.ctx[1], activeIndex / blob.sections.length * 100);
  
  $: complete = activeIndex === blob.sections.length - 1;
  $: completed = activeIndex > blob.sections.length - 1;

  // Scroll to the top each time a section is completed
  // @ts-expect-error
  $: activeIndex, document.querySelector('.swiper-container')?.scrollTo({ top: 0 });
</script>

<div class="ultra-survey">
  <ContainerUI>
    <div class="ultra-survey-form">
      <div>
        {#if completed}
          <h1 style="margin-bottom: 5px">
            <Icon f7="checkmark_circle_fill" color="green" style="font-size: 46px;margin-bottom:5px" />
            {language().buttons.complete.text}
          </h1>
          <h5 style="font-size: 18px">{language().survey.complete.header.text}</h5>
        {:else}
          <h2 class="two-line-word-clamp" style="margin-bottom: 5px">
            {blob.sections[activeIndex].title}
          </h2>
          {#if blob.sections[activeIndex].instructions}
            <h5>{blob.sections[activeIndex].instructions}</h5>
          {/if}
        {/if}

        <Progressbar bind:this={progressbar} color="primary" progress={0} />
      </div>
      <Swiper
        allowTouchMove={false}
        on:swiper={setSwiper}
      >
        {#each blob.sections as section}
          <SwiperSlide>
            <div>
              {#each section.questions as question, index}
                <h4>{question.title}</h4>
                {#if values?.[activeIndex]?.values?.[index]}
                  {#if question.type === 'serverity'}
                    <ScaleLabeledUI
                      name="Severity"
                      inputs={severityScale}
                      bind:value={values[activeIndex].values[index].value}
                    />
                  {:else if question.type === 'frequency'}
                    <ScaleLabeledUI
                      name="Frequency"
                      inputs={frequencyScale}
                      bind:value={values[activeIndex].values[index].value}
                    />
                  {/if}
                {/if}
              {/each}
            </div>
            <br />
            <br />
          </SwiperSlide>
        {/each}
        <SwiperSlide>
          <br />
          <BlockFooter style="padding: 0">
            {language().survey.complete.description.text}
            <Link>{language().survey.complete.description.link}</Link>
          </BlockFooter>
        </SwiperSlide>
      </Swiper>
    </div>
  </ContainerUI>
  <FooterUI>
    <ContainerUI>
      {#if !completed}
        <Row>
          <Col width="50">
            <Button
              large
              outline
              disabled={activeIndex === 0}
              on:click={() => {
                swiper?.slidePrev();
                activeIndex = activeIndex - 1;
              }}
            >
              {language().buttons.back.text}
            </Button>
          </Col>
          <Col width="50">
            <Button
              fill
              large
              disabled={values[activeIndex].values.some(({ value }) => !value)}
              on:click={() => {
                swiper?.slideNext();
                activeIndex = activeIndex + 1;
              }}
            >
              {complete ? language().buttons.complete.text : language().buttons.next.text}
            </Button>
          </Col>
        </Row>
      {:else}
        <Button
          fill
          large
          on:click={() => dispatch('submit', values) }
        >
          {language().buttons.done.text}
        </Button>
      {/if}
      <br />
    </ContainerUI>
  </FooterUI>
</div>
