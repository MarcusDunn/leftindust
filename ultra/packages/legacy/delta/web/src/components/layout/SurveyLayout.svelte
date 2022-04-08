<script lang="ts" context="module">
  export type SurveyLayoutSubmissionField = {
    question: string;
    answer: string | number | string[];
  };

  export type SurveyLayoutSubmissionSection = {
    title: string;
    fields: SurveyLayoutSubmissionField[];
  };

  export type SurveyLayoutSubmission = {
    title: string;
    sections: SurveyLayoutSubmissionSection[];
  };
</script>

<script lang="ts">
  import type { FormSectionInput, FormTemplateInput } from '../../schemas/leftindust.schema';
  import type { Swiper as SwiperType } from 'swiper';

  import { DataType } from '../../schemas/leftindust.schema';
  import { tick } from 'svelte';

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

  import ContainerUI from '../../ui/layout/ContainerUI/ContainerUI.svelte';
  import FooterUI from '../../ui/layout/FooterUI/FooterUI.svelte';

  import FormInput from '../input/FormInput.svelte';
  import language from '../../languages/index';

  import { createEventDispatcher } from 'svelte';

  const dispatch = createEventDispatcher();

  export let template: FormTemplateInput;
  export let preview: number | undefined = undefined;
  export let color = 'deeppurple';

  let swiper: SwiperType | undefined;

  let complete = false;
  let completed = false;
  let progressbar: Progressbar | undefined;


  let activeIndex = 0;

  let submission: SurveyLayoutSubmission;
  let secitons: FormSectionInput[] = [];

  const setSubmission = () => {
    submission = {
      title: template.name,
      sections: template.sections.map((section) => ({
        title: section.name,
        fields: section.fields.map((field) => ({
          question: field.title,
          answer: (() => {
            switch (field.dataType) {
              case DataType.MultiMuliSelect:
              case DataType.SingleMuliSelect:
                return [];
              case DataType.Date:
                return new Date().getTime();
              default:
                return '';
            }
          })(),
        })),
      })),
    };
  };

  $: void tick().then(() => {
    template;
    setSubmission();
  });

  const getIndex = () => {
    if (swiper)
      activeIndex = preview ?? swiper.activeIndex;
  };

  const setSwiper = (event: CustomEvent<[SwiperType]>) => [swiper] = event.detail;

  $: if (submission?.sections) {
    f7.progressbar.set(progressbar?.$$.ctx[1], activeIndex / submission.sections.length * 100);
  }

  $: if (typeof preview === 'number') activeIndex = preview;
  $: void tick().then(() => {
    secitons = preview ? [template.sections[preview]] : template.sections;
  });

  $: complete = activeIndex === template.sections.length - 1;
  $: completed = activeIndex > template.sections.length - 1;

  // Scroll to the top each time a section is completed
  // @ts-expect-error
  $: activeIndex, document.querySelector('.swiper-container')?.scrollTo({ top: 0 });

</script>

<div class="ultra-survey">
  <ContainerUI>
    <div class={`ultra-survey-form ${typeof preview === 'undefined' ? 'ultra-survey-form-with-footer' : ''}`}>
      <div>
        {#if completed}
          <h2 style="margin-bottom: 5px">
            <Icon f7="checkmark_circle_fill" color="green" style="font-size: 36px;margin-bottom:5px" />
            {language().buttons.complete.text}
          </h2>
          <h5>{language().survey.complete.header.text}</h5>
        {:else}
          <h2 class="two-line-word-clamp" style="margin-bottom: 5px">
            {template.sections[activeIndex].name}
          </h2>
          {#if template.sections[activeIndex].description}
            <h5>{template.sections[activeIndex].description}</h5>
          {/if}
        {/if}

        {#if typeof preview === 'undefined'}
          <Progressbar bind:this={progressbar} {color} progress={0} />
        {/if}
      </div>
      <Swiper
        allowTouchMove={false}
        observer
        observeParents
        autoHeight
        preventClicksPropagation={false}
        preventClicks={false}
        on:swiper={setSwiper}
        on:slideChange={getIndex}
      >
        {#each secitons as section, sectionIndex (sectionIndex)}
          <SwiperSlide>
            <div>
              {#each section.fields as field, index (index)}
                <div>
                  <h4>{`${index + 1}. ${field.title}`}</h4>
                  {#if submission.sections[activeIndex]?.fields[index]}
                    <FormInput
                      type={field.dataType}
                      multiSelectPossibilities={field.multiSelectPossibilities}
                      bind:value={submission.sections[activeIndex].fields[index].answer}
                    />
                  {/if}
                </div>
              {/each}
            </div>
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
  {#if typeof preview === 'undefined'}
    <FooterUI>
      <ContainerUI>
        {#if !completed}
          <Row>
            <Col width="50">
              <Button
                large
                outline
                disabled={activeIndex === 0}
                on:click={() => swiper?.slidePrev()}
                {color}
              >
                {language().buttons.back.text}
              </Button>
            </Col>
            <Col width="50">
              <Button
                fill
                large
                disabled={submission?.sections[activeIndex].fields
                  .some(({ answer }) => !answer || (Array.isArray(answer) && answer.length === 0)) ?? false}
                on:click={() => swiper?.slideNext()}
                {color}
              >
                {complete ? language().buttons.complete.text : language().buttons.next.text}
              </Button>
            </Col>
          </Row>
        {:else}
          <Button
            fill
            large
            on:click={() => dispatch('submit', submission) }
            {color}
          >
            {language().buttons.done.text}
          </Button>
        {/if}
        <br />
      </ContainerUI>
    </FooterUI>
  {/if}
</div>
