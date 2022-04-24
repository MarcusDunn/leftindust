/* eslint-disable @typescript-eslint/ban-types */
import type * as Schema from '../schema/leftindust.schema';

import type { GraphQLResolveInfo, GraphQLScalarType, GraphQLScalarTypeConfig } from 'graphql';
import type { OperationStore } from '@urql/svelte';
import type { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type RequireFields<T, K extends keyof T> = Omit<T, K> & { [P in K]-?: NonNullable<T[P]> };
export type AddressFragmentFragment = (
  { __typename?: 'Address' }
  & Pick<Schema.Address, 'addressType' | 'address' | 'city' | 'country' | 'postalCode' | 'province'>
);

export type ContactFragmentFragment = (
  { __typename?: 'EmergencyContact' }
  & Pick<Schema.EmergencyContact, 'firstName' | 'middleName' | 'lastName' | 'relationship' | 'thumbnail'>
  & { phones: Array<(
    { __typename?: 'Phone' }
    & PhoneFragmentFragment
  )>, emails: Array<(
    { __typename?: 'Email' }
    & EmailFragmentFragment
  )> }
);

export type DoctorFragmentFragment = (
  { __typename: 'Doctor' }
  & Pick<Schema.Doctor, 'firstName' | 'middleName' | 'lastName'>
  & { addresses: Array<(
    { __typename?: 'Address' }
    & AddressFragmentFragment
  )>, phones: Array<(
    { __typename?: 'Phone' }
    & PhoneFragmentFragment
  )>, emails: Array<(
    { __typename?: 'Email' }
    & EmailFragmentFragment
  )>, user: Schema.Maybe<(
    { __typename?: 'User' }
    & UsersFragmentFragment
  )>, patients: Array<(
    { __typename?: 'Patient' }
    & PatientsFragmentFragment
  )> }
  & DoctorsFragmentFragment
);

export type DoctorsFragmentFragment = (
  { __typename?: 'Doctor' }
  & Pick<Schema.Doctor, 'title'>
  & { did: (
    { __typename?: 'DoctorId' }
    & Pick<Schema.DoctorId, 'id'>
  ), emails: Array<(
    { __typename?: 'Email' }
    & EmailFragmentFragment
  )> }
);

export type EmailFragmentFragment = (
  { __typename?: 'Email' }
  & Pick<Schema.Email, 'type' | 'email'>
);

export type FirebaseUsersFragmentFragment = (
  { __typename?: 'FirebaseInfo' }
  & Pick<Schema.FirebaseInfo, 'email' | 'uid'>
);

export type GroupFragmentFragment = (
  { __typename?: 'Group' }
  & Pick<Schema.Group, 'name'>
  & { gid: (
    { __typename?: 'GroupId' }
    & Pick<Schema.GroupId, 'id'>
  ) }
);

type IcdFragment_IcdLinearizationEntity_Fragment = (
  { __typename?: 'IcdLinearizationEntity' }
  & Pick<Schema.IcdLinearizationEntity, 'id' | 'title' | 'code' | 'description'>
);

type IcdFragment_IcdSimpleEntity_Fragment = (
  { __typename?: 'IcdSimpleEntity' }
  & Pick<Schema.IcdSimpleEntity, 'id' | 'title' | 'code' | 'description'>
);

export type IcdFragmentFragment = IcdFragment_IcdLinearizationEntity_Fragment | IcdFragment_IcdSimpleEntity_Fragment;

export type NamesFragmentFragment = (
  { __typename?: 'NameInfo' }
  & Pick<Schema.NameInfo, 'firstName' | 'middleName' | 'lastName'>
);

export type PatientFragmentFragment = (
  { __typename?: 'Patient' }
  & Pick<Schema.Patient, 'insuranceNumber' | 'ethnicity'>
  & { addresses: Array<(
    { __typename?: 'Address' }
    & AddressFragmentFragment
  )>, phones: Array<(
    { __typename?: 'Phone' }
    & PhoneFragmentFragment
  )>, emails: Array<(
    { __typename?: 'Email' }
    & EmailFragmentFragment
  )>, doctors: Array<(
    { __typename?: 'Doctor' }
    & DoctorsFragmentFragment
  )> }
  & PatientsFragmentFragment
);

export type PatientsFragmentFragment = (
  { __typename?: 'Patient' }
  & Pick<Schema.Patient, 'firstName' | 'middleName' | 'lastName' | 'sex' | 'gender'>
  & { pid: (
    { __typename?: 'PatientId' }
    & Pick<Schema.PatientId, 'id'>
  ), dateOfBirth: (
    { __typename?: 'Date' }
    & Pick<Schema.Date, 'day' | 'month' | 'year'>
  ), emails: Array<(
    { __typename?: 'Email' }
    & Pick<Schema.Email, 'type' | 'email'>
  )> }
);

export type PhoneFragmentFragment = (
  { __typename?: 'Phone' }
  & Pick<Schema.Phone, 'type' | 'number'>
);

export type UserFragmentFragment = (
  { __typename?: 'User' }
  & { doctor: Schema.Maybe<(
    { __typename?: 'Doctor' }
    & DoctorsFragmentFragment
  )>, patient: Schema.Maybe<(
    { __typename?: 'Patient' }
    & PatientsFragmentFragment
  )> }
  & UsersFragmentFragment
);

export type UsersFragmentFragment = (
  { __typename?: 'User' }
  & Pick<Schema.User, 'uid' | 'isRegistered'>
  & { group: Schema.Maybe<(
    { __typename?: 'Group' }
    & Pick<Schema.Group, 'name'>
  )>, firebaseUserInfo: (
    { __typename?: 'FirebaseInfo' }
    & Pick<Schema.FirebaseInfo, 'email'>
  ), names: Schema.Maybe<(
    { __typename?: 'NameInfo' }
    & NamesFragmentFragment
  )> }
);

export type DoctorEditMutationMutationVariables = Schema.Exact<{
  doctor: Schema.DoctorEditInput;
}>;


export type DoctorEditMutationMutation = (
  { __typename?: 'Mutation' }
  & { editDoctor: (
    { __typename?: 'Doctor' }
    & DoctorFragmentFragment
  ) }
);

export type DoctorMutationMutationVariables = Schema.Exact<{
  doctor: Schema.DoctorInput;
}>;


export type DoctorMutationMutation = (
  { __typename?: 'Mutation' }
  & { addDoctor: (
    { __typename?: 'Doctor' }
    & DoctorFragmentFragment
  ) }
);

export type PatientEditMutationMutationVariables = Schema.Exact<{
  patient: Schema.PatientEditInput;
}>;


export type PatientEditMutationMutation = (
  { __typename?: 'Mutation' }
  & { updatePatient: (
    { __typename?: 'Patient' }
    & PatientFragmentFragment
  ) }
);

export type PatientMutationMutationVariables = Schema.Exact<{
  patient: Schema.PatientInput;
}>;


export type PatientMutationMutation = (
  { __typename?: 'Mutation' }
  & { addPatient: (
    { __typename?: 'Patient' }
    & PatientFragmentFragment
  ) }
);

export type UserEditMutationMutationVariables = Schema.Exact<{
  user: Schema.UserEditInput;
}>;


export type UserEditMutationMutation = (
  { __typename?: 'Mutation' }
  & { editUser: (
    { __typename?: 'User' }
    & UserFragmentFragment
  ) }
);

export type UserMutationMutationVariables = Schema.Exact<{
  user: Schema.UserInput;
}>;


export type UserMutationMutation = (
  { __typename?: 'Mutation' }
  & { addUser: (
    { __typename?: 'User' }
    & UserFragmentFragment
  ) }
);

export type DoctorQueryQueryVariables = Schema.Exact<{
  dids?: Schema.Maybe<Array<Schema.DoctorIdInput> | Schema.DoctorIdInput>;
}>;


export type DoctorQueryQuery = (
  { __typename?: 'Query' }
  & { doctors: Array<(
    { __typename?: 'Doctor' }
    & DoctorFragmentFragment
  )> }
);

export type DoctorsQueryQueryVariables = Schema.Exact<{
  range?: Schema.Maybe<Schema.RangeInput>;
  dids?: Schema.Maybe<Array<Schema.DoctorIdInput> | Schema.DoctorIdInput>;
  example?: Schema.Maybe<Schema.DoctorExampleInput>;
}>;


export type DoctorsQueryQuery = (
  { __typename?: 'Query' }
  & { doctors: Array<(
    { __typename?: 'Doctor' }
    & DoctorsFragmentFragment
  )> }
);

export type GroupsQueryQueryVariables = Schema.Exact<{
  gids?: Schema.Maybe<Array<Schema.GroupIdInput> | Schema.GroupIdInput>;
  range?: Schema.Maybe<Schema.RangeInput>;
}>;


export type GroupsQueryQuery = (
  { __typename?: 'Query' }
  & { groups: Array<(
    { __typename?: 'Group' }
    & GroupFragmentFragment
  )> }
);

export type PatientQueryQueryVariables = Schema.Exact<{
  pids?: Schema.Maybe<Array<Schema.PatientIdInput> | Schema.PatientIdInput>;
}>;


export type PatientQueryQuery = (
  { __typename?: 'Query' }
  & { patients: Array<(
    { __typename?: 'Patient' }
    & PatientFragmentFragment
  )> }
);

export type PatientsQueryQueryVariables = Schema.Exact<{
  range?: Schema.Maybe<Schema.RangeInput>;
  pids?: Schema.Maybe<Array<Schema.PatientIdInput> | Schema.PatientIdInput>;
  sortedBy?: Schema.Maybe<Schema.SortableField>;
  example?: Schema.Maybe<Schema.PatientExampleInput>;
}>;


export type PatientsQueryQuery = (
  { __typename?: 'Query' }
  & { patients: Array<(
    { __typename?: 'Patient' }
    & PatientsFragmentFragment
  )> }
);

export type UserQueryQueryVariables = Schema.Exact<{
  uid: Schema.Scalars['ID'];
}>;


export type UserQueryQuery = (
  { __typename?: 'Query' }
  & { user: (
    { __typename?: 'User' }
    & UserFragmentFragment
  ) }
);

export type UsersQueryQueryVariables = Schema.Exact<{
  range?: Schema.Maybe<Schema.RangeInput>;
}>;


export type UsersQueryQuery = (
  { __typename?: 'Query' }
  & { users: Array<(
    { __typename?: 'User' }
    & UsersFragmentFragment
  )> }
);



export type ResolverTypeWrapper<T> = Promise<T> | T;


export type ResolverWithResolve<TResult, TParent, TContext, TArgs> = {
  resolve: ResolverFn<TResult, TParent, TContext, TArgs>;
};
export type Resolver<TResult, TParent = {}, TContext = {}, TArgs = {}> = ResolverFn<TResult, TParent, TContext, TArgs> | ResolverWithResolve<TResult, TParent, TContext, TArgs>;

export type ResolverFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => Promise<TResult> | TResult;

export type SubscriptionSubscribeFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => AsyncIterable<TResult> | Promise<AsyncIterable<TResult>>;

export type SubscriptionResolveFn<TResult, TParent, TContext, TArgs> = (
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => TResult | Promise<TResult>;

export interface SubscriptionSubscriberObject<TResult, TKey extends string, TParent, TContext, TArgs> {
  subscribe: SubscriptionSubscribeFn<{ [key in TKey]: TResult }, TParent, TContext, TArgs>;
  resolve?: SubscriptionResolveFn<TResult, { [key in TKey]: TResult }, TContext, TArgs>;
}

export interface SubscriptionResolverObject<TResult, TParent, TContext, TArgs> {
  subscribe: SubscriptionSubscribeFn<any, TParent, TContext, TArgs>;
  resolve: SubscriptionResolveFn<TResult, any, TContext, TArgs>;
}

export type SubscriptionObject<TResult, TKey extends string, TParent, TContext, TArgs> =
  | SubscriptionSubscriberObject<TResult, TKey, TParent, TContext, TArgs>
  | SubscriptionResolverObject<TResult, TParent, TContext, TArgs>;

export type SubscriptionResolver<TResult, TKey extends string, TParent = {}, TContext = {}, TArgs = {}> =
  | ((...args: any[]) => SubscriptionObject<TResult, TKey, TParent, TContext, TArgs>)
  | SubscriptionObject<TResult, TKey, TParent, TContext, TArgs>;

export type TypeResolveFn<TTypes, TParent = {}, TContext = {}> = (
  parent: TParent,
  context: TContext,
  info: GraphQLResolveInfo
) => Schema.Maybe<TTypes> | Promise<Schema.Maybe<TTypes>>;

export type IsTypeOfResolverFn<T = {}, TContext = {}> = (obj: T, context: TContext, info: GraphQLResolveInfo) => boolean | Promise<boolean>;

export type NextResolverFn<T> = () => Promise<T>;

export type DirectiveResolverFn<TResult = {}, TParent = {}, TContext = {}, TArgs = {}> = (
  next: NextResolverFn<TResult>,
  parent: TParent,
  args: TArgs,
  context: TContext,
  info: GraphQLResolveInfo
) => TResult | Promise<TResult>;

/** Mapping between all available schema types and the resolvers types */
export type ResolversTypes = {
  Address: ResolverTypeWrapper<Schema.Address>;
  String: ResolverTypeWrapper<Schema.Scalars['String']>;
  AddressEditInput: Schema.AddressEditInput;
  AddressInput: Schema.AddressInput;
  AddressType: Schema.AddressType;
  AssignedSurvey: ResolverTypeWrapper<Schema.AssignedSurvey>;
  AssignedSurveyId: ResolverTypeWrapper<Schema.AssignedSurveyId>;
  CanadianProvince: ResolverTypeWrapper<Schema.CanadianProvince>;
  CaseAgnosticStringFilterInput: Schema.CaseAgnosticStringFilterInput;
  Boolean: ResolverTypeWrapper<Schema.Scalars['Boolean']>;
  Clinic: ResolverTypeWrapper<Schema.Clinic>;
  ClinicEditInput: Schema.ClinicEditInput;
  ClinicId: ResolverTypeWrapper<Schema.ClinicId>;
  ClinicIdInput: Schema.ClinicIdInput;
  ClinicInput: Schema.ClinicInput;
  ConvertTarget: Schema.ConvertTarget;
  Country: Schema.Country;
  Crud: Schema.Crud;
  DataType: Schema.DataType;
  Date: ResolverTypeWrapper<Schema.Date>;
  Int: ResolverTypeWrapper<Schema.Scalars['Int']>;
  DateFilterInput: Schema.DateFilterInput;
  DateInput: Schema.DateInput;
  DayOfWeek: Schema.DayOfWeek;
  Doctor: ResolverTypeWrapper<Schema.Doctor>;
  DoctorEditInput: Schema.DoctorEditInput;
  DoctorExampleInput: Schema.DoctorExampleInput;
  DoctorId: ResolverTypeWrapper<Schema.DoctorId>;
  DoctorIdInput: Schema.DoctorIdInput;
  DoctorInput: Schema.DoctorInput;
  Email: ResolverTypeWrapper<Schema.Email>;
  EmailInput: Schema.EmailInput;
  EmailType: Schema.EmailType;
  EmergencyContact: ResolverTypeWrapper<Schema.EmergencyContact>;
  EmergencyContactInput: Schema.EmergencyContactInput;
  Ethnicity: Schema.Ethnicity;
  Event: ResolverTypeWrapper<Schema.Event>;
  EventEditInput: Schema.EventEditInput;
  EventId: ResolverTypeWrapper<Schema.EventId>;
  EventIdInput: Schema.EventIdInput;
  EventInput: Schema.EventInput;
  FirebaseInfo: ResolverTypeWrapper<Schema.FirebaseInfo>;
  FormFieldInput: Schema.FormFieldInput;
  Float: ResolverTypeWrapper<Schema.Scalars['Float']>;
  FormSectionInput: Schema.FormSectionInput;
  FormTemplateId: ResolverTypeWrapper<Schema.FormTemplateId>;
  FormTemplateIdInput: Schema.FormTemplateIdInput;
  FormTemplateInput: Schema.FormTemplateInput;
  FoundationIcdCodeInput: Schema.FoundationIcdCodeInput;
  GraphQLFormData: ResolverTypeWrapper<Schema.GraphQlFormData>;
  GraphQLFormSection: ResolverTypeWrapper<Schema.GraphQlFormSection>;
  GraphQLFormTemplate: ResolverTypeWrapper<Schema.GraphQlFormTemplate>;
  GraphQLPermissionInput: Schema.GraphQlPermissionInput;
  GraphQLProvince: ResolversTypes['CanadianProvince'];
  GraphQLRecordInput: Schema.GraphQlRecordInput;
  GraphQLVisitEditInput: Schema.GraphQlVisitEditInput;
  GraphQlFormField: ResolverTypeWrapper<Schema.GraphQlFormField>;
  Group: ResolverTypeWrapper<Schema.Group>;
  GroupId: ResolverTypeWrapper<Schema.GroupId>;
  GroupIdInput: Schema.GroupIdInput;
  GroupInput: Schema.GroupInput;
  IcdEntityType: Schema.IcdEntityType;
  IcdFoundationEntity: ResolverTypeWrapper<Schema.IcdFoundationEntity>;
  IcdGuessType: Schema.IcdGuessType;
  IcdGuessWord: ResolverTypeWrapper<Schema.IcdGuessWord>;
  IcdLanguageSpecificText: ResolverTypeWrapper<Schema.IcdLanguageSpecificText>;
  IcdLinearizationEntity: ResolverTypeWrapper<Schema.IcdLinearizationEntity>;
  IcdListFilterInput: Schema.IcdListFilterInput;
  IcdPostCoordinationScaleInfo: ResolverTypeWrapper<Schema.IcdPostCoordinationScaleInfo>;
  IcdPostcoordinationAvailability: Schema.IcdPostcoordinationAvailability;
  IcdReallySimpleEntity: ResolversTypes['IcdLinearizationEntity'] | ResolversTypes['IcdSimpleEntity'];
  IcdSearchResult: ResolverTypeWrapper<Schema.IcdSearchResult>;
  IcdSimpleEntity: ResolverTypeWrapper<Schema.IcdSimpleEntity>;
  IcdSimplePropertyValue: ResolverTypeWrapper<Schema.IcdSimplePropertyValue>;
  IcdTerm: ResolverTypeWrapper<Schema.IcdTerm>;
  Long: ResolverTypeWrapper<Schema.Scalars['Long']>;
  Month: Schema.Month;
  Mutation: ResolverTypeWrapper<{}>;
  NameEditInput: Schema.NameEditInput;
  NameInfo: ResolverTypeWrapper<Schema.NameInfo>;
  NameInput: Schema.NameInput;
  Patient: ResolverTypeWrapper<Schema.Patient>;
  PatientEditInput: Schema.PatientEditInput;
  ID: ResolverTypeWrapper<Schema.Scalars['ID']>;
  PatientExampleInput: Schema.PatientExampleInput;
  PatientId: ResolverTypeWrapper<Schema.PatientId>;
  PatientIdInput: Schema.PatientIdInput;
  PatientInput: Schema.PatientInput;
  Permission: ResolverTypeWrapper<Schema.Permission>;
  PermissionId: ResolverTypeWrapper<Schema.PermissionId>;
  Permissions: ResolverTypeWrapper<Schema.Permissions>;
  Person: ResolversTypes['Doctor'] | ResolversTypes['EmergencyContact'] | ResolversTypes['Patient'];
  Phone: ResolverTypeWrapper<Schema.Phone>;
  PhoneInput: Schema.PhoneInput;
  PhoneType: Schema.PhoneType;
  Query: ResolverTypeWrapper<{}>;
  RangeInput: Schema.RangeInput;
  Record: ResolverTypeWrapper<Schema.Record>;
  RecordId: ResolverTypeWrapper<Schema.RecordId>;
  RecordIdInput: Schema.RecordIdInput;
  RecordType: Schema.RecordType;
  Recurrence: ResolverTypeWrapper<Schema.Recurrence>;
  RecurrenceEditSettingsInput: Schema.RecurrenceEditSettingsInput;
  RecurrenceInput: Schema.RecurrenceInput;
  Relationship: Schema.Relationship;
  Sex: Schema.Sex;
  SortableField: Schema.SortableField;
  Tables: Schema.Tables;
  TimeRangeInput: Schema.TimeRangeInput;
  TimeZonedTime: ResolverTypeWrapper<Schema.TimeZonedTime>;
  UUID: ResolverTypeWrapper<Schema.Scalars['UUID']>;
  User: ResolverTypeWrapper<Schema.User>;
  UserEditInput: Schema.UserEditInput;
  UserInput: Schema.UserInput;
  UtcTime: ResolverTypeWrapper<Schema.UtcTime>;
  UtcTimeInput: Schema.UtcTimeInput;
  Visit: ResolverTypeWrapper<Schema.Visit>;
  VisitId: ResolverTypeWrapper<Schema.VisitId>;
  VisitIdInput: Schema.VisitIdInput;
  VisitInput: Schema.VisitInput;
  WhiteSpaceAgnosticStringFilterInput: Schema.WhiteSpaceAgnosticStringFilterInput;
  _FieldSet: ResolverTypeWrapper<Schema.Scalars['_FieldSet']>;
  _Service: ResolverTypeWrapper<Schema._Service>;
};

/** Mapping between all available schema types and the resolvers parents */
export type ResolversParentTypes = {
  Address: Schema.Address;
  String: Schema.Scalars['String'];
  AddressEditInput: Schema.AddressEditInput;
  AddressInput: Schema.AddressInput;
  AssignedSurvey: Schema.AssignedSurvey;
  AssignedSurveyId: Schema.AssignedSurveyId;
  CanadianProvince: Schema.CanadianProvince;
  CaseAgnosticStringFilterInput: Schema.CaseAgnosticStringFilterInput;
  Boolean: Schema.Scalars['Boolean'];
  Clinic: Schema.Clinic;
  ClinicEditInput: Schema.ClinicEditInput;
  ClinicId: Schema.ClinicId;
  ClinicIdInput: Schema.ClinicIdInput;
  ClinicInput: Schema.ClinicInput;
  Date: Schema.Date;
  Int: Schema.Scalars['Int'];
  DateFilterInput: Schema.DateFilterInput;
  DateInput: Schema.DateInput;
  Doctor: Schema.Doctor;
  DoctorEditInput: Schema.DoctorEditInput;
  DoctorExampleInput: Schema.DoctorExampleInput;
  DoctorId: Schema.DoctorId;
  DoctorIdInput: Schema.DoctorIdInput;
  DoctorInput: Schema.DoctorInput;
  Email: Schema.Email;
  EmailInput: Schema.EmailInput;
  EmergencyContact: Schema.EmergencyContact;
  EmergencyContactInput: Schema.EmergencyContactInput;
  Event: Schema.Event;
  EventEditInput: Schema.EventEditInput;
  EventId: Schema.EventId;
  EventIdInput: Schema.EventIdInput;
  EventInput: Schema.EventInput;
  FirebaseInfo: Schema.FirebaseInfo;
  FormFieldInput: Schema.FormFieldInput;
  Float: Schema.Scalars['Float'];
  FormSectionInput: Schema.FormSectionInput;
  FormTemplateId: Schema.FormTemplateId;
  FormTemplateIdInput: Schema.FormTemplateIdInput;
  FormTemplateInput: Schema.FormTemplateInput;
  FoundationIcdCodeInput: Schema.FoundationIcdCodeInput;
  GraphQLFormData: Schema.GraphQlFormData;
  GraphQLFormSection: Schema.GraphQlFormSection;
  GraphQLFormTemplate: Schema.GraphQlFormTemplate;
  GraphQLPermissionInput: Schema.GraphQlPermissionInput;
  GraphQLProvince: ResolversParentTypes['CanadianProvince'];
  GraphQLRecordInput: Schema.GraphQlRecordInput;
  GraphQLVisitEditInput: Schema.GraphQlVisitEditInput;
  GraphQlFormField: Schema.GraphQlFormField;
  Group: Schema.Group;
  GroupId: Schema.GroupId;
  GroupIdInput: Schema.GroupIdInput;
  GroupInput: Schema.GroupInput;
  IcdFoundationEntity: Schema.IcdFoundationEntity;
  IcdGuessWord: Schema.IcdGuessWord;
  IcdLanguageSpecificText: Schema.IcdLanguageSpecificText;
  IcdLinearizationEntity: Schema.IcdLinearizationEntity;
  IcdListFilterInput: Schema.IcdListFilterInput;
  IcdPostCoordinationScaleInfo: Schema.IcdPostCoordinationScaleInfo;
  IcdReallySimpleEntity: ResolversParentTypes['IcdLinearizationEntity'] | ResolversParentTypes['IcdSimpleEntity'];
  IcdSearchResult: Schema.IcdSearchResult;
  IcdSimpleEntity: Schema.IcdSimpleEntity;
  IcdSimplePropertyValue: Schema.IcdSimplePropertyValue;
  IcdTerm: Schema.IcdTerm;
  Long: Schema.Scalars['Long'];
  Mutation: {};
  NameEditInput: Schema.NameEditInput;
  NameInfo: Schema.NameInfo;
  NameInput: Schema.NameInput;
  Patient: Schema.Patient;
  PatientEditInput: Schema.PatientEditInput;
  ID: Schema.Scalars['ID'];
  PatientExampleInput: Schema.PatientExampleInput;
  PatientId: Schema.PatientId;
  PatientIdInput: Schema.PatientIdInput;
  PatientInput: Schema.PatientInput;
  Permission: Schema.Permission;
  PermissionId: Schema.PermissionId;
  Permissions: Schema.Permissions;
  Person: ResolversParentTypes['Doctor'] | ResolversParentTypes['EmergencyContact'] | ResolversParentTypes['Patient'];
  Phone: Schema.Phone;
  PhoneInput: Schema.PhoneInput;
  Query: {};
  RangeInput: Schema.RangeInput;
  Record: Schema.Record;
  RecordId: Schema.RecordId;
  RecordIdInput: Schema.RecordIdInput;
  Recurrence: Schema.Recurrence;
  RecurrenceEditSettingsInput: Schema.RecurrenceEditSettingsInput;
  RecurrenceInput: Schema.RecurrenceInput;
  TimeRangeInput: Schema.TimeRangeInput;
  TimeZonedTime: Schema.TimeZonedTime;
  UUID: Schema.Scalars['UUID'];
  User: Schema.User;
  UserEditInput: Schema.UserEditInput;
  UserInput: Schema.UserInput;
  UtcTime: Schema.UtcTime;
  UtcTimeInput: Schema.UtcTimeInput;
  Visit: Schema.Visit;
  VisitId: Schema.VisitId;
  VisitIdInput: Schema.VisitIdInput;
  VisitInput: Schema.VisitInput;
  WhiteSpaceAgnosticStringFilterInput: Schema.WhiteSpaceAgnosticStringFilterInput;
  _FieldSet: Schema.Scalars['_FieldSet'];
  _Service: Schema._Service;
};

export type ExtendsDirectiveArgs = { };

export type ExtendsDirectiveResolver<Result, Parent, ContextType = any, Args = ExtendsDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type ExternalDirectiveArgs = { };

export type ExternalDirectiveResolver<Result, Parent, ContextType = any, Args = ExternalDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type KeyDirectiveArgs = {
  fields?: Schema.Maybe<Schema.Scalars['_FieldSet']>;
};

export type KeyDirectiveResolver<Result, Parent, ContextType = any, Args = KeyDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type ProvidesDirectiveArgs = {
  fields?: Schema.Maybe<Schema.Scalars['_FieldSet']>;
};

export type ProvidesDirectiveResolver<Result, Parent, ContextType = any, Args = ProvidesDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type RequiresDirectiveArgs = {
  fields?: Schema.Maybe<Schema.Scalars['_FieldSet']>;
};

export type RequiresDirectiveResolver<Result, Parent, ContextType = any, Args = RequiresDirectiveArgs> = DirectiveResolverFn<Result, Parent, ContextType, Args>;

export type AddressResolvers<ContextType = any, ParentType extends ResolversParentTypes['Address'] = ResolversParentTypes['Address']> = {
  address?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  addressType?: Resolver<Schema.Maybe<ResolversTypes['AddressType']>, ParentType, ContextType>;
  city?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  country?: Resolver<ResolversTypes['Country'], ParentType, ContextType>;
  postalCode?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  province?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type AssignedSurveyResolvers<ContextType = any, ParentType extends ResolversParentTypes['AssignedSurvey'] = ResolversParentTypes['AssignedSurvey']> = {
  id?: Resolver<ResolversTypes['AssignedSurveyId'], ParentType, ContextType>;
  patient?: Resolver<ResolversTypes['Patient'], ParentType, ContextType>;
  surveyTemplate?: Resolver<ResolversTypes['GraphQLFormTemplate'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type AssignedSurveyIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['AssignedSurveyId'] = ResolversParentTypes['AssignedSurveyId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CanadianProvinceResolvers<ContextType = any, ParentType extends ResolversParentTypes['CanadianProvince'] = ResolversParentTypes['CanadianProvince']> = {
  asShortStrings?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
  asStrings?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
  longToShort?: Resolver<ResolversTypes['String'], ParentType, ContextType, RequireFields<Schema.CanadianProvinceLongToShortArgs, 'province'>>;
  shortToLong?: Resolver<ResolversTypes['String'], ParentType, ContextType, RequireFields<Schema.CanadianProvinceShortToLongArgs, 'province'>>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type ClinicResolvers<ContextType = any, ParentType extends ResolversParentTypes['Clinic'] = ResolversParentTypes['Clinic']> = {
  address?: Resolver<ResolversTypes['Address'], ParentType, ContextType>;
  cid?: Resolver<ResolversTypes['ClinicId'], ParentType, ContextType>;
  doctors?: Resolver<Array<ResolversTypes['Doctor']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type ClinicIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['ClinicId'] = ResolversParentTypes['ClinicId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type DateResolvers<ContextType = any, ParentType extends ResolversParentTypes['Date'] = ResolversParentTypes['Date']> = {
  day?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  month?: Resolver<ResolversTypes['Month'], ParentType, ContextType>;
  year?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  toUtcTime?: Resolver<ResolversTypes['UtcTime'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type DoctorResolvers<ContextType = any, ParentType extends ResolversParentTypes['Doctor'] = ResolversParentTypes['Doctor']> = {
  addresses?: Resolver<Array<ResolversTypes['Address']>, ParentType, ContextType>;
  dateOfBirth?: Resolver<Schema.Maybe<ResolversTypes['Date']>, ParentType, ContextType>;
  did?: Resolver<ResolversTypes['DoctorId'], ParentType, ContextType>;
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phones?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  clinic?: Resolver<ResolversTypes['Clinic'], ParentType, ContextType>;
  events?: Resolver<Array<ResolversTypes['Event']>, ParentType, ContextType>;
  patients?: Resolver<Array<ResolversTypes['Patient']>, ParentType, ContextType>;
  schedule?: Resolver<Array<ResolversTypes['Event']>, ParentType, ContextType, RequireFields<Schema.DoctorScheduleArgs, 'from' | 'to'>>;
  user?: Resolver<Schema.Maybe<ResolversTypes['User']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type DoctorIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['DoctorId'] = ResolversParentTypes['DoctorId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type EmailResolvers<ContextType = any, ParentType extends ResolversParentTypes['Email'] = ResolversParentTypes['Email']> = {
  email?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['EmailType'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type EmergencyContactResolvers<ContextType = any, ParentType extends ResolversParentTypes['EmergencyContact'] = ResolversParentTypes['EmergencyContact']> = {
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phones?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  relationship?: Resolver<ResolversTypes['Relationship'], ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type EventResolvers<ContextType = any, ParentType extends ResolversParentTypes['Event'] = ResolversParentTypes['Event']> = {
  allDay?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  eid?: Resolver<ResolversTypes['EventId'], ParentType, ContextType>;
  endTime?: Resolver<Schema.Maybe<ResolversTypes['UtcTime']>, ParentType, ContextType>;
  reoccurrence?: Resolver<Schema.Maybe<ResolversTypes['Recurrence']>, ParentType, ContextType>;
  startTime?: Resolver<Schema.Maybe<ResolversTypes['UtcTime']>, ParentType, ContextType>;
  title?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  doctors?: Resolver<Array<ResolversTypes['Doctor']>, ParentType, ContextType>;
  patients?: Resolver<Array<ResolversTypes['Patient']>, ParentType, ContextType>;
  visit?: Resolver<Schema.Maybe<ResolversTypes['Visit']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type EventIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['EventId'] = ResolversParentTypes['EventId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type FirebaseInfoResolvers<ContextType = any, ParentType extends ResolversParentTypes['FirebaseInfo'] = ResolversParentTypes['FirebaseInfo']> = {
  displayName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  email?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phoneNumber?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  photoUrl?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  tenantId?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  tokensValidAfterTimestamp?: Resolver<Schema.Maybe<ResolversTypes['Long']>, ParentType, ContextType>;
  uid?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type FormTemplateIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['FormTemplateId'] = ResolversParentTypes['FormTemplateId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GraphQlFormDataResolvers<ContextType = any, ParentType extends ResolversParentTypes['GraphQLFormData'] = ResolversParentTypes['GraphQLFormData']> = {
  data?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  patient?: Resolver<ResolversTypes['Patient'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GraphQlFormSectionResolvers<ContextType = any, ParentType extends ResolversParentTypes['GraphQLFormSection'] = ResolversParentTypes['GraphQLFormSection']> = {
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  fields?: Resolver<Array<ResolversTypes['GraphQlFormField']>, ParentType, ContextType>;
  name?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  number?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GraphQlFormTemplateResolvers<ContextType = any, ParentType extends ResolversParentTypes['GraphQLFormTemplate'] = ResolversParentTypes['GraphQLFormTemplate']> = {
  id?: Resolver<ResolversTypes['FormTemplateId'], ParentType, ContextType>;
  name?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  sections?: Resolver<Array<ResolversTypes['GraphQLFormSection']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GraphQlProvinceResolvers<ContextType = any, ParentType extends ResolversParentTypes['GraphQLProvince'] = ResolversParentTypes['GraphQLProvince']> = {
  __resolveType: TypeResolveFn<'CanadianProvince', ParentType, ContextType>;
  asShortStrings?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
  asStrings?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
};

export type GraphQlFormFieldResolvers<ContextType = any, ParentType extends ResolversParentTypes['GraphQlFormField'] = ResolversParentTypes['GraphQlFormField']> = {
  dataType?: Resolver<ResolversTypes['DataType'], ParentType, ContextType>;
  dateLowerBound?: Resolver<Schema.Maybe<ResolversTypes['Date']>, ParentType, ContextType>;
  dateUpperBound?: Resolver<Schema.Maybe<ResolversTypes['Date']>, ParentType, ContextType>;
  floatLowerBound?: Resolver<Schema.Maybe<ResolversTypes['Float']>, ParentType, ContextType>;
  floatUpperBound?: Resolver<Schema.Maybe<ResolversTypes['Float']>, ParentType, ContextType>;
  intLowerBound?: Resolver<Schema.Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  intUpperBound?: Resolver<Schema.Maybe<ResolversTypes['Int']>, ParentType, ContextType>;
  jsonMetaData?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  multiSelectPossibilities?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  number?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  textRegex?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GroupResolvers<ContextType = any, ParentType extends ResolversParentTypes['Group'] = ResolversParentTypes['Group']> = {
  gid?: Resolver<ResolversTypes['GroupId'], ParentType, ContextType>;
  name?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type GroupIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['GroupId'] = ResolversParentTypes['GroupId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdFoundationEntityResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdFoundationEntity'] = ResolversParentTypes['IcdFoundationEntity']> = {
  browserUrl?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  child?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  context?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  definition?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  exclusion?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  fullySpecifiedName?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  id?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  inclusion?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  longDefinition?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  narrowerTerm?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  parent?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  synonym?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdGuessWordResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdGuessWord'] = ResolversParentTypes['IcdGuessWord']> = {
  dontChangeResult?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  label?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdLanguageSpecificTextResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdLanguageSpecificText'] = ResolversParentTypes['IcdLanguageSpecificText']> = {
  language?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  value?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdLinearizationEntityResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdLinearizationEntity'] = ResolversParentTypes['IcdLinearizationEntity']> = {
  blockId?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  browserUrl?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  child?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  classKind?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  code?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  codeRange?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  codingNote?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  context?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  definition?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  exclusion?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  foundationChildElsewhere?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  fullySpecifiedName?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  id?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  inclusion?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  indexTerm?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdTerm']>>, ParentType, ContextType>;
  languageTitle?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  longDefinition?: Resolver<Schema.Maybe<ResolversTypes['IcdLanguageSpecificText']>, ParentType, ContextType>;
  parent?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  postcoordinationScale?: Resolver<Schema.Maybe<Array<Schema.Maybe<ResolversTypes['IcdPostCoordinationScaleInfo']>>>, ParentType, ContextType>;
  source?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdPostCoordinationScaleInfoResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdPostCoordinationScaleInfo'] = ResolversParentTypes['IcdPostCoordinationScaleInfo']> = {
  allowMultipleValues?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  axisName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  id?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  requiredPostcoordination?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  scaleEntity?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdReallySimpleEntityResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdReallySimpleEntity'] = ResolversParentTypes['IcdReallySimpleEntity']> = {
  __resolveType: TypeResolveFn<'IcdLinearizationEntity' | 'IcdSimpleEntity', ParentType, ContextType>;
  code?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  id?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
};

export type IcdSearchResultResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdSearchResult'] = ResolversParentTypes['IcdSearchResult']> = {
  destinationEntities?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdSimpleEntity']>>, ParentType, ContextType>;
  error?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  errorMessage?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  guessType?: Resolver<Schema.Maybe<ResolversTypes['IcdGuessType']>, ParentType, ContextType>;
  resultChopped?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  uniqueSearchId?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  wordSuggestionsChopped?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  words?: Resolver<Schema.Maybe<Array<ResolversTypes['IcdGuessWord']>>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdSimpleEntityResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdSimpleEntity'] = ResolversParentTypes['IcdSimpleEntity']> = {
  chapter?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  code?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  descendants?: Resolver<Array<ResolversTypes['IcdSimpleEntity']>, ParentType, ContextType>;
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  entityType?: Resolver<ResolversTypes['IcdEntityType'], ParentType, ContextType>;
  hasCodingNote?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  hasMaternalChapterLink?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  id?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  important?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  isLeaf?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  isResidualOther?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  isResidualUnspecified?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  matchingPVs?: Resolver<Array<ResolversTypes['IcdSimplePropertyValue']>, ParentType, ContextType>;
  postcoordinationAvailability?: Resolver<ResolversTypes['IcdPostcoordinationAvailability'], ParentType, ContextType>;
  propertiesTruncated?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  score?: Resolver<ResolversTypes['Float'], ParentType, ContextType>;
  stemId?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  theCode?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  titleIsASearchResult?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  titleIsTopScore?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  entity?: Resolver<Schema.Maybe<ResolversTypes['IcdFoundationEntity']>, ParentType, ContextType>;
  tagTitle?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType, Partial<Schema.IcdSimpleEntityTagTitleArgs>>;
  urlId?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType, Partial<Schema.IcdSimpleEntityUrlIdArgs>>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdSimplePropertyValueResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdSimplePropertyValue'] = ResolversParentTypes['IcdSimplePropertyValue']> = {
  important?: Resolver<Schema.Maybe<ResolversTypes['Boolean']>, ParentType, ContextType>;
  label?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  propertyId?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  score?: Resolver<Schema.Maybe<ResolversTypes['Float']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type IcdTermResolvers<ContextType = any, ParentType extends ResolversParentTypes['IcdTerm'] = ResolversParentTypes['IcdTerm']> = {
  foundationReference?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  label?: Resolver<ResolversTypes['IcdLanguageSpecificText'], ParentType, ContextType>;
  linearizationReference?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface LongScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['Long'], any> {
  name: 'Long';
}

export type MutationResolvers<ContextType = any, ParentType extends ResolversParentTypes['Mutation'] = ResolversParentTypes['Mutation']> = {
  addClinic?: Resolver<ResolversTypes['Clinic'], ParentType, ContextType, RequireFields<Schema.MutationAddClinicArgs, 'clinic'>>;
  editClinic?: Resolver<ResolversTypes['Clinic'], ParentType, ContextType, RequireFields<Schema.MutationEditClinicArgs, 'clinic'>>;
  addDoctor?: Resolver<ResolversTypes['Doctor'], ParentType, ContextType, RequireFields<Schema.MutationAddDoctorArgs, 'doctor'>>;
  editDoctor?: Resolver<ResolversTypes['Doctor'], ParentType, ContextType, RequireFields<Schema.MutationEditDoctorArgs, 'doctor'>>;
  addEvent?: Resolver<ResolversTypes['Event'], ParentType, ContextType, RequireFields<Schema.MutationAddEventArgs, 'event'>>;
  editEvent?: Resolver<ResolversTypes['Event'], ParentType, ContextType, RequireFields<Schema.MutationEditEventArgs, 'event'>>;
  editRecurringEvent?: Resolver<ResolversTypes['Event'], ParentType, ContextType, RequireFields<Schema.MutationEditRecurringEventArgs, 'event' | 'recurrenceSettings'>>;
  addSurveyTemplate?: Resolver<ResolversTypes['GraphQLFormTemplate'], ParentType, ContextType, RequireFields<Schema.MutationAddSurveyTemplateArgs, 'surveyTemplate'>>;
  assignSurvey?: Resolver<Array<ResolversTypes['Patient']>, ParentType, ContextType, RequireFields<Schema.MutationAssignSurveyArgs, 'patients' | 'survey'>>;
  submitSurvey?: Resolver<ResolversTypes['GraphQLFormData'], ParentType, ContextType, RequireFields<Schema.MutationSubmitSurveyArgs, 'patient' | 'surveyJson'>>;
  addGroup?: Resolver<ResolversTypes['Group'], ParentType, ContextType, RequireFields<Schema.MutationAddGroupArgs, 'group'>>;
  addPatient?: Resolver<ResolversTypes['Patient'], ParentType, ContextType, RequireFields<Schema.MutationAddPatientArgs, 'patient'>>;
  updatePatient?: Resolver<ResolversTypes['Patient'], ParentType, ContextType, RequireFields<Schema.MutationUpdatePatientArgs, 'patient'>>;
  addPermission?: Resolver<ResolversTypes['Permission'], ParentType, ContextType, RequireFields<Schema.MutationAddPermissionArgs, 'permission'>>;
  addRecord?: Resolver<ResolversTypes['Record'], ParentType, ContextType, RequireFields<Schema.MutationAddRecordArgs, 'record'>>;
  addUser?: Resolver<ResolversTypes['User'], ParentType, ContextType, RequireFields<Schema.MutationAddUserArgs, 'user'>>;
  editUser?: Resolver<ResolversTypes['User'], ParentType, ContextType, RequireFields<Schema.MutationEditUserArgs, 'user'>>;
  addVisit?: Resolver<ResolversTypes['Visit'], ParentType, ContextType, RequireFields<Schema.MutationAddVisitArgs, 'visit'>>;
  editVisit?: Resolver<ResolversTypes['Visit'], ParentType, ContextType, RequireFields<Schema.MutationEditVisitArgs, 'visit'>>;
};

export type NameInfoResolvers<ContextType = any, ParentType extends ResolversParentTypes['NameInfo'] = ResolversParentTypes['NameInfo']> = {
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PatientResolvers<ContextType = any, ParentType extends ResolversParentTypes['Patient'] = ResolversParentTypes['Patient']> = {
  addresses?: Resolver<Array<ResolversTypes['Address']>, ParentType, ContextType>;
  dateOfBirth?: Resolver<ResolversTypes['Date'], ParentType, ContextType>;
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  ethnicity?: Resolver<Schema.Maybe<ResolversTypes['Ethnicity']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  gender?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  insuranceNumber?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phones?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  pid?: Resolver<ResolversTypes['PatientId'], ParentType, ContextType>;
  sex?: Resolver<ResolversTypes['Sex'], ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  assignedForms?: Resolver<Array<ResolversTypes['AssignedSurvey']>, ParentType, ContextType>;
  contacts?: Resolver<Array<ResolversTypes['Person']>, ParentType, ContextType>;
  doctors?: Resolver<Array<ResolversTypes['Doctor']>, ParentType, ContextType>;
  events?: Resolver<Array<ResolversTypes['Event']>, ParentType, ContextType>;
  user?: Resolver<Schema.Maybe<ResolversTypes['User']>, ParentType, ContextType>;
  visits?: Resolver<Array<ResolversTypes['Visit']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PatientIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['PatientId'] = ResolversParentTypes['PatientId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PermissionResolvers<ContextType = any, ParentType extends ResolversParentTypes['Permission'] = ResolversParentTypes['Permission']> = {
  columnName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  endTime?: Resolver<Schema.Maybe<ResolversTypes['UtcTime']>, ParentType, ContextType>;
  permissionType?: Resolver<ResolversTypes['Crud'], ParentType, ContextType>;
  pid?: Resolver<ResolversTypes['PermissionId'], ParentType, ContextType>;
  referencedTableName?: Resolver<ResolversTypes['Tables'], ParentType, ContextType>;
  rowId?: Resolver<Schema.Maybe<ResolversTypes['UUID']>, ParentType, ContextType>;
  startTime?: Resolver<Schema.Maybe<ResolversTypes['UtcTime']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PermissionIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['PermissionId'] = ResolversParentTypes['PermissionId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PermissionsResolvers<ContextType = any, ParentType extends ResolversParentTypes['Permissions'] = ResolversParentTypes['Permissions']> = {
  groupPerms?: Resolver<Array<ResolversTypes['Permission']>, ParentType, ContextType>;
  userPerms?: Resolver<Array<ResolversTypes['Permission']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PersonResolvers<ContextType = any, ParentType extends ResolversParentTypes['Person'] = ResolversParentTypes['Person']> = {
  __resolveType: TypeResolveFn<'Doctor' | 'EmergencyContact' | 'Patient', ParentType, ContextType>;
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phones?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
};

export type PhoneResolvers<ContextType = any, ParentType extends ResolversParentTypes['Phone'] = ResolversParentTypes['Phone']> = {
  number?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['PhoneType'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type QueryResolvers<ContextType = any, ParentType extends ResolversParentTypes['Query'] = ResolversParentTypes['Query']> = {
  clinic?: Resolver<ResolversTypes['Clinic'], ParentType, ContextType, RequireFields<Schema.QueryClinicArgs, 'cid'>>;
  getContactsByPatient?: Resolver<Array<ResolversTypes['Person']>, ParentType, ContextType, RequireFields<Schema.QueryGetContactsByPatientArgs, 'pid'>>;
  convert?: Resolver<ResolversTypes['String'], ParentType, ContextType, RequireFields<Schema.QueryConvertArgs, 'json' | 'target'>>;
  country?: Resolver<ResolversTypes['GraphQLProvince'], ParentType, ContextType, RequireFields<Schema.QueryCountryArgs, 'country'>>;
  doctors?: Resolver<Array<ResolversTypes['Doctor']>, ParentType, ContextType, Partial<Schema.QueryDoctorsArgs>>;
  events?: Resolver<Array<ResolversTypes['Event']>, ParentType, ContextType, Partial<Schema.QueryEventsArgs>>;
  surveys?: Resolver<Array<ResolversTypes['GraphQLFormTemplate']>, ParentType, ContextType, Partial<Schema.QuerySurveysArgs>>;
  groups?: Resolver<Array<ResolversTypes['Group']>, ParentType, ContextType, Partial<Schema.QueryGroupsArgs>>;
  mockingbirdIsAlive?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  icd?: Resolver<ResolversTypes['IcdLinearizationEntity'], ParentType, ContextType, RequireFields<Schema.QueryIcdArgs, 'icdCode'>>;
  icds?: Resolver<Array<ResolversTypes['IcdLinearizationEntity']>, ParentType, ContextType, RequireFields<Schema.QueryIcdsArgs, 'icdCodes'>>;
  searchIcd?: Resolver<ResolversTypes['IcdSearchResult'], ParentType, ContextType, RequireFields<Schema.QuerySearchIcdArgs, 'query'>>;
  patients?: Resolver<Array<ResolversTypes['Patient']>, ParentType, ContextType, Partial<Schema.QueryPatientsArgs>>;
  permissions?: Resolver<ResolversTypes['Permissions'], ParentType, ContextType, RequireFields<Schema.QueryPermissionsArgs, 'uid'>>;
  records?: Resolver<Array<ResolversTypes['Record']>, ParentType, ContextType, Partial<Schema.QueryRecordsArgs>>;
  firebaseUsers?: Resolver<Array<ResolversTypes['FirebaseInfo']>, ParentType, ContextType, Partial<Schema.QueryFirebaseUsersArgs>>;
  user?: Resolver<ResolversTypes['User'], ParentType, ContextType, RequireFields<Schema.QueryUserArgs, 'uid'>>;
  users?: Resolver<Array<ResolversTypes['User']>, ParentType, ContextType, Partial<Schema.QueryUsersArgs>>;
  visits?: Resolver<Array<ResolversTypes['Visit']>, ParentType, ContextType, RequireFields<Schema.QueryVisitsArgs, 'strict'>>;
  _service?: Resolver<Schema.Maybe<ResolversTypes['_Service']>, ParentType, ContextType>;
};

export type RecordResolvers<ContextType = any, ParentType extends ResolversParentTypes['Record'] = ResolversParentTypes['Record']> = {
  creationDate?: Resolver<ResolversTypes['UtcTime'], ParentType, ContextType>;
  jsonBlob?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  rid?: Resolver<ResolversTypes['RecordId'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['RecordType'], ParentType, ContextType>;
  patient?: Resolver<ResolversTypes['Patient'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type RecordIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['RecordId'] = ResolversParentTypes['RecordId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type RecurrenceResolvers<ContextType = any, ParentType extends ResolversParentTypes['Recurrence'] = ResolversParentTypes['Recurrence']> = {
  daysOfWeek?: Resolver<Array<ResolversTypes['DayOfWeek']>, ParentType, ContextType>;
  endDate?: Resolver<ResolversTypes['Date'], ParentType, ContextType>;
  startDate?: Resolver<ResolversTypes['Date'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type TimeZonedTimeResolvers<ContextType = any, ParentType extends ResolversParentTypes['TimeZonedTime'] = ResolversParentTypes['TimeZonedTime']> = {
  timeZone?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  unixMilliseconds?: Resolver<ResolversTypes['Long'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface UuidScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['UUID'], any> {
  name: 'UUID';
}

export type UserResolvers<ContextType = any, ParentType extends ResolversParentTypes['User'] = ResolversParentTypes['User']> = {
  group?: Resolver<Schema.Maybe<ResolversTypes['Group']>, ParentType, ContextType>;
  uid?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  doctor?: Resolver<Schema.Maybe<ResolversTypes['Doctor']>, ParentType, ContextType>;
  firebaseUserInfo?: Resolver<ResolversTypes['FirebaseInfo'], ParentType, ContextType>;
  hasPermission?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType, RequireFields<Schema.UserHasPermissionArgs, 'perm'>>;
  isRegistered?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  names?: Resolver<Schema.Maybe<ResolversTypes['NameInfo']>, ParentType, ContextType>;
  patient?: Resolver<Schema.Maybe<ResolversTypes['Patient']>, ParentType, ContextType>;
  permissions?: Resolver<ResolversTypes['Permissions'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type UtcTimeResolvers<ContextType = any, ParentType extends ResolversParentTypes['UtcTime'] = ResolversParentTypes['UtcTime']> = {
  unixMilliseconds?: Resolver<ResolversTypes['Long'], ParentType, ContextType>;
  before?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType, RequireFields<Schema.UtcTimeBeforeArgs, 'end'>>;
  withRespectTo?: Resolver<ResolversTypes['TimeZonedTime'], ParentType, ContextType, RequireFields<Schema.UtcTimeWithRespectToArgs, 'timeZone'>>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type VisitResolvers<ContextType = any, ParentType extends ResolversParentTypes['Visit'] = ResolversParentTypes['Visit']> = {
  description?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  vid?: Resolver<ResolversTypes['VisitId'], ParentType, ContextType>;
  event?: Resolver<ResolversTypes['Event'], ParentType, ContextType>;
  icds?: Resolver<Array<ResolversTypes['IcdLinearizationEntity']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type VisitIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['VisitId'] = ResolversParentTypes['VisitId']> = {
  id?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface _FieldSetScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['_FieldSet'], any> {
  name: '_FieldSet';
}

export type _ServiceResolvers<ContextType = any, ParentType extends ResolversParentTypes['_Service'] = ResolversParentTypes['_Service']> = {
  sdl?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type Resolvers<ContextType = any> = {
  Address?: AddressResolvers<ContextType>;
  AssignedSurvey?: AssignedSurveyResolvers<ContextType>;
  AssignedSurveyId?: AssignedSurveyIdResolvers<ContextType>;
  CanadianProvince?: CanadianProvinceResolvers<ContextType>;
  Clinic?: ClinicResolvers<ContextType>;
  ClinicId?: ClinicIdResolvers<ContextType>;
  Date?: DateResolvers<ContextType>;
  Doctor?: DoctorResolvers<ContextType>;
  DoctorId?: DoctorIdResolvers<ContextType>;
  Email?: EmailResolvers<ContextType>;
  EmergencyContact?: EmergencyContactResolvers<ContextType>;
  Event?: EventResolvers<ContextType>;
  EventId?: EventIdResolvers<ContextType>;
  FirebaseInfo?: FirebaseInfoResolvers<ContextType>;
  FormTemplateId?: FormTemplateIdResolvers<ContextType>;
  GraphQLFormData?: GraphQlFormDataResolvers<ContextType>;
  GraphQLFormSection?: GraphQlFormSectionResolvers<ContextType>;
  GraphQLFormTemplate?: GraphQlFormTemplateResolvers<ContextType>;
  GraphQLProvince?: GraphQlProvinceResolvers<ContextType>;
  GraphQlFormField?: GraphQlFormFieldResolvers<ContextType>;
  Group?: GroupResolvers<ContextType>;
  GroupId?: GroupIdResolvers<ContextType>;
  IcdFoundationEntity?: IcdFoundationEntityResolvers<ContextType>;
  IcdGuessWord?: IcdGuessWordResolvers<ContextType>;
  IcdLanguageSpecificText?: IcdLanguageSpecificTextResolvers<ContextType>;
  IcdLinearizationEntity?: IcdLinearizationEntityResolvers<ContextType>;
  IcdPostCoordinationScaleInfo?: IcdPostCoordinationScaleInfoResolvers<ContextType>;
  IcdReallySimpleEntity?: IcdReallySimpleEntityResolvers<ContextType>;
  IcdSearchResult?: IcdSearchResultResolvers<ContextType>;
  IcdSimpleEntity?: IcdSimpleEntityResolvers<ContextType>;
  IcdSimplePropertyValue?: IcdSimplePropertyValueResolvers<ContextType>;
  IcdTerm?: IcdTermResolvers<ContextType>;
  Long?: GraphQLScalarType;
  Mutation?: MutationResolvers<ContextType>;
  NameInfo?: NameInfoResolvers<ContextType>;
  Patient?: PatientResolvers<ContextType>;
  PatientId?: PatientIdResolvers<ContextType>;
  Permission?: PermissionResolvers<ContextType>;
  PermissionId?: PermissionIdResolvers<ContextType>;
  Permissions?: PermissionsResolvers<ContextType>;
  Person?: PersonResolvers<ContextType>;
  Phone?: PhoneResolvers<ContextType>;
  Query?: QueryResolvers<ContextType>;
  Record?: RecordResolvers<ContextType>;
  RecordId?: RecordIdResolvers<ContextType>;
  Recurrence?: RecurrenceResolvers<ContextType>;
  TimeZonedTime?: TimeZonedTimeResolvers<ContextType>;
  UUID?: GraphQLScalarType;
  User?: UserResolvers<ContextType>;
  UtcTime?: UtcTimeResolvers<ContextType>;
  Visit?: VisitResolvers<ContextType>;
  VisitId?: VisitIdResolvers<ContextType>;
  _FieldSet?: GraphQLScalarType;
  _Service?: _ServiceResolvers<ContextType>;
};

export type DirectiveResolvers<ContextType = any> = {
  extends?: ExtendsDirectiveResolver<any, any, ContextType>;
  external?: ExternalDirectiveResolver<any, any, ContextType>;
  key?: KeyDirectiveResolver<any, any, ContextType>;
  provides?: ProvidesDirectiveResolver<any, any, ContextType>;
  requires?: RequiresDirectiveResolver<any, any, ContextType>;
};

export const PhoneFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'phoneFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Phone' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'number' } }] } }] } as unknown as DocumentNode<PhoneFragmentFragment, unknown>;
export const EmailFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'emailFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Email' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } }] } }] } as unknown as DocumentNode<EmailFragmentFragment, unknown>;
export const ContactFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'contactFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'EmergencyContact' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'relationship' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'phones' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'phoneFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'emailFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'thumbnail' } }] } },...PhoneFragmentFragmentDoc.definitions,...EmailFragmentFragmentDoc.definitions] } as unknown as DocumentNode<ContactFragmentFragment, unknown>;
export const DoctorsFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'doctorsFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Doctor' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'did' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'emailFragment' } }] } }] } },...EmailFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorsFragmentFragment, unknown>;
export const AddressFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'addressFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Address' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addressType' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'address' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'city' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'country' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'postalCode' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'province' } }] } }] } as unknown as DocumentNode<AddressFragmentFragment, unknown>;
export const NamesFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'namesFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'NameInfo' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } }] } }] } as unknown as DocumentNode<NamesFragmentFragment, unknown>;
export const UsersFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'usersFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'User' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'uid' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'isRegistered' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'group' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'name' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firebaseUserInfo' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'names' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'namesFragment' } }] } }] } },...NamesFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UsersFragmentFragment, unknown>;
export const PatientsFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'patientsFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Patient' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'pid' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'dateOfBirth' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'day' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'month' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'year' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sex' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'gender' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } }] } }] } }] } as unknown as DocumentNode<PatientsFragmentFragment, unknown>;
export const DoctorFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'doctorFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Doctor' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'__typename' } },{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorsFragment' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'addresses' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'addressFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'phones' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'phoneFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'emailFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'user' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'usersFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'patients' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientsFragment' } }] } }] } },...DoctorsFragmentFragmentDoc.definitions,...AddressFragmentFragmentDoc.definitions,...PhoneFragmentFragmentDoc.definitions,...EmailFragmentFragmentDoc.definitions,...UsersFragmentFragmentDoc.definitions,...PatientsFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorFragmentFragment, unknown>;
export const FirebaseUsersFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'firebaseUsersFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'FirebaseInfo' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'uid' } }] } }] } as unknown as DocumentNode<FirebaseUsersFragmentFragment, unknown>;
export const GroupFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'groupFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Group' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'gid' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'name' } }] } }] } as unknown as DocumentNode<GroupFragmentFragment, unknown>;
export const IcdFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'icdFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'IcdReallySimpleEntity' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'code' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'description' } }] } }] } as unknown as DocumentNode<IcdFragmentFragment, unknown>;
export const PatientFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'patientFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Patient' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientsFragment' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'insuranceNumber' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'ethnicity' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'addresses' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'addressFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'phones' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'phoneFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'emailFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'doctors' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorsFragment' } }] } }] } },...PatientsFragmentFragmentDoc.definitions,...AddressFragmentFragmentDoc.definitions,...PhoneFragmentFragmentDoc.definitions,...EmailFragmentFragmentDoc.definitions,...DoctorsFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PatientFragmentFragment, unknown>;
export const UserFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'userFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'User' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'usersFragment' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'doctor' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorsFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'patient' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientsFragment' } }] } }] } },...UsersFragmentFragmentDoc.definitions,...DoctorsFragmentFragmentDoc.definitions,...PatientsFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UserFragmentFragment, unknown>;
export const DoctorEditMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'doctorEditMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorEditInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'editDoctor' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'doctor' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorFragment' } }] } }] } },...DoctorFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorEditMutationMutation, DoctorEditMutationMutationVariables>;
export const DoctorMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'doctorMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addDoctor' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'doctor' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorFragment' } }] } }] } },...DoctorFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorMutationMutation, DoctorMutationMutationVariables>;
export const PatientEditMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'patientEditMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientEditInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'updatePatient' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'patient' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientFragment' } }] } }] } },...PatientFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PatientEditMutationMutation, PatientEditMutationMutationVariables>;
export const PatientMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'patientMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addPatient' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'patient' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientFragment' } }] } }] } },...PatientFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PatientMutationMutation, PatientMutationMutationVariables>;
export const UserEditMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'userEditMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'user' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'UserEditInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'editUser' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'user' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'user' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'userFragment' } }] } }] } },...UserFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UserEditMutationMutation, UserEditMutationMutationVariables>;
export const UserMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'userMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'user' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'UserInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addUser' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'user' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'user' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'userFragment' } }] } }] } },...UserFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UserMutationMutation, UserMutationMutationVariables>;
export const DoctorQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'doctorQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'dids' } },'type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorIdInput' } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'doctors' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'dids' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'dids' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorFragment' } }] } }] } },...DoctorFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorQueryQuery, DoctorQueryQueryVariables>;
export const DoctorsQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'doctorsQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'RangeInput' } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'dids' } },'type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorIdInput' } } } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'example' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorExampleInput' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'doctors' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'dids' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'dids' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'example' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'example' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctorsFragment' } }] } }] } },...DoctorsFragmentFragmentDoc.definitions] } as unknown as DocumentNode<DoctorsQueryQuery, DoctorsQueryQueryVariables>;
export const GroupsQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'groupsQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'gids' } },'type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'GroupIdInput' } } } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'RangeInput' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'groups' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'gids' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'gids' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'groupFragment' } }] } }] } },...GroupFragmentFragmentDoc.definitions] } as unknown as DocumentNode<GroupsQueryQuery, GroupsQueryQueryVariables>;
export const PatientQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'patientQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'pids' } },'type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientIdInput' } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'patients' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'pids' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'pids' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientFragment' } }] } }] } },...PatientFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PatientQueryQuery, PatientQueryQueryVariables>;
export const PatientsQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'patientsQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'RangeInput' } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'pids' } },'type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientIdInput' } } } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'sortedBy' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SortableField' } } },{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'example' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientExampleInput' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'patients' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'sortedBy' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'sortedBy' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'pids' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'pids' } } },{ 'kind':'Argument','name':{ 'kind':'Name','value':'example' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'example' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patientsFragment' } }] } }] } },...PatientsFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PatientsQueryQuery, PatientsQueryQueryVariables>;
export const UserQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'userQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'uid' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'ID' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'user' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'uid' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'uid' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'userFragment' } }] } }] } },...UserFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UserQueryQuery, UserQueryQueryVariables>;
export const UsersQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'usersQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'RangeInput' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'users' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'usersFragment' } }] } }] } },...UsersFragmentFragmentDoc.definitions] } as unknown as DocumentNode<UsersQueryQuery, UsersQueryQueryVariables>;
export type DoctorEditMutationMutationStore = OperationStore<DoctorEditMutationMutation, DoctorEditMutationMutationVariables>;
export type DoctorMutationMutationStore = OperationStore<DoctorMutationMutation, DoctorMutationMutationVariables>;
export type PatientEditMutationMutationStore = OperationStore<PatientEditMutationMutation, PatientEditMutationMutationVariables>;
export type PatientMutationMutationStore = OperationStore<PatientMutationMutation, PatientMutationMutationVariables>;
export type UserEditMutationMutationStore = OperationStore<UserEditMutationMutation, UserEditMutationMutationVariables>;
export type UserMutationMutationStore = OperationStore<UserMutationMutation, UserMutationMutationVariables>;
export type DoctorQueryQueryStore = OperationStore<DoctorQueryQuery, DoctorQueryQueryVariables>;
export type DoctorsQueryQueryStore = OperationStore<DoctorsQueryQuery, DoctorsQueryQueryVariables>;
export type GroupsQueryQueryStore = OperationStore<GroupsQueryQuery, GroupsQueryQueryVariables>;
export type PatientQueryQueryStore = OperationStore<PatientQueryQuery, PatientQueryQueryVariables>;
export type PatientsQueryQueryStore = OperationStore<PatientsQueryQuery, PatientsQueryQueryVariables>;
export type UserQueryQueryStore = OperationStore<UserQueryQuery, UserQueryQueryVariables>;
export type UsersQueryQueryStore = OperationStore<UsersQueryQuery, UsersQueryQueryVariables>;