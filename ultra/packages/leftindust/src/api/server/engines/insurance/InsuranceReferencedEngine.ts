import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import { writable } from 'svelte/store';

import type { Data } from '../..';
import type { DocumentNode } from 'graphql';

import type { PatientInsuranceQueryResult } from '../../requests/queries';
import type { Patient } from '../../schema/leftindust.schema';

import { client } from '../..';

import patientInsuranceQuery from '../../requests/queries/patientInsuranceQuery.graphql';

export type InsuranceReferencedEngineResult = PatientInsuranceQueryResult;

export type InsuranceReferencedEngine = {
  request: ReadableQuery<InsuranceReferencedEngineResult>;
  insuranceNumber: Writable<InsuranceReferencedEngineResult['patients'][0]['insuranceNumber']>;
};

export type InsuranceReferencedEngineVariables = {
  data: Data;
};

export type InsuranceReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const InsuranceReferencedEngine = (variables: InsuranceReferencedEngineVariables): InsuranceReferencedEngine => {
  const { data } = variables;

  const insuranceNumber = writable<InsuranceReferencedEngineResult['patients'][0]['insuranceNumber']>();

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: InsuranceReferencedEngineRequestVariables;

  switch (data.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: data.id }],
      };
      key = 'patients';
      query = patientInsuranceQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${data.type}" was passed into the 'referenced' insurance engine`,
      );
  }

  const request = client.query<InsuranceReferencedEngineResult, InsuranceReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    insuranceNumber.set(data?.[key]?.[0]?.insuranceNumber);
  });

  return {
    request,
    insuranceNumber,
  };
};

export default InsuranceReferencedEngine;
