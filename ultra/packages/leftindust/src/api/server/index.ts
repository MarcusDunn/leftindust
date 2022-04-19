import type {
  NamesFragment,
  BasicUserFragment,
  BasicDoctorFragment,
  BasicPatientFragment,
  PatientInsuranceFragment,
  BasicEventFragment,
  BasicIcdFragment,
  BasicVisitFragment,
  BasicRecordFragment,
  BasicSurveyTemplateFragment,
  ContactsFragment,
  BasicFirebaseUserFragment,
  BasicGroupFragment,
  Fragments,
} from './requests/fragments';

import type {
  UserQueryResult,
  UsersQueryResult,
  PatientsQueryResult,
  DoctorsQueryResult,
  EventsQueryResult,
  VisitsQueryResult,
  PatientInsuranceQueryResult,
  FirebaseUsersQueryResult,
  PatientContactsQueryResult,
  PatientQueryResult,
  DoctorQueryResult,
  EventQueryResult,
  VisitQueryResult,
  DoctorEventsQueryResult,
  PatientEventsQueryResult,
  PatientDoctorsQueryResult,
  DoctorPatientsQueryResult,
  PatientVisitsQueryResult,
  RecordsQueryResult,
  IcdSearchQueryResult,
  IcdQueryResult,
  SurveysQueryResult,
  SurveyTemplateQueryResult,
  PatientSurveyQueryResult,
  IntrospectionQueryResult,
  GroupsQueryResult,
} from './requests/queries';
import type { Person, Document, DataType } from './requests';

import { defaultRangeInput } from './requests';

import { initializeApp } from 'firebase/app';
import { getDatabase } from 'firebase/database';
import 'firebase/auth';

import config from './config.json';

import { InMemoryCache } from '@apollo/client';
import { SvelteApolloClient } from 'svelte-apollo-client';
import { getAuth } from 'firebase/auth';

export type Data<T = DataType> = {
  type: T;
  id: string;
};

import { ApolloLink, HttpLink } from '@apollo/client';

import type { OperationDefinitionNode } from 'graphql';

import { onError } from '@apollo/client/link/error';
import { setContext } from '@apollo/client/link/context';
import ApolloLinkTimeout from './clients/link/timeout';

import { getMainDefinition } from '@apollo/client/utilities';

// Initialize Firebase
const firebase = initializeApp(config.firebase);

const auth = getAuth(firebase);

const httpLink = new HttpLink({
  uri: `${config.leftindust.address}:${config.leftindust.port}/graphql`,
  fetch,
});

const errorLink = onError(({ graphQLErrors, networkError }) => {
  if (graphQLErrors) {
    console.log('graphQLErrors', graphQLErrors);
  }
  if (networkError) {
    console.log('networkError', networkError);
  }
});

const timeoutLink = new ApolloLinkTimeout(10000);

const withToken = setContext(async () => {
  // Get firebase token and return it
  const user = auth.currentUser;
  const token = await user?.getIdToken();
  console.log(token);
  return { token };
});

const authMiddleware = new ApolloLink((operation, forward) => {
  // Receive async token and add it to our header
  const { token } = operation.getContext();
  operation.setContext(() => ({
    headers: { 'mediq-auth-token': token },
  }));
  return forward(operation);
});

// To omit __typename from all mutations
const cleanTypenameLink = new ApolloLink((operation, forward) => {
  const omitTypename = (key: string, value: unknown) => (key === '__typename' ? undefined : value);

  const def = getMainDefinition(operation.query);
  if (def && (<OperationDefinitionNode>def).operation === 'mutation') {
    operation.variables = JSON.parse(JSON.stringify(operation.variables), omitTypename);
  }
  return forward ? forward(operation) : null;
});

// Complete finishing up the client
const leftindust = ApolloLink.from([
  withToken,
  errorLink,
  timeoutLink,
  authMiddleware.concat(cleanTypenameLink.concat(httpLink)),
]);

// Initialize realtime db
const realtime = getDatabase(firebase);

// Setup client
const client = SvelteApolloClient({
  link: leftindust,
  cache: new InMemoryCache({
    addTypename: true,
  }),
});

export type {
  NamesFragment,
  BasicUserFragment,
  BasicDoctorFragment,
  BasicPatientFragment,
  PatientInsuranceFragment,
  BasicEventFragment,
  BasicIcdFragment,
  BasicVisitFragment,
  BasicRecordFragment,
  BasicSurveyTemplateFragment,
  ContactsFragment,
  BasicFirebaseUserFragment,
  BasicGroupFragment,
  Fragments,
  UserQueryResult,
  UsersQueryResult,
  PatientsQueryResult,
  DoctorsQueryResult,
  EventsQueryResult,
  VisitsQueryResult,
  PatientInsuranceQueryResult,
  FirebaseUsersQueryResult,
  PatientContactsQueryResult,
  PatientQueryResult,
  DoctorQueryResult,
  EventQueryResult,
  VisitQueryResult,
  DoctorEventsQueryResult,
  PatientEventsQueryResult,
  PatientDoctorsQueryResult,
  DoctorPatientsQueryResult,
  PatientVisitsQueryResult,
  RecordsQueryResult,
  IcdSearchQueryResult,
  IcdQueryResult,
  SurveysQueryResult,
  SurveyTemplateQueryResult,
  PatientSurveyQueryResult,
  IntrospectionQueryResult,
  GroupsQueryResult,
  Person,
  Document,
};

export {
  defaultRangeInput,
  client,
  firebase,
  realtime,
  auth,
};