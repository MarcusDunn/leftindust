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

import firebase from 'firebase/app';
import 'firebase/auth';

import leftindust from './clients/leftindust';
import config from './config.json';

import { InMemoryCache } from '@apollo/client';
import { SvelteApolloClient } from 'svelte-apollo-client';

export type Data<T = DataType> = {
  type: T;
  id: string;
};

// Initialize Firebase
firebase.initializeApp(config.firebase);

// Initialize realtime db
const realtime = firebase.database();

const client = SvelteApolloClient({
  link: leftindust,
  cache: new InMemoryCache({
    addTypename: true,
  }),
});

export {
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
  Comparable,
  defaultRangeInput,
  client,
  realtime,
};