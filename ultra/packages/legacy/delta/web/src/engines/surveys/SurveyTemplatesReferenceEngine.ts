import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';

import type { Selectable } from '../../modules/SelectModule';
import type { DocumentNode } from 'graphql';

import type { PatientSurveyQueryResult } from '../../requests/queries';
import type { Patient } from '../../schemas/leftindust.schema';

import { writable } from 'svelte/store';

import { getTrigger } from '../../modules/TriggerModule';
import { client } from '../../server';

import patientSurveysQuery from '../../requests/queries/patientSurveysQuery.graphql';

export type SurveyTemplatesReferencedEngineRequest = PatientSurveyQueryResult;

export type SurveyTemplatesReferencedEngine = {
  request: ReadableQuery<SurveyTemplatesReferencedEngineRequest>;
  surveys: Writable<PatientSurveyQueryResult['patients'][0]['assignedForms']>;
};

export type SurveyTemplatesReferencedEngineVariables = {
  selectable: Selectable;
};

export type SurveyTemplatesReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const SurveyTemplatesReferencedEngine = (variables: SurveyTemplatesReferencedEngineVariables): SurveyTemplatesReferencedEngine => {
  const { selectable } = variables;

  const surveys: SurveyTemplatesReferencedEngine['surveys'] = writable([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: SurveyTemplatesReferencedEngineRequestVariables;

  switch (selectable.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: selectable.id }],
      };
      key = 'patients';
      query = patientSurveysQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${selectable.type}" was passed into the 'referenced' visits engine`,
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
