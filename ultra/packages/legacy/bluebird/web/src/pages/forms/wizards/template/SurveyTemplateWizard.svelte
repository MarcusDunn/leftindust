<script lang="ts">
  import type { Swiper as SwiperType } from 'swiper';
  import {
    BlockFooter,
    Button,
    Col,
    Icon,
    Row,
    Swiper,
    SwiperSlide,
  } from 'framework7-svelte';
  import { get } from 'svelte/store';
  import deepmerge from 'deepmerge';

  import { closeWizard } from '@framework/modules/NavigationModule';
  import getNativeAPI from '@framework/bridge';

  import language from '@framework/languages';
  import {
    SurveyWizardForm,
    SurveyWizardActiveSectionIndex,
    SurveyWizardFormTemplate,
  } from './store/index';

  import SurveyTemplateMutateEngine from '@framework/engines/surveys/SurveyTemplateMutateEngine';

  import WizardSplitUI from '@framework/ui/layout/WizardUI/WizardSplitUI.svelte';
  import IFrameUI from '@framework/ui/view/IFrameUI/IFrameUI.svelte';

  import SurveyWizardSection from './layout/SurveyTemplateWizardSection.svelte';
  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';

  const { Dialog } = getNativeAPI();

  let swiper: SwiperType | undefined;

  let disabled = true;

  const setSwiper = (event: CustomEvent<[SwiperType]>) => [swiper] = event.detail;

  const getIndex = () => {
    if (swiper)
      $SurveyWizardActiveSectionIndex = swiper.activeIndex;
  };

  const reset = () => {
    $SurveyWizardForm = deepmerge({}, SurveyWizardFormTemplate);
    $SurveyWizardActiveSectionIndex = 0;
  };

  const submit = () => void SurveyTemplateMutateEngine($SurveyWizardForm).then((result) => {
    result ? (() => {
      reset();
      closeWizard();
    })() : void Dialog.alert({
      message: language().errors.internalError.text,
      detail: language().errors.request.text,
      buttons: [language().buttons.ok.text],
    });
  });


  $: disabled = !$SurveyWizardForm.name
      || $SurveyWizardForm.sections.some((section) => !(section.name && section.fields.length > 0));

</script>

<WizardSplitUI
  title={language().forms.headers.newSurvey.text}
  subtitle={language().forms.placeholders.addSurveyDescription.text}
  color="deeppurple"
  {disabled}
  on:submit={submit}
  on:close={reset}
>
  <h4>Title</h4>
  <InputUI>
    <input type="text" placeholder="Title" bind:value={$SurveyWizardForm.name} />
  </InputUI>
  <br />
  <BlockFooter>
    Add a self-explanitory title to your form/survey. This is the first thing your users will see before participating in your survey.
  </BlockFooter>
  <br />
  <Swiper
    allowTouchMove={false}
    observer
    observeParents
    autoHeight
    on:swiper={setSwiper}
    on:slideChange={getIndex}
  >
    {#each $SurveyWizardForm.sections as _, index}
      <SwiperSlide>
        <SurveyWizardSection sectionIndex={index} {swiper} />
      </SwiperSlide>
    {/each}
  </Swiper>
  <br />
  <Button
    color="deeppurple"
    fill
    large
    on:click={() => {
      $SurveyWizardForm.sections = [
        ...$SurveyWizardForm.sections,
        {
          name: '',
          number: $SurveyWizardForm.sections.length,
          fields: [],
        },
      ];
      setTimeout(() => {
        // This is just a wierd bug?
        // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
        swiper?.slideTo($SurveyWizardActiveSectionIndex + 1);
      }, 50);
    }}
  >
    New Section
  </Button>
  <p />
  <Row>
    <Col width="50">
      <Button
        color="primary"
        outline
        large
        disabled={$SurveyWizardActiveSectionIndex === 0}
        on:click={() => swiper?.slidePrev()}
      >
        Previous
      </Button>
    </Col>
    <Col width="50">
      <Button
        color="primary"
        outline
        large
        disabled={$SurveyWizardActiveSectionIndex === $SurveyWizardForm.sections.length - 1}
        on:click={() => swiper?.slideNext()}
      >
        Next
      </Button>
    </Col>
  </Row>
  <br />
  <BlockFooter>
    Create new section or navigate between sections you have already created.
  </BlockFooter>
  <br />
  <Button
    color="red"
    fill
    large
    disabled={$SurveyWizardActiveSectionIndex === 0}
    on:click={() => {
      const index = get(SurveyWizardActiveSectionIndex);
      if ($SurveyWizardActiveSectionIndex !== 0) {
        swiper?.slidePrev();
      }

      $SurveyWizardForm.sections = $SurveyWizardForm.sections.filter((_, sectionIndex) => sectionIndex !== index);
    }}
  >
    <Icon f7="trash_fill" style="margin-right: 7px" />
    Delete Section
  </Button>

  <IFrameUI
    views={[{
      url: '/wizard/survey/preview/',
      selected: true,
    }]}
    slot="detail"
  />
</WizardSplitUI>
