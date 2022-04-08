import type { FixedLengthArray } from '../../../types';
import type {
  Doctor,
  DoctorIdInput,
  GraphQlFormTemplate,
  Patient,
  PatientIdInput,
} from '../../schema/leftindust.schema';
import type {
  BasicDoctorFragment,
  BasicUserFragment,
  NamesFragment,
  BasicPatientFragment,
  PatientInsuranceFragment,
  BasicEventFragment,
  BasicVisitFragment,
  BasicRecordFragment,
  BasicIcdFragment,
  BasicSurveyTemplateFragment,
  ContactsFragment,
  BasicFirebaseUserFragment,
  BasicGroupFragment,
} from '../fragments';

export type UserQueryResult = {
  user: BasicUserFragment & {
    doctor?: BasicDoctorFragment;
    patient?: BasicDoctorFragment;
    names: NamesFragment;
  };
};

export type UsersQueryResult = {
  users: (BasicUserFragment & {
    names: NamesFragment;
  })[];
};

export type PatientsQueryResult = {
  patients: (BasicPatientFragment)[];
};

export type DoctorsQueryResult = {
  doctors: (BasicDoctorFragment)[];
};

export type EventsQueryResult = {
  events: BasicEventFragment[];
}

export type VisitsQueryResult = {
  visits: BasicVisitFragment[];
}

export type PatientInsuranceQueryResult = {
  patients: PatientInsuranceFragment[];
};

export type FirebaseUsersQueryResult = {
  firebaseUsers: BasicFirebaseUserFragment[];
}

export type PatientContactsQueryResult = {
  patients: {
    contacts: ContactsFragment[];
  }[];
};

export type PatientQueryResult = {
  patients: FixedLengthArray<
    [
      BasicPatientFragment & {
        ethnicity: Patient['ethnicity'];
        addresses: Patient['addresses'];
        phones: Patient['phones'];
        emails: Patient['emails'];
        insuranceNumber: Patient['insuranceNumber'];
        doctors: BasicDoctorFragment[];
      },
    ]
  >;
};

export type DoctorQueryResult = {
  doctors: FixedLengthArray<
    [
      BasicDoctorFragment & {
        addresses: Doctor['addresses'];
        phones: Doctor['phones'];
        emails: Doctor['emails'];
        user: BasicUserFragment;
        patients: BasicPatientFragment[];
      },
    ]
  >;
};

export type EventQueryResult = {
  events: FixedLengthArray<
    [
      BasicEventFragment & {
        doctors: [{
          __typename: NonNullable<Doctor['__typename']>;
          did: DoctorIdInput;
        }];
        patients: [{
          __typename: NonNullable<Patient['__typename']>;
          pid: PatientIdInput;
        }];
      },
    ]
  >;
}

export type VisitQueryResult = {
  visits: FixedLengthArray<
    [
      BasicVisitFragment & {
        event: {
          doctors: [{
            __typename: NonNullable<Doctor['__typename']>;
            did: DoctorIdInput;
          }];
          patients: [{
            __typename: NonNullable<Patient['__typename']>;
            pid: PatientIdInput;
          }];
        }
      },
    ]
  >;
}

export type DoctorEventsQueryResult = {
  doctors: FixedLengthArray<
    [
      {
        events: BasicEventFragment[];
      },
    ]
  >;
};

export type PatientEventsQueryResult = {
  patients: FixedLengthArray<
    [
      {
        events: BasicEventFragment[];
      },
    ]
  >;
};

export type PatientDoctorsQueryResult = {
  patients: DoctorsQueryResult[];
}

export type DoctorPatientsQueryResult = {
  doctors: PatientsQueryResult[];
}

export type PatientVisitsQueryResult = {
  patients: VisitsQueryResult[];
}

export type RecordsQueryResult = {
  getRecords: BasicRecordFragment[];
}

export type IcdSearchQueryResult = {
  searchIcd: {
    destinationEntities: BasicIcdFragment[];
  };
}

export type IcdQueryResult = {
  icd: BasicIcdFragment;
}

export type SurveysQueryResult = {
  surveys: BasicSurveyTemplateFragment[];
}

export type SurveyTemplateQueryResult = {
  surveys: FixedLengthArray<[{
    id: GraphQlFormTemplate['id'];
    name: GraphQlFormTemplate['name'];
    sections: {
      name: GraphQlFormTemplate['sections'][0]['name'];
      description: GraphQlFormTemplate['sections'][0]['description'];
      number: GraphQlFormTemplate['sections'][0]['number'];
      fields: {
        dataType: GraphQlFormTemplate['sections'][0]['fields'][0]['dataType'];
        floatLowerBound: GraphQlFormTemplate['sections'][0]['fields'][0]['floatLowerBound'];
        floatUpperBound: GraphQlFormTemplate['sections'][0]['fields'][0]['floatUpperBound'];
        intLowerBound: GraphQlFormTemplate['sections'][0]['fields'][0]['intLowerBound'];
        intUpperBound: GraphQlFormTemplate['sections'][0]['fields'][0]['intUpperBound'];
        jsonMetaData: GraphQlFormTemplate['sections'][0]['fields'][0]['jsonMetaData'];
        multiSelectPossibilities: GraphQlFormTemplate['sections'][0]['fields'][0]['multiSelectPossibilities'];
        number: GraphQlFormTemplate['sections'][0]['fields'][0]['number'];
        textRegex: GraphQlFormTemplate['sections'][0]['fields'][0]['textRegex'];
        title: GraphQlFormTemplate['sections'][0]['fields'][0]['title'];
      }[];
    }[];
  }]>;
};

export type PatientSurveyQueryResult = {
  patients: FixedLengthArray<
    [
      {
        assignedForms: {
          __typename: Patient['assignedForms'][0]['__typename'];
          id: Patient['assignedForms'][0]['id'];
          surveyTemplate: BasicSurveyTemplateFragment;
        }[];
      },
    ]
  >;
}

export type IntrospectionQueryResult = {
  __type: {
    enumValues: { name: string }[];
  }
}

export type GroupsQueryResult = {
  groups: BasicGroupFragment[];
}