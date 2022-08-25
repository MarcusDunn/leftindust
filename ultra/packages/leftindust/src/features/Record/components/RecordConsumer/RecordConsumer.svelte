<script lang="ts">
  import { client, SurveyTemplateInputType, type SurveyTemplate } from '@/api/server';
  import Appbar from '../../../UI/components/Appbar/Appbar.svelte';
  import Page from '../../../UI/components/Page/Page.svelte';
  import RecordSections from '../../components/RecordSections/RecordSections.svelte';
  import type { AnySchema } from 'yup';
  import type Lazy from 'yup/lib/Lazy';

  import { _ } from '@/language';
  import RecordFooter from '../../components/RecordFooter/RecordFooter.svelte';
  import RecordPoweredBy from '../../components/RecordPoweredBy/RecordPoweredBy.svelte';
  import { getYupInputTypeFromTemplateCategory } from '../../../Template';
  import * as yup from 'yup';
  import { createForm } from 'felte';
  import type { RecordForm, RecordValues } from '../../';
  import { validator } from '@felte/validator-yup';

  export let template: SurveyTemplate;

  let pageRef: HTMLDivElement;

  let currentSectionIndex = 0;
  let complete = false;

  let values: RecordValues[];
  let forms: RecordForm[];

  const getValueFromType = (type: SurveyTemplateInputType) => {
    switch (type) {
      case SurveyTemplateInputType.Text:
      case SurveyTemplateInputType.Paragraph:
        return '';
      case SurveyTemplateInputType.Number:
      case SurveyTemplateInputType.Date:
        return undefined;
      case SurveyTemplateInputType.SingleSelect:
      case SurveyTemplateInputType.MultiSelect:
        return [];
    }
  };

  $: values = template.sections.map(({ inputs }) => ({
    inputs: inputs.map(({ type }) => ({
      value: getValueFromType(type),
    })),
  }));

  $: forms = template.sections.map((section) => {
    const schemaBuilder: Record<string, AnySchema<unknown, unknown, unknown> | Lazy<any, unknown>> = {};
    const initialValues: Record<string, unknown> = {};

    section.inputs.forEach((input) => {
      schemaBuilder[input.calculationId] = getYupInputTypeFromTemplateCategory(input.type);
      initialValues[input.calculationId] = getValueFromType(input.type);
    });

    const schema = yup.object(schemaBuilder);

    return {
      // @ts-expect-error
      form: createForm({
        initialValues,
        onSubmit: () => {
          currentSectionIndex += 1;

          const isComplete = currentSectionIndex === (template.sections.length);

          if (isComplete) {
            complete = isComplete;
          }
        },
        extend: [
          validator({ schema }),
        ],
      }),
      ref: undefined,
    };
  });

  const scrollTop = () => {
    pageRef?.scrollTo(0, 0);
  };

  $: currentSectionIndex, scrollTop();

  console.log(template);
</script>

<Page bind:instance={pageRef}>
  <svelte:fragment slot="fixed">
    <Appbar />
    <RecordFooter {template} {forms} {complete} bind:currentSectionIndex />
    <!--<RecordPoweredBy />-->
  </svelte:fragment>

  <RecordSections
    sections={template.sections}
    calculations={template.calculations}
    {forms}
    {complete}
    bind:values
    bind:currentSectionIndex
  />
</Page>