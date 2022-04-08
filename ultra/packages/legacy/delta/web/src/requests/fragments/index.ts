import type {
  User,
  Doctor,
  NameInfo,
  Patient,
  Event,
  Visit,
  Record,
  IcdSimpleEntity,
  IcdLinearizationEntity,
  IcdReallySimpleEntity,
  GraphQlFormTemplate,
  EmergencyContact,
  FirebaseInfo,
  Group,
} from '../../schemas/leftindust.schema';

export type NamesFragment = {
  __typename: NameInfo['__typename'];
  firstName: NameInfo['firstName'];
  middleName?: NameInfo['middleName'];
  lastName: NameInfo['lastName'];
};

export type BasicUserFragment = {
  __typename: User['__typename'];
  uid: User['uid'];
  isRegistered: User['isRegistered'];
  group: User['group'];
  firebaseUserInfo: {
    email: User['firebaseUserInfo']['email'];
  };
};

export type BasicDoctorFragment = NamesFragment & {
  __typename: Doctor['__typename'];
  title: Doctor['title'];
  did: Doctor['did'];
  emails: Doctor['emails'];
};

export type BasicPatientFragment = NamesFragment & {
  __typename: Patient['__typename'];
  dateOfBirth: Patient['dateOfBirth'];
  pid: Patient['pid'];
  sex: Patient['sex'];
  emails: Patient['emails'];
};

export type PatientInsuranceFragment = {
  __typename: Patient['__typename'];
  insuranceNumber: Patient['insuranceNumber'];
};

export type BasicEventFragment = {
  __typename: Event['__typename'];
  title: Event['title'];
  startTime: Event['startTime'];
  endTime: Event['endTime'];
  allDay: Event['allDay'];
  description: Event['description'];
  eid: Event['eid'];
  visit: Event['visit'];
};

export type BasicIcdFragment = {
  __typename: NonNullable<IcdSimpleEntity['__typename'] | IcdLinearizationEntity['__typename']>;
  id: NonNullable<IcdReallySimpleEntity['id']>;
  title: IcdReallySimpleEntity['title'];
  description: IcdReallySimpleEntity['description'];
  code: IcdReallySimpleEntity['code'];
}

export type BasicVisitFragment = {
  __typename: Visit['__typename'];
  vid: Visit['vid'];
  title: Visit['title'];
  description: Visit['description'];
  icds: BasicIcdFragment[];
  event: {
    __typename: Visit['event']['__typename'];
    eid: Visit['event']['eid'];
    startTime: Visit['event']['startTime'];
    endTime: Visit['event']['endTime'];
    allDay: Visit['event']['allDay'];
  }
};

export type BasicRecordFragment = {
  __typename: Record['__typename'];
  creationDate: {
    unixMilliseconds: Record['creationDate']['unixMilliseconds'];
  };
  jsonBlob: Record['jsonBlob'];
  rid: Record['rid'];
  type: Record['type'];
  patient: {
    pid: Record['patient']['pid'];
  };
};

export type BasicSurveyTemplateFragment = {
  __typename: GraphQlFormTemplate['__typename'];
  name: GraphQlFormTemplate['name'];
  id: GraphQlFormTemplate['id'];
  sections: [{
    name: GraphQlFormTemplate['sections'][0]['name'];
    number: GraphQlFormTemplate['sections'][0]['number'];
    fields: [{
      number: GraphQlFormTemplate['sections'][0]['fields'][0]['number'];
    }];
  }]
}

export type ContactsFragment = EmergencyContact;

export type BasicFirebaseUserFragment = {
  __typename: NonNullable<FirebaseInfo['__typename']>;
  uid: NonNullable<FirebaseInfo['uid']>;
  email: FirebaseInfo['email'];
}

export type BasicGroupFragment = {
  __typename: NonNullable<Group['__typename']>;
  gid: Group['gid'];
  name: Group['name'];
}

export type Fragments =
  NamesFragment |
  BasicUserFragment |
  BasicDoctorFragment |
  BasicPatientFragment |
  PatientInsuranceFragment |
  BasicEventFragment |
  BasicIcdFragment |
  BasicVisitFragment |
  BasicRecordFragment |
  BasicSurveyTemplateFragment |
  ContactsFragment |
  BasicFirebaseUserFragment |
  BasicGroupFragment
