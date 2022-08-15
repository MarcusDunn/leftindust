export type Maybe<T> = T | undefined;
export type InputMaybe<T> = T | undefined;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
  Base64: any;
  Duration: any;
  LocalDate: any;
  LocalDateTime: any;
  UUID: any;
};

export enum AddressType {
  Apartment = 'Apartment',
  Home = 'Home',
  Other = 'Other',
  School = 'School',
  Work = 'Work'
}

export type Clinic = {
  __typename?: 'Clinic';
  id: ClinicId;
  name: Scalars['String'];
};

export type ClinicId = {
  __typename?: 'ClinicId';
  value: Scalars['UUID'];
};

export type ClinicIdInput = {
  value: Scalars['UUID'];
};

export type CompleteSurvey = {
  __typename?: 'CompleteSurvey';
  id: CompleteSurveyId;
  sections: Array<CompleteSurveySection>;
  surveyTemplate: SurveyTemplate;
};

export type CompleteSurveyId = {
  __typename?: 'CompleteSurveyId';
  value: Scalars['UUID'];
};

export type CompleteSurveyIdInput = {
  value?: InputMaybe<Scalars['UUID']>;
};

export type CompleteSurveySection = {
  __typename?: 'CompleteSurveySection';
  id: CompleteSurveySectionId;
  inputs: Array<CompleteSurveySectionInput>;
};

export type CompleteSurveySectionId = {
  __typename?: 'CompleteSurveySectionId';
  value: Scalars['UUID'];
};

export type CompleteSurveySectionInput = {
  __typename?: 'CompleteSurveySectionInput';
  id: CompleteSurveySectionInputId;
  value: Scalars['String'];
};

export type CompleteSurveySectionInputId = {
  __typename?: 'CompleteSurveySectionInputId';
  value: Scalars['UUID'];
};

export enum Countries {
  Canada = 'Canada'
}

export type CreateAddress = {
  address: Scalars['String'];
  addressType: AddressType;
  city: Scalars['String'];
  country: Countries;
  postalCode: Scalars['String'];
  province: Scalars['String'];
};

export type CreateClinic = {
  address: CreateAddress;
  doctors?: Array<DoctorIdInput>;
  name: Scalars['String'];
};

export type CreateCompleteSurvey = {
  completeSurveyTemplateSections: Array<CreateCompleteSurveySection>;
  surveyLinkId: SurveyTemplateLinkIdInput;
};

export type CreateCompleteSurveyInput = {
  surveyTemplateSectionInputId: SurveyTemplateSectionInputIdInput;
  value: Scalars['String'];
};

export type CreateCompleteSurveySection = {
  completedSurveyInputs: Array<CreateCompleteSurveyInput>;
  surveyTemplateSectionId: SurveyTemplateSectionIdInput;
};

export type CreateNameInfo = {
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: InputMaybe<Scalars['String']>;
};

export type CreateSurveyTemplate = {
  calculations?: Array<CreateSurveyTemplateCalculation>;
  sections: Array<CreateSurveyTemplateSection>;
  subtitle?: InputMaybe<Scalars['String']>;
  title: Scalars['String'];
};

export type CreateSurveyTemplateCalculation = {
  calculation: Scalars['String'];
  inputType?: InputMaybe<SurveyTemplateInputType>;
  label: Scalars['String'];
  showOnComplete: Scalars['Boolean'];
};

export type CreateSurveyTemplateSection = {
  calculationId?: InputMaybe<Scalars['Int']>;
  inputs: Array<CreateSurveyTemplateSectionInput>;
  subtitle?: InputMaybe<Scalars['String']>;
  title: Scalars['String'];
};

export type CreateSurveyTemplateSectionInput = {
  calculationId?: InputMaybe<Scalars['Int']>;
  category: SurveyTemplateCategory;
  label: Scalars['String'];
  options?: InputMaybe<Array<Scalars['String']>>;
  placeholder?: InputMaybe<Scalars['String']>;
  required: Scalars['Boolean'];
  type: SurveyTemplateInputType;
  uploadAccept?: InputMaybe<TemplateInputUploadType>;
  uploadMultiple?: InputMaybe<Scalars['Boolean']>;
};

export type CreateUser = {
  group?: InputMaybe<MediqGroupIdInput>;
  nameInfo: CreateNameInfo;
  uid: Scalars['String'];
};

export type Doctor = {
  __typename?: 'Doctor';
  dateOfBirth?: Maybe<Scalars['LocalDate']>;
  firstName: Scalars['String'];
  id?: Maybe<DoctorId>;
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  thumbnail?: Maybe<Scalars['Base64']>;
  title?: Maybe<Scalars['String']>;
};

export type DoctorId = {
  __typename?: 'DoctorId';
  value: Scalars['UUID'];
};

export type DoctorIdInput = {
  value: Scalars['UUID'];
};

export type EditClinic = {
  address?: InputMaybe<CreateAddress>;
  cid: ClinicIdInput;
  doctors?: Array<DoctorIdInput>;
  name?: InputMaybe<Scalars['String']>;
};

export enum Ethnicity {
  AmericanAboriginal = 'AmericanAboriginal',
  Asian = 'Asian',
  Black = 'Black',
  Hispanic = 'Hispanic',
  PacificIslander = 'PacificIslander',
  White = 'White'
}

export type MediqGroup = {
  __typename?: 'MediqGroup';
  id: MediqGroupId;
  name?: Maybe<Scalars['String']>;
};

export type MediqGroupId = {
  __typename?: 'MediqGroupId';
  value: Scalars['UUID'];
};

export type MediqGroupIdInput = {
  value?: InputMaybe<Scalars['UUID']>;
};

export type MediqUser = {
  __typename?: 'MediqUser';
  accountDetails?: Maybe<UserAccountDetails>;
  group?: Maybe<MediqGroup>;
  id: MediqUserId;
  name: NameInfo;
};

export type MediqUserId = {
  __typename?: 'MediqUserId';
  value: Scalars['String'];
};

export type Mutation = {
  __typename?: 'Mutation';
  /**  clinic */
  addClinic: Clinic;
  /**  survey template */
  addSurveyTemplate: SurveyTemplate;
  /**  complete survey */
  createCompleteSurvey?: Maybe<CompleteSurvey>;
  /**  user */
  createMediqUser?: Maybe<MediqUser>;
  /**  survey link */
  createSurveyLink: SurveyLink;
  editClinic?: Maybe<Clinic>;
};


export type MutationAddClinicArgs = {
  clinic: CreateClinic;
};


export type MutationAddSurveyTemplateArgs = {
  surveyTemplate: CreateSurveyTemplate;
};


export type MutationCreateCompleteSurveyArgs = {
  createCompleteSurvey?: InputMaybe<CreateCompleteSurvey>;
};


export type MutationCreateMediqUserArgs = {
  createUser?: InputMaybe<CreateUser>;
};


export type MutationCreateSurveyLinkArgs = {
  surveyTemplateId?: InputMaybe<SurveyTemplateIdInput>;
};


export type MutationEditClinicArgs = {
  clinic: EditClinic;
};

export type NameInfo = {
  __typename?: 'NameInfo';
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
};

export type Patient = {
  __typename?: 'Patient';
  dateOfBirth: Scalars['LocalDate'];
  ethnicity?: Maybe<Ethnicity>;
  firstName: Scalars['String'];
  gender: Scalars['String'];
  id: PatientId;
  insuranceNumber?: Maybe<Scalars['String']>;
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  sex: Sex;
  thumbnail?: Maybe<Scalars['Base64']>;
};

export type PatientId = {
  __typename?: 'PatientId';
  value: Scalars['UUID'];
};

export type PatientIdInput = {
  value?: InputMaybe<Scalars['UUID']>;
};

export type Query = {
  __typename?: 'Query';
  /**  clinic */
  clinicByClinicId?: Maybe<Clinic>;
  /**  complete survey */
  completeSurveyById?: Maybe<CompleteSurvey>;
  /**  doctor */
  doctorsByDoctorIds: Array<Maybe<Doctor>>;
  doctorsByRange: Array<Doctor>;
  /**  mockingbirdIsAlive */
  mockingbirdIsAlive: Scalars['Boolean'];
  /**  patient */
  patientsByPatientId: Array<Maybe<Patient>>;
  patientsByRange: Array<Patient>;
  /**  survey link */
  surveyLinkById?: Maybe<SurveyLink>;
  /**  survey template */
  surveyTemplateById?: Maybe<SurveyTemplate>;
  surveyTemplateByRange: Array<SurveyTemplate>;
  /**  user */
  userByUserUniqueId?: Maybe<MediqUser>;
};


export type QueryClinicByClinicIdArgs = {
  clinicId: ClinicIdInput;
};


export type QueryCompleteSurveyByIdArgs = {
  completeSurveyId?: InputMaybe<CompleteSurveyIdInput>;
};


export type QueryDoctorsByDoctorIdsArgs = {
  doctorIds: Array<DoctorIdInput>;
};


export type QueryDoctorsByRangeArgs = {
  range: Range;
};


export type QueryPatientsByPatientIdArgs = {
  patientIds: Array<PatientIdInput>;
};


export type QueryPatientsByRangeArgs = {
  range: Range;
};


export type QuerySurveyLinkByIdArgs = {
  surveyLinkId: SurveyLinkIdInput;
};


export type QuerySurveyTemplateByIdArgs = {
  surveyTemplateId: SurveyTemplateIdInput;
};


export type QuerySurveyTemplateByRangeArgs = {
  range: Range;
};


export type QueryUserByUserUniqueIdArgs = {
  uniqueId: Scalars['String'];
};

export type Range = {
  from?: InputMaybe<Scalars['Int']>;
  to?: InputMaybe<Scalars['Int']>;
};

export enum Sex {
  Female = 'Female',
  Intersex = 'Intersex',
  Male = 'Male'
}

export type SurveyLink = {
  __typename?: 'SurveyLink';
  completedSurveys: Array<CompleteSurvey>;
  id: SurveyLinkId;
  surveyTemplate: SurveyTemplate;
};

export type SurveyLinkId = {
  __typename?: 'SurveyLinkId';
  value: Scalars['UUID'];
};

export type SurveyLinkIdInput = {
  value?: InputMaybe<Scalars['UUID']>;
};

export type SurveyTemplate = {
  __typename?: 'SurveyTemplate';
  calculations: Array<SurveyTemplateCalculation>;
  id: SurveyTemplateId;
  sections: Array<SurveyTemplateSection>;
  subtitle?: Maybe<Scalars['String']>;
  title: Scalars['String'];
};

export type SurveyTemplateCalculation = {
  __typename?: 'SurveyTemplateCalculation';
  calculation?: Maybe<Scalars['String']>;
  id: SurveyTemplateCalculationId;
  index: Scalars['Int'];
  inputType?: Maybe<SurveyTemplateInputType>;
  label?: Maybe<Scalars['String']>;
  showOnComplete?: Maybe<Scalars['Boolean']>;
};

export type SurveyTemplateCalculationId = {
  __typename?: 'SurveyTemplateCalculationId';
  value: Scalars['UUID'];
};

export enum SurveyTemplateCategory {
  Body = 'Body',
  Date = 'Date',
  Title = 'Title'
}

export type SurveyTemplateId = {
  __typename?: 'SurveyTemplateId';
  value: Scalars['UUID'];
};

export type SurveyTemplateIdInput = {
  value: Scalars['UUID'];
};

export type SurveyTemplateInput = {
  __typename?: 'SurveyTemplateInput';
  calculationId?: Maybe<Scalars['Int']>;
  category?: Maybe<SurveyTemplateCategory>;
  id: SurveyTemplateInputId;
  label: Scalars['String'];
  options?: Maybe<Array<Scalars['String']>>;
  placeholder?: Maybe<Scalars['String']>;
  required: Scalars['Boolean'];
  type: SurveyTemplateInputType;
  uploadAccept?: Maybe<TemplateInputUploadType>;
  uploadMultiple?: Maybe<Scalars['Boolean']>;
};

export type SurveyTemplateInputId = {
  __typename?: 'SurveyTemplateInputId';
  value: Scalars['UUID'];
};

export enum SurveyTemplateInputType {
  Date = 'Date',
  MultiSelect = 'MultiSelect',
  Number = 'Number',
  Paragraph = 'Paragraph',
  SingleSelect = 'SingleSelect',
  Text = 'Text',
  Title = 'Title',
  Upload = 'Upload'
}

export type SurveyTemplateLinkIdInput = {
  value?: InputMaybe<Scalars['UUID']>;
};

export type SurveyTemplateSection = {
  __typename?: 'SurveyTemplateSection';
  calculationId?: Maybe<Scalars['Int']>;
  id: SurveyTemplateSectionId;
  inputs: Array<SurveyTemplateInput>;
  subtitle?: Maybe<Scalars['String']>;
  title: Scalars['String'];
};

export type SurveyTemplateSectionId = {
  __typename?: 'SurveyTemplateSectionId';
  value: Scalars['UUID'];
};

export type SurveyTemplateSectionIdInput = {
  calculationId?: InputMaybe<Scalars['Int']>;
  value: Scalars['UUID'];
};

export type SurveyTemplateSectionInputIdInput = {
  value: Scalars['UUID'];
};

export enum TemplateInputUploadType {
  All = 'All',
  Documents = 'Documents',
  Images = 'Images'
}

export type UserAccountDetails = {
  __typename?: 'UserAccountDetails';
  email?: Maybe<Scalars['String']>;
  isRegistered: Scalars['Boolean'];
};
