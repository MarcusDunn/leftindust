import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import type { BasicSurveyTemplateFragment } from '../../requests/fragments';
import type { SurveysQueryResult } from '../../requests/queries';

import type { FormTemplateIdInput } from '../../schemas/leftindust.schema';

import { client } from '../../server';

import surveyTemplatesQuery from '../../requests/queries/surveyTemplatesQuery.graphql';
import { writable } from 'svelte/store';
import { getTrigger } from '../../modules/TriggerModule';

export type SurveyTemplatesSpecificEngineVariables = {
  surveys: FormTemplateIdInput[];
};

export type SurveyTemplatesSpecificEngine = {
  request: ReadableQuery<SurveysQueryResult>;
  surveys: Writable<BasicSurveyTemplateFragment[]>;
};

const SurveyTemplatesSpecificEngine = (
  variables: SurveyTemplatesSpecificEngineVariables,
): SurveyTemplatesSpecificEngine => {
  const surveys = writable<BasicSurveyTemplateFragment[]>([]);

  const request = client.query<SurveysQueryResult, SurveyTemplatesSpecificEngineVariables>(
    surveyTemplatesQuery,
    {
      variables,
    },
  );

  request.subscribe(({ data }) => {
    if (data?.surveys)
      surveys.set(data.surveys);
  });

  getTrigger('refreshSurveys').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    surveys,
  };
};

export default SurveyTemplatesSpecificEngine;
