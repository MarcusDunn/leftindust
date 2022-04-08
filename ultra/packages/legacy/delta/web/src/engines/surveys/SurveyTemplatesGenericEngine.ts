import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import type { BasicSurveyTemplateFragment } from '../../requests/fragments';
import type { SurveysQueryResult } from '../../requests/queries';

import type { RangeInput } from '../../schemas/leftindust.schema';

import { client } from '../../server';

import surveyTemplatesQuery from '../../requests/queries/surveyTemplatesQuery.graphql';
import { writable } from 'svelte/store';
import { getTrigger } from '../../modules/TriggerModule';

export type SurveyTemplatesGenericEngineVariables = {
  range: RangeInput;
};

export type SurveyTemplatesGenericEngine = {
  request: ReadableQuery<SurveysQueryResult>;
  surveys: Writable<BasicSurveyTemplateFragment[]>;
  fetchMore: (allowInfinite: boolean, newVariables: RangeInput) => boolean | void;
};

const SurveyTemplatesGenericEngine = (
  variables: SurveyTemplatesGenericEngineVariables,
): SurveyTemplatesGenericEngine => {
  const surveys = writable<BasicSurveyTemplateFragment[]>();

  const request = client.query<SurveysQueryResult, SurveyTemplatesGenericEngineVariables>(
    surveyTemplatesQuery,
    {
      variables,
    },
  );

  const fetchMore: SurveyTemplatesGenericEngine['fetchMore'] = (allowInfinite, newVariables) => {
    if (!allowInfinite) return;
    allowInfinite = false;

    void request
      .fetchMore({
        variables: newVariables,
        updateQuery: (previousResult: SurveysQueryResult,
          { fetchMoreResult }: { fetchMoreResult?: SurveysQueryResult }) => {
          // Return previous value if no new values
          if (!fetchMoreResult) return previousResult;

          // Merge the new patients with the old patients
          return {
            ...previousResult,
            patients: [...previousResult.surveys, ...fetchMoreResult.surveys],
          };
        },
      })
      .then(() => {
        console.log('Fetched more surveys');
      });

    return allowInfinite;
  };

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
    fetchMore,
  };
};

export default SurveyTemplatesGenericEngine;
