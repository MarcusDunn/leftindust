import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Data } from '../..';
import type { DocumentNode } from 'graphql';

import type { PatientSurveyQueryResult } from '../../requests/queries';
import type { Patient } from '../../schema/leftindust.schema';

import { writable } from 'svelte/store';

import { getTrigger } from '@/features/Triggers';
import { client } from '../..';

import patientSurveysQuery from '../../requests/queries/patientSurveysQuery.graphql';

export type SurveyTemplatesReferencedEngineRequest = PatientSurveyQueryResult;

export type SurveyTemplatesReferencedEngine = {
  request: ReadableQuery<SurveyTemplatesReferencedEngineRequest>;
  surveys: Writable<PatientSurveyQueryResult['patients'][0]['assignedForms']>;
};

export type SurveyTemplatesReferencedEngineVariables = {
  data: Data;
};

export type SurveyTemplatesReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const SurveyTemplatesReferencedEngine = (variables: SurveyTemplatesReferencedEngineVariables): SurveyTemplatesReferencedEngine => {
  const { data } = variables;

  const surveys: SurveyTemplatesReferencedEngine['surveys'] = writable([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: SurveyTemplatesReferencedEngineRequestVariables;

  switch (data.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: data.id }],
      };
      key = 'patients';
      query = patientSurveysQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${data.type}" was passed into the 'referenced' visits engine`,
      );
  }

  const request = client.query<SurveyTemplatesReferencedEngineRequest, SurveyTemplatesReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    surveys.set(data?.[key]?.[0]?.assignedForms ?? []);
  });

  getTrigger('refreshSurveys').subscribe(() => {
    request?.refetch();
  });

  return {
    request,
    surveys,
  };
};

export default SurveyTemplatesReferencedEngine;
