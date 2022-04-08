import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import type { SurveyTemplateQueryResult } from '../../requests/queries';
import type { FixedLengthArray } from '../../types/helpers';

import type { FormTemplateIdInput } from '../../schemas/leftindust.schema';

import { client } from '../../server';

import surveyTemplateQuery from '../../requests/queries/surveyTemplateQuery.graphql';
import { writable } from 'svelte/store';
import { getTrigger } from '../../modules/TriggerModule';

export type SurveyTemplateEngineVariables = {
  surveys: FixedLengthArray<FormTemplateIdInput[]>;
};

export type SurveyTemplateEngine = {
  request: ReadableQuery<SurveyTemplateQueryResult>;
  survey: Writable<SurveyTemplateQueryResult['surveys'][0]>;
};

const SurveyTemplateEngine = (
  variables: SurveyTemplateEngineVariables,
): SurveyTemplateEngine => {
  const survey = writable<SurveyTemplateQueryResult['surveys'][0]>();

  const request = client.query<SurveyTemplateQueryResult, SurveyTemplateEngineVariables>(
    surveyTemplateQuery,
    {
      variables,
    },
  );

  request.subscribe(({ data }) => {
    if (data?.surveys)
      survey.set(data.surveys[0]);
  });

  getTrigger('refreshSurveys').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    survey,
  };
};

export default SurveyTemplateEngine;
