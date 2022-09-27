import type { CreateCompleteSurvey, Scalars, SurveyTemplateSectionIdInput, SurveyTemplateSectionInputIdInput } from '@/api/server';
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
  const mappedRecord = values.map((section) => ({
    surveyTemplateSectionId: {
      value: section.id.value,
    },
    completedSurveyInputs: section.inputs.map((input) => ({
      surveyTemplateSectionInputId: {
        value: input.id.value,
      },
      value: input.value as string,
    })),
  }));

  return mappedRecord;
};