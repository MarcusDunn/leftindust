<script lang="ts">
  import { SurveyTemplateInputType, type SurveyTemplate } from '@/api/server';
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import RecordSections from '../RecordSections/RecordSections.svelte';
  import type { AnySchema } from 'yup';
  import type Lazy from 'yup/lib/Lazy';

  import RecordFooter from '../RecordFooter/RecordFooter.svelte';
  import { getYupInputTypeFromTemplateCategory } from '@/features/Template';
  import * as yup from 'yup';
  import { createForm } from 'felte';
  import type { RecordForm, RecordValues } from '../..';
  import { validator } from '@felte/validator-yup';

  export let template: SurveyTemplate;

  let pageRef: HTMLDivElement;

  let currentSectionIndex = 0;
  let complete = false;

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

  let values: RecordValues = template.sections.map(({ inputs, id }) => ({
    id,
    inputs: inputs.map(({ type, id }) => ({
      id,
      value: getValueFromType(type),
    })),
  }));

  const forms: RecordForm[] = template.sections.map((section) => {
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

          if (isComplete) complete = isComplete;
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
</script>

<Page bind:instance={pageRef}>
  <svelte:fragment slot="fixed">
    <Appbar />
    <RecordFooter
      {template}
      {forms}
      {complete}
      bind:values
      bind:currentSectionIndex
    />
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