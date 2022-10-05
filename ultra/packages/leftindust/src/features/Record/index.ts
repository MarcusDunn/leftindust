import { CompleteSurveyInputType, type CreateCompleteSurvey, type CreateCompleteSurveyInput, type Scalars, type SurveyTemplateSectionIdInput, type SurveyTemplateSectionInputIdInput } from '@/api/server';
import type { createForm } from 'felte';
import { isNonEmptyArrayOfNumbers, isNonEmptyArrayOfStrings } from '../Helpers';

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
  const mappedRecord: CreateCompleteSurvey['completeSurveyTemplateSections'] = values.map((section): CreateCompleteSurvey['completeSurveyTemplateSections'][number] => ({
    surveyTemplateSectionId: {
      value: section.id.value,
    },
    completedSurveyInputs: section.inputs.map((input): CreateCompleteSurveyInput => ({
      surveyTemplateSectionInputId: {
        value: input.id.value,
      },
      type: CompleteSurveyInputType.String,
      ...(() => {
        switch (typeof input.value) {
          case 'string':
            return {
              stringInput: input.value,
              type: CompleteSurveyInputType.String,
            };
          case 'number':
            return {
              numberValue: input.value,
              type: CompleteSurveyInputType.Number,
            };
          default:
            if (Array.isArray(input.value)) {
              if (isNonEmptyArrayOfStrings(input.value)) {
                return {
                  stringArrayInput: input.value,
                  type: CompleteSurveyInputType.StringArray,
                };
              } else if (isNonEmptyArrayOfNumbers(input.value)) {
                return {
                  numberArrayInput: input.value,
                  type: CompleteSurveyInputType.NumberArray,
                };
              }
            }
        }
      })(),
    })),
  }));

  return mappedRecord;
};