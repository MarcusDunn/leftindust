import { CompleteSurveyInputType, type CreateCompleteSurvey, type CreateCompleteSurveyInput, type Scalars, type SurveyTemplateSectionIdInput, type SurveyTemplateSectionInputIdInput } from '@/api/server';
import type { createForm } from 'felte';

export type RecordForm = {
  form:  ReturnType<typeof createForm>;
  ref: HTMLFormElement | undefined;
};

export type RecordValues = ({
  id: SurveyTemplateSectionIdInput;
  inputs: ({
    id: SurveyTemplateSectionInputIdInput;
    value: string | number | boolean | string[] | undefined;
  })[];
})[];

export const mapRecordToCompleteSurveyInput = (
  values: RecordValues,
): CreateCompleteSurvey['completeSurveyTemplateSections'] => {
  // @ts-expect-error
  const mappedRecord: CreateCompleteSurvey['completeSurveyTemplateSections'] = values.map((section) => ({
    surveyTemplateSectionId: {
      value: section.id.value,
    },
    completedSurveyInputs: section.inputs.map((input) => {
      let type: string | number | boolean | string[] | undefined;
      let key: 'numberArrayInput' | 'numberInput' | 'stringArrayInput' | 'stringInput' | undefined;

      switch (typeof input.value) {
        case 'string':
          type = CompleteSurveyInputType.String;
          key = 'stringInput';
          break;
        case 'number':
          type = CompleteSurveyInputType.String;
          key = 'numberInput';
          break;
        default:
          if (Array.isArray(input.value)) {
            if (input.value.some((value) => typeof value === 'string')) {
              type = CompleteSurveyInputType.StringArray;
              key = 'stringArrayInput';
            } else if (input.value.some((value) => typeof value === 'number')) {
              type = CompleteSurveyInputType.NumberArray;
              key = 'numberArrayInput';
            }
          }
          break;
      }

      if (type && key && input.value) {
        return {
          surveyTemplateSectionInputId: {
            value: input.id.value,
          },
          [key]: input.value,
          type,
        };
      }
    // @ts-expect-error
    }).filter((input): input is CreateCompleteSurveyInput => !!input),
  }));

  return mappedRecord;
};