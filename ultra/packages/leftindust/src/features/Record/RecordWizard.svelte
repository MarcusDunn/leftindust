<script lang="ts">
  import { SurveyTemplateInputType, type CreateCompleteSurveySection, type SurveyTemplate } from '@/api/server';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import RecordSections from './components/RecordSections/RecordSections.svelte';
  import type { AnySchema } from 'yup';
  import type Lazy from 'yup/lib/Lazy';

  import { _ } from '@/language';
  import RecordFooter from './components/RecordFooter/RecordFooter.svelte';
  import RecordPoweredBy from './components/RecordPoweredBy/RecordPoweredBy.svelte';
  import { getYupInputTypeFromTemplateCategory } from '../Template';
  import * as yup from 'yup';
  import { createForm } from 'felte';
  import type { RecordForm, RecordValues } from '.';
  import { validator } from '@felte/validator-yup';

  export let template: SurveyTemplate;

  let currentSectionIndex = 0;

  let values: RecordValues[] = template.sections.map(({ inputs }) => ({
    inputs: inputs.map(({ type }) => ({
      value: (() => {
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
      })(),
    })),
  }));

  const forms: RecordForm[] = template.sections.map((section) => {
    const schemaBuilder: Record<string, AnySchema<unknown, unknown, unknown> | Lazy<any, unknown>> = {};

    section.inputs.forEach((input) => {
      schemaBuilder[input.calculationId] = getYupInputTypeFromTemplateCategory(input.type);
    });

    const schema = yup.object(schemaBuilder);

    return {
      // @ts-expect-error
      form: createForm({
        onSubmit: () => {
          currentSectionIndex += 1;
          console.log('submit');
        },
        extend: [
          validator({ schema }),
        ],
      }),
      ref: undefined,
    };
  });

  console.log(template);
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar />
    <RecordFooter {template} {forms} bind:currentSectionIndex />
    <!--<RecordPoweredBy />-->
  </svelte:fragment>

  <RecordSections
    sections={template.sections}
    {forms}
    bind:values
    bind:currentSectionIndex
  />
</Page>