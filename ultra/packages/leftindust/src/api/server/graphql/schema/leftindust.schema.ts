export type Maybe<T> = T | undefined;
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
  /** Long type */
  Long: any;
  /** A type representing a formatted java.util.UUID */
  UUID: any;
  /** Federation type representing set of fields */
  _FieldSet: any;
};






export type Address = {
  __typename?: 'Address';
  address: Scalars['String'];
  addressType?: Maybe<AddressType>;
  city: Scalars['String'];
  country: Country;
  postalCode: Scalars['String'];
  province: Scalars['String'];
};

export type AddressEditInput = {
  address?: Maybe<Scalars['String']>;
  addressType?: Maybe<AddressType>;
  city?: Maybe<Scalars['String']>;
  country?: Maybe<Country>;
  postalCode?: Maybe<Scalars['String']>;
  province?: Maybe<Scalars['String']>;
};

export type AddressInput = {
  address: Scalars['String'];
  addressType: AddressType;
  city: Scalars['String'];
  country: Country;
  postalCode: Scalars['String'];
  province: Scalars['String'];
};

export enum AddressType {
  Home = 'Home',
  Work = 'Work',
  School = 'School',
  Apartment = 'Apartment',
  Other = 'Other'
}

export type AssignedSurvey = {
  __typename?: 'AssignedSurvey';
  id: AssignedSurveyId;
  patient: Patient;
  surveyTemplate: GraphQlFormTemplate;
};

export type AssignedSurveyId = {
  __typename?: 'AssignedSurveyId';
  id: Scalars['UUID'];
};

export type CanadianProvince = GraphQlProvince & {
  __typename?: 'CanadianProvince';
  asShortStrings: Array<Scalars['String']>;
  asStrings: Array<Scalars['String']>;
  longToShort: Scalars['String'];
  shortToLong: Scalars['String'];
};


export type CanadianProvinceLongToShortArgs = {
  province: Scalars['String'];
};


export type CanadianProvinceShortToLongArgs = {
  province: Scalars['String'];
};

export type CaseAgnosticStringFilterInput = {
  contains?: Maybe<Scalars['String']>;
  endsWith?: Maybe<Scalars['String']>;
  eq?: Maybe<Scalars['String']>;
  ne?: Maybe<Scalars['String']>;
  notContain?: Maybe<Scalars['String']>;
  notEndWith?: Maybe<Scalars['String']>;
  notStartWith?: Maybe<Scalars['String']>;
  startsWith?: Maybe<Scalars['String']>;
  strict: Scalars['Boolean'];
};

export type Clinic = {
  __typename?: 'Clinic';
  address: Address;
  cid: ClinicId;
  doctors: Array<Doctor>;
};

export type ClinicEditInput = {
  address?: Maybe<AddressEditInput>;
  cid: ClinicIdInput;
  /** passing null will not update, to clear: pass an empty list */
  doctors?: Maybe<Array<DoctorIdInput>>;
  name?: Maybe<Scalars['String']>;
};

export type ClinicId = {
  __typename?: 'ClinicId';
  id: Scalars['UUID'];
};

export type ClinicIdInput = {
  id: Scalars['UUID'];
};

export type ClinicInput = {
  address: AddressInput;
  /** defaults to empty list */
  doctors?: Maybe<Array<DoctorIdInput>>;
  name: Scalars['String'];
};

export enum ConvertTarget {
  Json = 'Json',
  Csv = 'Csv'
}

export enum Country {
  Canada = 'Canada'
}

export enum Crud {
  Create = 'CREATE',
  Read = 'READ',
  Update = 'UPDATE',
  Delete = 'DELETE'
}

export enum DataType {
  SingleMuliSelect = 'SingleMuliSelect',
  MultiMuliSelect = 'MultiMuliSelect',
  Text = 'Text',
  Integer = 'Integer',
  Date = 'Date',
  Float = 'Float'
}

export type Date = {
  __typename?: 'Date';
  day: Scalars['Int'];
  month: Month;
  year: Scalars['Int'];
  /** @deprecated will be removed before 1.0, replace with using the information I send you */
  toUtcTime: UtcTime;
};

export type DateFilterInput = {
  after?: Maybe<DateInput>;
  before?: Maybe<DateInput>;
  strict: Scalars['Boolean'];
};

export type DateInput = {
  day: Scalars['Int'];
  month: Month;
  year: Scalars['Int'];
};

export enum DayOfWeek {
  Mon = 'Mon',
  Tue = 'Tue',
  Wed = 'Wed',
  Thu = 'Thu',
  Fri = 'Fri',
  Sat = 'Sat',
  Sun = 'Sun'
}

export type Doctor = Person & {
  __typename?: 'Doctor';
  addresses: Array<Address>;
  dateOfBirth?: Maybe<Date>;
  did: DoctorId;
  emails: Array<Email>;
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  phones: Array<Phone>;
  thumbnail?: Maybe<Scalars['String']>;
  title?: Maybe<Scalars['String']>;
  clinic: Clinic;
  events: Array<Event>;
  patients: Array<Patient>;
  schedule: Array<Event>;
  user?: Maybe<User>;
};


export type DoctorScheduleArgs = {
  from: UtcTimeInput;
  to: UtcTimeInput;
};

export type DoctorEditInput = {
  addresses?: Maybe<Array<AddressInput>>;
  /** setting to null will remove the doctor from the clinic */
  clinics?: Maybe<Array<ClinicIdInput>>;
  dateOfBirth?: Maybe<DateInput>;
  did: DoctorIdInput;
  emails?: Maybe<Array<EmailInput>>;
  nameInfo?: Maybe<NameEditInput>;
  patients?: Maybe<Array<PatientIdInput>>;
  phones?: Maybe<Array<PhoneInput>>;
  title?: Maybe<Scalars['String']>;
  userUid?: Maybe<Scalars['String']>;
};

export type DoctorExampleInput = {
  firstName?: Maybe<CaseAgnosticStringFilterInput>;
  lastName?: Maybe<CaseAgnosticStringFilterInput>;
  strict: Scalars['Boolean'];
};

export type DoctorId = {
  __typename?: 'DoctorId';
  id: Scalars['UUID'];
};

export type DoctorIdInput = {
  id: Scalars['UUID'];
};

export type DoctorInput = {
  addresses?: Maybe<Array<AddressInput>>;
  clinic?: Maybe<ClinicIdInput>;
  dateOfBirth?: Maybe<DateInput>;
  emails?: Maybe<Array<EmailInput>>;
  nameInfo: NameInput;
  patients?: Maybe<Array<PatientIdInput>>;
  phones?: Maybe<Array<PhoneInput>>;
  title?: Maybe<Scalars['String']>;
  user?: Maybe<UserInput>;
};

export type Email = {
  __typename?: 'Email';
  email: Scalars['String'];
  type: EmailType;
};

export type EmailInput = {
  email: Scalars['String'];
  type: EmailType;
};

export enum EmailType {
  Work = 'Work',
  Personal = 'Personal',
  School = 'School',
  Other = 'Other'
}

export type EmergencyContact = Person & {
  __typename?: 'EmergencyContact';
  emails: Array<Email>;
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  phones: Array<Phone>;
  relationship: Relationship;
  /** a base64 icon. Cannot be over 10 000 characters when base64 encoded */
  thumbnail?: Maybe<Scalars['String']>;
};

/** either phones or emails MUST contain at least one element */
export type EmergencyContactInput = {
  /** defaults to empty list */
  emails?: Maybe<Array<EmailInput>>;
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  /** defaults to empty list */
  phones?: Maybe<Array<PhoneInput>>;
  relationship: Relationship;
};

export enum Ethnicity {
  AmericanAboriginal = 'AmericanAboriginal',
  Asian = 'Asian',
  Black = 'Black',
  Hispanic = 'Hispanic',
  PacificIslander = 'PacificIslander',
  White = 'White'
}

export type Event = {
  __typename?: 'Event';
  allDay: Scalars['Boolean'];
  description?: Maybe<Scalars['String']>;
  eid: EventId;
  endTime?: Maybe<UtcTime>;
  reoccurrence?: Maybe<Recurrence>;
  startTime?: Maybe<UtcTime>;
  title: Scalars['String'];
  doctors: Array<Doctor>;
  patients: Array<Patient>;
  visit?: Maybe<Visit>;
};

/**
 * passing explicit nulls will unset the value (provided that leaves the event in a valid state) and not setting a value
 *  will leave it unchanged. The same consistency rules around start/end/allDay still apply and it is the callers
 *  responsibility to pass a valid series of arguments
 */
export type EventEditInput = {
  /**  weather the event is all day or not. Cannot be null and must be false if and only if end is not null */
  allDay?: Maybe<Scalars['Boolean']>;
  /** The new description. Can be set to null to remove the old description */
  description?: Maybe<Scalars['String']>;
  /** the doctors attached to this event. if set to null, no changes will be made, to clear the list you must pass an empty list */
  doctors?: Maybe<Array<DoctorIdInput>>;
  /** the id of the event you are looking to edit */
  eid: EventIdInput;
  /** The new end of the event. Can only be null if the event is allDay */
  end?: Maybe<UtcTimeInput>;
  /** the patients attached to this event. see doctors for nullability rules */
  patients?: Maybe<Array<PatientIdInput>>;
  /** the rules surrounding recurrence of the event, Can be set to null. Setting this value will overwrite the whole recurrence object, leaving fields blank will __not__ retain prior values */
  recurrence?: Maybe<RecurrenceInput>;
  /** The new start of the event. Cannot be null */
  start?: Maybe<UtcTimeInput>;
  /** The new title of the event. Cannot be set to null */
  title?: Maybe<Scalars['String']>;
};

export type EventId = {
  __typename?: 'EventId';
  id: Scalars['UUID'];
};

export type EventIdInput = {
  id: Scalars['UUID'];
};

export type EventInput = {
  allDay: Scalars['Boolean'];
  description?: Maybe<Scalars['String']>;
  doctors?: Maybe<Array<DoctorIdInput>>;
  /** UTC */
  end: UtcTimeInput;
  patients?: Maybe<Array<PatientIdInput>>;
  recurrence?: Maybe<RecurrenceInput>;
  /** UTC */
  start: UtcTimeInput;
  title: Scalars['String'];
};

export type FirebaseInfo = {
  __typename?: 'FirebaseInfo';
  displayName?: Maybe<Scalars['String']>;
  email?: Maybe<Scalars['String']>;
  phoneNumber?: Maybe<Scalars['String']>;
  photoUrl?: Maybe<Scalars['String']>;
  tenantId?: Maybe<Scalars['String']>;
  tokensValidAfterTimestamp?: Maybe<Scalars['Long']>;
  uid?: Maybe<Scalars['String']>;
};

export type FormFieldInput = {
  dataType: DataType;
  dateLowerBound?: Maybe<DateInput>;
  dateUpperBound?: Maybe<DateInput>;
  floatLowerBound?: Maybe<Scalars['Float']>;
  floatUpperBound?: Maybe<Scalars['Float']>;
  intLowerBound?: Maybe<Scalars['Int']>;
  intUpperBound?: Maybe<Scalars['Int']>;
  jsonMetaData?: Maybe<Scalars['String']>;
  multiSelectPossibilities?: Maybe<Array<Scalars['String']>>;
  number: Scalars['Int'];
  textRegex?: Maybe<Scalars['String']>;
  title: Scalars['String'];
};

export type FormSectionInput = {
  /** Max 50 000 chars */
  description?: Maybe<Scalars['String']>;
  /** Note that I do not provide a stable order to these fields */
  fields: Array<FormFieldInput>;
  name: Scalars['String'];
  number: Scalars['Int'];
};

export type FormTemplateId = {
  __typename?: 'FormTemplateId';
  id: Scalars['UUID'];
};

export type FormTemplateIdInput = {
  id: Scalars['UUID'];
};

export type FormTemplateInput = {
  name: Scalars['String'];
  sections: Array<FormSectionInput>;
};

export type FoundationIcdCodeInput = {
  /** full A ICD-11 Foundation Code URL */
  url: Scalars['String'];
};

export type GraphQlFormData = {
  __typename?: 'GraphQLFormData';
  data: Scalars['String'];
  patient: Patient;
};

export type GraphQlFormSection = {
  __typename?: 'GraphQLFormSection';
  /** Max 50 000 chars */
  description?: Maybe<Scalars['String']>;
  /** Note that I do not provide a stable order to these fields */
  fields: Array<GraphQlFormField>;
  name: Scalars['String'];
  number: Scalars['Int'];
};

export type GraphQlFormTemplate = {
  __typename?: 'GraphQLFormTemplate';
  id: FormTemplateId;
  name: Scalars['String'];
  sections: Array<GraphQlFormSection>;
};

export type GraphQlPermissionInput = {
  columnName?: Maybe<Scalars['String']>;
  endTime?: Maybe<UtcTimeInput>;
  permissionType: Crud;
  referencedTableName: Tables;
  rowId?: Maybe<Scalars['UUID']>;
  startTime?: Maybe<UtcTimeInput>;
};

export type GraphQlProvince = {
  asShortStrings: Array<Scalars['String']>;
  asStrings: Array<Scalars['String']>;
};

export type GraphQlRecordInput = {
  jsonBlob: Scalars['String'];
  patient: PatientIdInput;
  recordType: RecordType;
};

export type GraphQlVisitEditInput = {
  /** the new description, explicitly set to null to clear */
  description?: Maybe<Scalars['String']>;
  /** the new event to attach this to */
  eid?: Maybe<EventIdInput>;
  /** the icd codes for this visit, set to empty array to clear */
  foundationIcdCodes?: Maybe<Array<FoundationIcdCodeInput>>;
  /** the new title, explicitly set to null to clear */
  title?: Maybe<Scalars['String']>;
  /** the id of the visit you wish to edit */
  vid: VisitIdInput;
};

export type GraphQlFormField = {
  __typename?: 'GraphQlFormField';
  dataType: DataType;
  dateLowerBound?: Maybe<Date>;
  dateUpperBound?: Maybe<Date>;
  floatLowerBound?: Maybe<Scalars['Float']>;
  floatUpperBound?: Maybe<Scalars['Float']>;
  intLowerBound?: Maybe<Scalars['Int']>;
  intUpperBound?: Maybe<Scalars['Int']>;
  jsonMetaData?: Maybe<Scalars['String']>;
  multiSelectPossibilities?: Maybe<Array<Scalars['String']>>;
  number: Scalars['Int'];
  textRegex?: Maybe<Scalars['String']>;
  title: Scalars['String'];
};

export type Group = {
  __typename?: 'Group';
  gid: GroupId;
  name: Scalars['String'];
};

export type GroupId = {
  __typename?: 'GroupId';
  id: Scalars['UUID'];
};

export type GroupIdInput = {
  id: Scalars['UUID'];
};

export type GroupInput = {
  name: Scalars['String'];
};

export enum IcdEntityType {
  Zero = 'Zero',
  One = 'One',
  Two = 'Two'
}

export type IcdFoundationEntity = {
  __typename?: 'IcdFoundationEntity';
  browserUrl?: Maybe<Scalars['String']>;
  child?: Maybe<Array<Scalars['String']>>;
  context?: Maybe<Scalars['String']>;
  definition?: Maybe<IcdLanguageSpecificText>;
  exclusion?: Maybe<Array<IcdTerm>>;
  fullySpecifiedName?: Maybe<IcdLanguageSpecificText>;
  id?: Maybe<Scalars['String']>;
  inclusion?: Maybe<Array<IcdTerm>>;
  longDefinition?: Maybe<IcdLanguageSpecificText>;
  narrowerTerm?: Maybe<Array<IcdTerm>>;
  parent?: Maybe<Array<Scalars['String']>>;
  synonym?: Maybe<Array<IcdTerm>>;
  title?: Maybe<IcdLanguageSpecificText>;
};

export enum IcdGuessType {
  Zero = 'Zero',
  One = 'One',
  Two = 'Two'
}

export type IcdGuessWord = {
  __typename?: 'IcdGuessWord';
  dontChangeResult: Scalars['String'];
  label?: Maybe<Scalars['String']>;
};

export type IcdLanguageSpecificText = {
  __typename?: 'IcdLanguageSpecificText';
  language?: Maybe<Scalars['String']>;
  value?: Maybe<Scalars['String']>;
};

export type IcdLinearizationEntity = IcdReallySimpleEntity & {
  __typename?: 'IcdLinearizationEntity';
  blockId?: Maybe<Scalars['String']>;
  browserUrl?: Maybe<Scalars['String']>;
  child?: Maybe<Array<Scalars['String']>>;
  classKind?: Maybe<Scalars['String']>;
  code?: Maybe<Scalars['String']>;
  codeRange?: Maybe<Scalars['String']>;
  codingNote?: Maybe<IcdLanguageSpecificText>;
  context?: Maybe<Scalars['String']>;
  definition?: Maybe<IcdLanguageSpecificText>;
  description?: Maybe<Scalars['String']>;
  exclusion?: Maybe<Array<IcdTerm>>;
  foundationChildElsewhere?: Maybe<Array<IcdTerm>>;
  fullySpecifiedName?: Maybe<IcdLanguageSpecificText>;
  id?: Maybe<Scalars['String']>;
  inclusion?: Maybe<Array<IcdTerm>>;
  indexTerm?: Maybe<Array<IcdTerm>>;
  languageTitle?: Maybe<IcdLanguageSpecificText>;
  longDefinition?: Maybe<IcdLanguageSpecificText>;
  parent?: Maybe<Array<Scalars['String']>>;
  postcoordinationScale?: Maybe<Array<Maybe<IcdPostCoordinationScaleInfo>>>;
  source?: Maybe<Scalars['String']>;
  title?: Maybe<Scalars['String']>;
};

export type IcdListFilterInput = {
  includes?: Maybe<Array<FoundationIcdCodeInput>>;
  notIncludes?: Maybe<Array<FoundationIcdCodeInput>>;
  strict: Scalars['Boolean'];
};

export type IcdPostCoordinationScaleInfo = {
  __typename?: 'IcdPostCoordinationScaleInfo';
  allowMultipleValues: Scalars['String'];
  axisName?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  requiredPostcoordination: Scalars['Boolean'];
  scaleEntity: Array<Scalars['String']>;
};

export enum IcdPostcoordinationAvailability {
  One = 'One',
  Two = 'Two',
  Three = 'Three'
}

export type IcdReallySimpleEntity = {
  code?: Maybe<Scalars['String']>;
  description?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  title?: Maybe<Scalars['String']>;
};

export type IcdSearchResult = {
  __typename?: 'IcdSearchResult';
  destinationEntities?: Maybe<Array<IcdSimpleEntity>>;
  error: Scalars['Boolean'];
  errorMessage?: Maybe<Scalars['String']>;
  guessType?: Maybe<IcdGuessType>;
  resultChopped: Scalars['Boolean'];
  uniqueSearchId: Scalars['Boolean'];
  wordSuggestionsChopped: Scalars['Boolean'];
  words?: Maybe<Array<IcdGuessWord>>;
};

export type IcdSimpleEntity = IcdReallySimpleEntity & {
  __typename?: 'IcdSimpleEntity';
  chapter?: Maybe<Scalars['String']>;
  code?: Maybe<Scalars['String']>;
  descendants: Array<IcdSimpleEntity>;
  description?: Maybe<Scalars['String']>;
  entityType: IcdEntityType;
  hasCodingNote: Scalars['Boolean'];
  hasMaternalChapterLink: Scalars['Boolean'];
  id: Scalars['String'];
  important: Scalars['Boolean'];
  isLeaf: Scalars['Boolean'];
  isResidualOther: Scalars['Boolean'];
  isResidualUnspecified: Scalars['Boolean'];
  matchingPVs: Array<IcdSimplePropertyValue>;
  postcoordinationAvailability: IcdPostcoordinationAvailability;
  propertiesTruncated: Scalars['Boolean'];
  score: Scalars['Float'];
  stemId?: Maybe<Scalars['String']>;
  theCode?: Maybe<Scalars['String']>;
  title?: Maybe<Scalars['String']>;
  titleIsASearchResult: Scalars['Boolean'];
  titleIsTopScore: Scalars['Boolean'];
  entity?: Maybe<IcdFoundationEntity>;
  tagTitle?: Maybe<Scalars['String']>;
  urlId?: Maybe<Scalars['String']>;
};


export type IcdSimpleEntityTagTitleArgs = {
  withTags?: Maybe<Scalars['Boolean']>;
};


export type IcdSimpleEntityUrlIdArgs = {
  asUrl?: Maybe<Scalars['Boolean']>;
};

export type IcdSimplePropertyValue = {
  __typename?: 'IcdSimplePropertyValue';
  important?: Maybe<Scalars['Boolean']>;
  label?: Maybe<Scalars['String']>;
  propertyId?: Maybe<Scalars['String']>;
  score?: Maybe<Scalars['Float']>;
};

export type IcdTerm = {
  __typename?: 'IcdTerm';
  foundationReference?: Maybe<Scalars['String']>;
  label: IcdLanguageSpecificText;
  linearizationReference?: Maybe<Scalars['String']>;
};


export enum Month {
  Jan = 'Jan',
  Feb = 'Feb',
  Mar = 'Mar',
  Apr = 'Apr',
  May = 'May',
  Jun = 'Jun',
  Jul = 'Jul',
  Aug = 'Aug',
  Sep = 'Sep',
  Oct = 'Oct',
  Nov = 'Nov',
  Dec = 'Dec'
}

export type Mutation = {
  __typename?: 'Mutation';
  addClinic: Clinic;
  editClinic: Clinic;
  addDoctor: Doctor;
  editDoctor: Doctor;
  addEvent: Event;
  /** edits the event referenced by eid */
  editEvent: Event;
  /** edits the event referenced by eid */
  editRecurringEvent: Event;
  addSurveyTemplate: GraphQlFormTemplate;
  assignSurvey: Array<Patient>;
  submitSurvey: GraphQlFormData;
  addGroup: Group;
  /**
   * adds a new patient and connects them to already existing doctors and contacts
   *         contacts and doctors default to empty lists
   */
  addPatient: Patient;
  /** updates a patient by their pid, only the not null fields are updated, pid MUST be defined */
  updatePatient: Patient;
  addPermission: Permission;
  addRecord: Record;
  addUser: User;
  editUser: User;
  addVisit: Visit;
  editVisit: Visit;
};


export type MutationAddClinicArgs = {
  clinic: ClinicInput;
};


export type MutationEditClinicArgs = {
  clinic: ClinicEditInput;
};


export type MutationAddDoctorArgs = {
  doctor: DoctorInput;
};


export type MutationEditDoctorArgs = {
  doctor: DoctorEditInput;
};


export type MutationAddEventArgs = {
  event: EventInput;
};


export type MutationEditEventArgs = {
  event: EventEditInput;
};


export type MutationEditRecurringEventArgs = {
  event: EventEditInput;
  recurrenceSettings: RecurrenceEditSettingsInput;
};


export type MutationAddSurveyTemplateArgs = {
  surveyTemplate: FormTemplateInput;
};


export type MutationAssignSurveyArgs = {
  patients: Array<PatientIdInput>;
  survey: FormTemplateIdInput;
};


export type MutationSubmitSurveyArgs = {
  patient: PatientIdInput;
  surveyJson: Scalars['String'];
};


export type MutationAddGroupArgs = {
  group: GroupInput;
};


export type MutationAddPatientArgs = {
  patient: PatientInput;
};


export type MutationUpdatePatientArgs = {
  patient: PatientEditInput;
};


export type MutationAddPermissionArgs = {
  groupId?: Maybe<GroupIdInput>;
  userUid?: Maybe<Scalars['String']>;
  permission: GraphQlPermissionInput;
};


export type MutationAddRecordArgs = {
  record: GraphQlRecordInput;
};


export type MutationAddUserArgs = {
  user: UserInput;
};


export type MutationEditUserArgs = {
  user: UserEditInput;
};


export type MutationAddVisitArgs = {
  visit: VisitInput;
};


export type MutationEditVisitArgs = {
  visit: GraphQlVisitEditInput;
};

export type NameEditInput = {
  /** setting firstName to null will have no effect on updates */
  firstName?: Maybe<Scalars['String']>;
  /** setting lastName to null will have no effect on updates */
  lastName?: Maybe<Scalars['String']>;
  /** setting middleName to null will remove a prior middleName */
  middleName?: Maybe<Scalars['String']>;
};

export type NameInfo = {
  __typename?: 'NameInfo';
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
};

export type NameInput = {
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
};

export type Patient = Person & {
  __typename?: 'Patient';
  addresses: Array<Address>;
  dateOfBirth: Date;
  emails: Array<Email>;
  ethnicity?: Maybe<Ethnicity>;
  firstName: Scalars['String'];
  gender: Scalars['String'];
  insuranceNumber?: Maybe<Scalars['String']>;
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  phones: Array<Phone>;
  pid: PatientId;
  sex: Sex;
  /** a base64 icon. Cannot be over 10 000 characters when base64 encoded */
  thumbnail?: Maybe<Scalars['String']>;
  assignedForms: Array<AssignedSurvey>;
  contacts: Array<Person>;
  doctors: Array<Doctor>;
  events: Array<Event>;
  user?: Maybe<User>;
  visits: Array<Visit>;
};

export type PatientEditInput = {
  /** setting addresses to null will have no effect on update. to remove, pass an emptyList */
  addresses?: Maybe<Array<AddressInput>>;
  /** setting dateOfBirth to null will have no effect on update */
  dateOfBirth?: Maybe<DateInput>;
  /** setting doctors to null will have no effect on update. to remove, pass an emptyList */
  doctors?: Maybe<Array<DoctorIdInput>>;
  /** setting emails to null will have no effect on update. to remove, pass an emptyList */
  emails?: Maybe<Array<EmailInput>>;
  /** setting emergencyContact to null will have no effect on update. to remove, pass an emptyList */
  emergencyContacts?: Maybe<Array<EmergencyContactInput>>;
  /** setting to null will delete prior ethnicity, leaving blank will keep old ethnicity */
  ethnicity?: Maybe<Ethnicity>;
  /** setting gender to null will have no effect on update */
  gender?: Maybe<Scalars['String']>;
  /** setting to null will delete prior insuranceNumber, leaving blank will keep old insuranceNumber */
  insuranceNumber?: Maybe<Scalars['ID']>;
  /** setting nameInfoEditInput to null will have no effect on update */
  nameInfo?: Maybe<NameEditInput>;
  /** setting phoneNumbers to null will have no effect on update. to remove, pass an emptyList */
  phones?: Maybe<Array<PhoneInput>>;
  /** required. Determines what patient is being updated */
  pid: PatientIdInput;
  /** setting sex to null will have no effect on update */
  sex?: Maybe<Sex>;
  /** setting to null will delete prior thumbnail, leaving blank will keep old thumbail. Cannot be over 10 000 characters */
  thumbnail?: Maybe<Scalars['String']>;
};

export type PatientExampleInput = {
  dateOfBirth?: Maybe<DateFilterInput>;
  firstName?: Maybe<CaseAgnosticStringFilterInput>;
  icdCodes?: Maybe<IcdListFilterInput>;
  insuranceNumber?: Maybe<WhiteSpaceAgnosticStringFilterInput>;
  lastName?: Maybe<CaseAgnosticStringFilterInput>;
  strict: Scalars['Boolean'];
};

export type PatientId = {
  __typename?: 'PatientId';
  id: Scalars['UUID'];
};

export type PatientIdInput = {
  id: Scalars['UUID'];
};

/**
 * The input side of Patient. Note that relations to other types are passed as ID's
 * if you want to clear a list, pass an empty list, explicitly setting a list to null or leaving blank will have no effect on
 * update operations and will result in empty list for create operations.
 */
export type PatientInput = {
  /** defaults to emptyList */
  addresses?: Maybe<Array<AddressInput>>;
  /** required */
  dateOfBirth: DateInput;
  /** defaults to emptyList */
  doctors?: Maybe<Array<DoctorIdInput>>;
  /** defaults to emptyList */
  emails?: Maybe<Array<EmailInput>>;
  /** defaults to emptyList */
  emergencyContacts?: Maybe<Array<EmergencyContactInput>>;
  /** defaults to null */
  ethnicity?: Maybe<Ethnicity>;
  /** defaults to sex */
  gender?: Maybe<Scalars['String']>;
  /** defaults to null */
  insuranceNumber?: Maybe<Scalars['ID']>;
  /** required */
  nameInfo: NameInput;
  /** defaults to emptyList */
  phones?: Maybe<Array<PhoneInput>>;
  /** required */
  sex: Sex;
  /** base64 representation of an image. cannot be over 10 000 characters */
  thumbnail?: Maybe<Scalars['String']>;
};

export type Permission = {
  __typename?: 'Permission';
  columnName?: Maybe<Scalars['String']>;
  endTime?: Maybe<UtcTime>;
  permissionType: Crud;
  pid: PermissionId;
  referencedTableName: Tables;
  rowId?: Maybe<Scalars['UUID']>;
  startTime?: Maybe<UtcTime>;
};

export type PermissionId = {
  __typename?: 'PermissionId';
  id: Scalars['UUID'];
};

export type Permissions = {
  __typename?: 'Permissions';
  groupPerms: Array<Permission>;
  userPerms: Array<Permission>;
};

export type Person = {
  emails: Array<Email>;
  firstName: Scalars['String'];
  lastName: Scalars['String'];
  middleName?: Maybe<Scalars['String']>;
  phones: Array<Phone>;
  /** a base64 icon. Cannot be over 10 000 characters when base64 encoded */
  thumbnail?: Maybe<Scalars['String']>;
};

export type Phone = {
  __typename?: 'Phone';
  number: Scalars['String'];
  type: PhoneType;
};

export type PhoneInput = {
  number: Scalars['String'];
  type: PhoneType;
};

export enum PhoneType {
  Work = 'Work',
  Cell = 'Cell',
  Home = 'Home',
  Pager = 'Pager',
  Other = 'Other'
}

export type Query = {
  __typename?: 'Query';
  clinic: Clinic;
  getContactsByPatient: Array<Person>;
  convert: Scalars['String'];
  country: GraphQlProvince;
  /** only pass one variable */
  doctors: Array<Doctor>;
  events: Array<Event>;
  /** fetch survey templates by one getting a range or the survey id */
  surveys: Array<GraphQlFormTemplate>;
  groups: Array<Group>;
  mockingbirdIsAlive: Scalars['Boolean'];
  icd: IcdLinearizationEntity;
  icds: Array<IcdLinearizationEntity>;
  searchIcd: IcdSearchResult;
  patients: Array<Patient>;
  permissions: Permissions;
  records: Array<Record>;
  /**
   * gets all users from firebase, you can
   * filter out already registered
   * users by setting filterRegistered
   * to true (defaults to false)
   */
  firebaseUsers: Array<FirebaseInfo>;
  /** attempts to find the mediq-registered user by uid, if the user does not exist in the DB */
  user: User;
  /** returns a list of users, only one argument should be specified */
  users: Array<User>;
  visits: Array<Visit>;
  _service?: Maybe<_Service>;
};


export type QueryClinicArgs = {
  cid: ClinicIdInput;
};


export type QueryGetContactsByPatientArgs = {
  pid: PatientIdInput;
};


export type QueryConvertArgs = {
  json: Scalars['String'];
  target: ConvertTarget;
};


export type QueryCountryArgs = {
  country: Country;
};


export type QueryDoctorsArgs = {
  dids?: Maybe<Array<DoctorIdInput>>;
  pid?: Maybe<PatientIdInput>;
  range?: Maybe<RangeInput>;
  example?: Maybe<DoctorExampleInput>;
};


export type QueryEventsArgs = {
  events?: Maybe<Array<EventIdInput>>;
  doctors?: Maybe<Array<DoctorIdInput>>;
  patients?: Maybe<Array<PatientIdInput>>;
  range?: Maybe<TimeRangeInput>;
};


export type QuerySurveysArgs = {
  range?: Maybe<RangeInput>;
  surveys?: Maybe<Array<FormTemplateIdInput>>;
};


export type QueryGroupsArgs = {
  gids?: Maybe<Array<GroupIdInput>>;
  range?: Maybe<RangeInput>;
};


export type QueryIcdArgs = {
  icdCode: Scalars['String'];
};


export type QueryIcdsArgs = {
  icdCodes: Array<Scalars['String']>;
};


export type QuerySearchIcdArgs = {
  query: Scalars['String'];
  flexiSearch?: Maybe<Scalars['Boolean']>;
  flatResults?: Maybe<Scalars['Boolean']>;
};


export type QueryPatientsArgs = {
  range?: Maybe<RangeInput>;
  pids?: Maybe<Array<PatientIdInput>>;
  sortedBy?: Maybe<SortableField>;
  example?: Maybe<PatientExampleInput>;
};


export type QueryPermissionsArgs = {
  uid: Scalars['String'];
};


export type QueryRecordsArgs = {
  pid?: Maybe<PatientIdInput>;
  rids?: Maybe<Array<RecordIdInput>>;
};


export type QueryFirebaseUsersArgs = {
  range?: Maybe<RangeInput>;
  filterRegistered?: Maybe<Scalars['Boolean']>;
};


export type QueryUserArgs = {
  uid: Scalars['ID'];
};


export type QueryUsersArgs = {
  range?: Maybe<RangeInput>;
  uniqueIds?: Maybe<Array<Scalars['ID']>>;
};


export type QueryVisitsArgs = {
  vids?: Maybe<Array<VisitIdInput>>;
  pid?: Maybe<PatientIdInput>;
  did?: Maybe<DoctorIdInput>;
  strict: Scalars['Boolean'];
};

export type RangeInput = {
  from: Scalars['Int'];
  to: Scalars['Int'];
};

export type Record = {
  __typename?: 'Record';
  creationDate: UtcTime;
  jsonBlob: Scalars['String'];
  rid: RecordId;
  type: RecordType;
  patient: Patient;
};

export type RecordId = {
  __typename?: 'RecordId';
  id: Scalars['UUID'];
};

export type RecordIdInput = {
  id: Scalars['UUID'];
};

export enum RecordType {
  Blood = 'Blood',
  Form = 'Form'
}

export type Recurrence = {
  __typename?: 'Recurrence';
  daysOfWeek: Array<DayOfWeek>;
  endDate: Date;
  startDate: Date;
};

/**
 * the date range that the edits will effect the reoccurring event. This allows things such as editing a
 *             single event of a reoccurring event or leaving the past events untouched but editing future ones
 */
export type RecurrenceEditSettingsInput = {
  /** the end of the events that the edit should take place on */
  editEnd: DateInput;
  /** the start of events that the edit should take place on */
  editStart: DateInput;
};

export type RecurrenceInput = {
  daysOfWeek: Array<DayOfWeek>;
  endDate: DateInput;
  startDate: DateInput;
};

export enum Relationship {
  Parent = 'Parent',
  Child = 'Child',
  Guardian = 'Guardian',
  Grandparent = 'Grandparent',
  Grandchild = 'Grandchild',
  Aunt = 'Aunt',
  Uncle = 'Uncle',
  Cousin = 'Cousin',
  Sibling = 'Sibling',
  Partner = 'Partner',
  Other = 'Other'
}

export enum Sex {
  Male = 'Male',
  Female = 'Female',
  Intersex = 'Intersex'
}

export enum SortableField {
  Pid = 'PID',
  FirstName = 'FIRST_NAME',
  LastName = 'LAST_NAME'
}

export enum Tables {
  AccessControlList = 'AccessControlList',
  Action = 'Action',
  Doctor = 'Doctor',
  Group = 'Group',
  Patient = 'Patient',
  User = 'User',
  Visit = 'Visit',
  Record = 'Record',
  EmergencyContact = 'EmergencyContact',
  Event = 'Event',
  IcdCode = 'IcdCode',
  Clinic = 'Clinic',
  Form = 'Form'
}

export type TimeRangeInput = {
  end: UtcTimeInput;
  start: UtcTimeInput;
};

export type TimeZonedTime = {
  __typename?: 'TimeZonedTime';
  timeZone: Scalars['String'];
  unixMilliseconds: Scalars['Long'];
};


export type User = {
  __typename?: 'User';
  group?: Maybe<Group>;
  uid: Scalars['String'];
  doctor?: Maybe<Doctor>;
  firebaseUserInfo: FirebaseInfo;
  hasPermission: Scalars['Boolean'];
  isRegistered: Scalars['Boolean'];
  names?: Maybe<NameInfo>;
  patient?: Maybe<Patient>;
  permissions: Permissions;
};


export type UserHasPermissionArgs = {
  perm: GraphQlPermissionInput;
};

/** edits the user with the given uid, fields left unset will not be edited  */
export type UserEditInput = {
  doctor?: Maybe<DoctorIdInput>;
  group?: Maybe<GroupIdInput>;
  uid: Scalars['String'];
};

export type UserInput = {
  doctor?: Maybe<DoctorIdInput>;
  group?: Maybe<GroupIdInput>;
  nameInfo: NameInput;
  uid: Scalars['String'];
};

export type UtcTime = {
  __typename?: 'UtcTime';
  unixMilliseconds: Scalars['Long'];
  before: Scalars['Boolean'];
  /**
   * the timezone string should follow the format from https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
   * eg. America/Los_Angeles for British Columbia's time zone (generally referred to as PST)
   */
  withRespectTo: TimeZonedTime;
};


export type UtcTimeBeforeArgs = {
  end: UtcTimeInput;
};


export type UtcTimeWithRespectToArgs = {
  timeZone: Scalars['String'];
};

export type UtcTimeInput = {
  unixMilliseconds: Scalars['Long'];
};

export type Visit = {
  __typename?: 'Visit';
  description?: Maybe<Scalars['String']>;
  title?: Maybe<Scalars['String']>;
  vid: VisitId;
  event: Event;
  icds: Array<IcdLinearizationEntity>;
};

export type VisitId = {
  __typename?: 'VisitId';
  id: Scalars['UUID'];
};

export type VisitIdInput = {
  id: Scalars['UUID'];
};

export type VisitInput = {
  description?: Maybe<Scalars['String']>;
  eid: EventIdInput;
  foundationIcdCodes: Array<FoundationIcdCodeInput>;
  title?: Maybe<Scalars['String']>;
};

export type WhiteSpaceAgnosticStringFilterInput = {
  contains?: Maybe<Scalars['String']>;
  endsWith?: Maybe<Scalars['String']>;
  eq?: Maybe<Scalars['String']>;
  ne?: Maybe<Scalars['String']>;
  notContain?: Maybe<Scalars['String']>;
  notEndWith?: Maybe<Scalars['String']>;
  notStartWith?: Maybe<Scalars['String']>;
  startsWith?: Maybe<Scalars['String']>;
  strict: Scalars['Boolean'];
};


export type _Service = {
  __typename?: '_Service';
  sdl: Scalars['String'];
};
