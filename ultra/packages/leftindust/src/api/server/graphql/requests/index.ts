/* eslint-disable @typescript-eslint/ban-types */
import type * as Schema from '../schema/leftindust.schema';

import type { GraphQLResolveInfo, GraphQLScalarType, GraphQLScalarTypeConfig } from 'graphql';
import type { OperationStore } from '@urql/svelte';
import type { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type RequireFields<T, K extends keyof T> = Omit<T, K> & { [P in K]-?: NonNullable<T[P]> };
export type CompleteSurveyFragmentFragment = { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } };

export type DoctorFragment = { __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined, addresses: Array<{ __typename: 'Address', address: string, city: string, country: Schema.Countries, postalCode: string, province: string, type: Schema.AddressType | undefined, id: { __typename: 'AddressId', value: any } }>, emails: Array<{ __typename: 'Email', email: string, type: Schema.EmailType, id: { __typename: 'EmailId', value: any } }>, phoneNumbers: Array<{ __typename: 'Phone', number: string, type: Schema.PhoneType, id: { __typename: 'PhoneId', value: any } }> };

export type NameFragment = { __typename: 'NameInfo', firstName: string, middleName: string | undefined, lastName: string };

export type PartialCompleteSurveyFragmentFragment = { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', label: string, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', label: string | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } };

export type PartialDoctorFragment = { __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined };

export type PartialPatientFragment = { __typename: 'Patient', firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any, gender: string | undefined, sex: Schema.Sex, thumbnail: any | undefined, id: { __typename: 'PatientId', value: any } };

export type PartialTemplateFragmentFragment = { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', label: string, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', label: string | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> };

export type PartialUserFragment = { __typename: 'MediqUser', id: { __typename: 'MediqUserId', value: string }, group: { __typename: 'MediqGroup', name: string | undefined } | undefined, accountDetails: { __typename: 'UserAccountDetails', email: string | undefined, isRegistered: boolean } | undefined, name: { __typename: 'NameInfo', firstName: string, middleName: string | undefined, lastName: string } };

export type PatientFragment = { __typename: 'Patient', firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any, gender: string | undefined, sex: Schema.Sex, thumbnail: any | undefined, insuranceNumber: string | undefined, ethnicity: Schema.Ethnicity | undefined, id: { __typename: 'PatientId', value: any }, addresses: Array<{ __typename: 'Address', address: string, city: string, country: Schema.Countries, postalCode: string, province: string, type: Schema.AddressType | undefined, id: { __typename: 'AddressId', value: any } }>, emails: Array<{ __typename: 'Email', email: string, type: Schema.EmailType, id: { __typename: 'EmailId', value: any } }>, phoneNumbers: Array<{ __typename: 'Phone', number: string, type: Schema.PhoneType, id: { __typename: 'PhoneId', value: any } }> };

export type SurveyLinkFragmentFragment = { __typename: 'SurveyLink', id: { __typename: 'SurveyLinkId', value: any }, completedSurvey: { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } } | undefined, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } };

export type TemplateFragmentFragment = { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> };

export type AddDoctorMutationMutationVariables = Schema.Exact<{
  doctor: Schema.CreateDoctor;
}>;


export type AddDoctorMutationMutation = { __typename: 'Mutation', addDoctor: { __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined, addresses: Array<{ __typename: 'Address', address: string, city: string, country: Schema.Countries, postalCode: string, province: string, type: Schema.AddressType | undefined, id: { __typename: 'AddressId', value: any } }>, emails: Array<{ __typename: 'Email', email: string, type: Schema.EmailType, id: { __typename: 'EmailId', value: any } }>, phoneNumbers: Array<{ __typename: 'Phone', number: string, type: Schema.PhoneType, id: { __typename: 'PhoneId', value: any } }> } | undefined };

export type AddPatientMutationVariables = Schema.Exact<{
  patient: Schema.CreatePatient;
}>;


export type AddPatientMutation = { __typename: 'Mutation', addPatient: { __typename: 'Patient', id: { __typename: 'PatientId', value: any } } | undefined };

export type CreateCompleteSurveyMutationMutationVariables = Schema.Exact<{
  createCompleteSurvey: Schema.CreateCompleteSurvey;
}>;


export type CreateCompleteSurveyMutationMutation = { __typename: 'Mutation', createCompleteSurvey: { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any } } | undefined };

export type CreateSurveyLinkMutationMutationVariables = Schema.Exact<{
  createSurveyLink: Schema.CreateSurveyLink;
}>;


export type CreateSurveyLinkMutationMutation = { __typename: 'Mutation', createSurveyLink: { __typename: 'SurveyLink', id: { __typename: 'SurveyLinkId', value: any } } };

export type EditDoctorMutationVariables = Schema.Exact<{
  editDoctor: Schema.EditDoctor;
}>;


export type EditDoctorMutation = { __typename: 'Mutation', editDoctor: { __typename: 'Doctor', id: { __typename: 'DoctorId', value: any } | undefined } | undefined };

export type EditPatientMutationVariables = Schema.Exact<{
  editPatient: Schema.EditPatient;
}>;


export type EditPatientMutation = { __typename: 'Mutation', editPatient: { __typename: 'Patient', id: { __typename: 'PatientId', value: any } } | undefined };

export type SurveyTemplateMutationMutationVariables = Schema.Exact<{
  surveyTemplate: Schema.CreateSurveyTemplate;
}>;


export type SurveyTemplateMutationMutation = { __typename: 'Mutation', addSurveyTemplate: { __typename: 'SurveyTemplate', id: { __typename: 'SurveyTemplateId', value: any } } };

export type CompleteSurveyByIdQueryQueryVariables = Schema.Exact<{
  completeSurveyId: Schema.CompleteSurveyIdInput;
}>;


export type CompleteSurveyByIdQueryQuery = { __typename: 'Query', completeSurveyById: { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } } | undefined };

export type CompleteSurveyRangeQueryQueryVariables = Schema.Exact<{
  range: Schema.Range;
}>;


export type CompleteSurveyRangeQueryQuery = { __typename: 'Query', completeSurveyByRange: Array<{ __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } }> };

export type DoctorsByDoctorIdQueryQueryVariables = Schema.Exact<{
  doctorIds: Array<Schema.DoctorIdInput> | Schema.DoctorIdInput;
}>;


export type DoctorsByDoctorIdQueryQuery = { __typename: 'Query', doctorsByDoctorIds: Array<{ __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined, addresses: Array<{ __typename: 'Address', address: string, city: string, country: Schema.Countries, postalCode: string, province: string, type: Schema.AddressType | undefined, id: { __typename: 'AddressId', value: any } }>, emails: Array<{ __typename: 'Email', email: string, type: Schema.EmailType, id: { __typename: 'EmailId', value: any } }>, phoneNumbers: Array<{ __typename: 'Phone', number: string, type: Schema.PhoneType, id: { __typename: 'PhoneId', value: any } }> } | undefined> };

export type PartialCompleteSurveyByIdQueryQueryVariables = Schema.Exact<{
  completeSurveyId: Schema.CompleteSurveyIdInput;
}>;


export type PartialCompleteSurveyByIdQueryQuery = { __typename: 'Query', completeSurveyById: { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', label: string, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', label: string | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } } | undefined };

export type PartialDoctorsByDoctorIdQueryQueryVariables = Schema.Exact<{
  doctorIds: Array<Schema.DoctorIdInput> | Schema.DoctorIdInput;
}>;


export type PartialDoctorsByDoctorIdQueryQuery = { __typename: 'Query', doctorsByDoctorIds: Array<{ __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined } | undefined> };

export type PartialDoctorsByRangeQueryQueryVariables = Schema.Exact<{
  range: Schema.Range;
}>;


export type PartialDoctorsByRangeQueryQuery = { __typename: 'Query', doctorsByRange: Array<{ __typename: 'Doctor', title: string | undefined, firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any | undefined, thumbnail: any | undefined, id: { __typename: 'DoctorId', value: any } | undefined }> };

export type PartialPatientsByPatientIdQueryQueryVariables = Schema.Exact<{
  patientIds: Array<Schema.PatientIdInput> | Schema.PatientIdInput;
}>;


export type PartialPatientsByPatientIdQueryQuery = { __typename: 'Query', patientsByPatientId: Array<{ __typename: 'Patient', firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any, gender: string | undefined, sex: Schema.Sex, thumbnail: any | undefined, id: { __typename: 'PatientId', value: any } } | undefined> };

export type PartialPatientsByRangeQueryQueryVariables = Schema.Exact<{
  range: Schema.Range;
}>;


export type PartialPatientsByRangeQueryQuery = { __typename: 'Query', patientsByRange: Array<{ __typename: 'Patient', firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any, gender: string | undefined, sex: Schema.Sex, thumbnail: any | undefined, id: { __typename: 'PatientId', value: any } }> };

export type PartialSurveyTemplateByIdQueryQueryVariables = Schema.Exact<{
  surveyTemplateId: Schema.SurveyTemplateIdInput;
}>;


export type PartialSurveyTemplateByIdQueryQuery = { __typename: 'Query', surveyTemplateById: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', label: string, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', label: string | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } | undefined };

export type PartialSurveyTemplateByRangeQueryQueryVariables = Schema.Exact<{
  range: Schema.Range;
}>;


export type PartialSurveyTemplateByRangeQueryQuery = { __typename: 'Query', surveyTemplateByRange: Array<{ __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', label: string, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', label: string | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> }> };

export type PartialUserByUserUniqueIdQueryQueryVariables = Schema.Exact<{
  uniqueId: Schema.Scalars['String'];
}>;


export type PartialUserByUserUniqueIdQueryQuery = { __typename: 'Query', userByUserUniqueId: { __typename: 'MediqUser', id: { __typename: 'MediqUserId', value: string }, group: { __typename: 'MediqGroup', name: string | undefined } | undefined, accountDetails: { __typename: 'UserAccountDetails', email: string | undefined, isRegistered: boolean } | undefined, name: { __typename: 'NameInfo', firstName: string, middleName: string | undefined, lastName: string } } | undefined };

export type PatientsByPatientIdQueryQueryVariables = Schema.Exact<{
  patientIds: Array<Schema.PatientIdInput> | Schema.PatientIdInput;
}>;


export type PatientsByPatientIdQueryQuery = { __typename: 'Query', patientsByPatientId: Array<{ __typename: 'Patient', firstName: string, middleName: string | undefined, lastName: string, dateOfBirth: any, gender: string | undefined, sex: Schema.Sex, thumbnail: any | undefined, insuranceNumber: string | undefined, ethnicity: Schema.Ethnicity | undefined, id: { __typename: 'PatientId', value: any }, addresses: Array<{ __typename: 'Address', address: string, city: string, country: Schema.Countries, postalCode: string, province: string, type: Schema.AddressType | undefined, id: { __typename: 'AddressId', value: any } }>, emails: Array<{ __typename: 'Email', email: string, type: Schema.EmailType, id: { __typename: 'EmailId', value: any } }>, phoneNumbers: Array<{ __typename: 'Phone', number: string, type: Schema.PhoneType, id: { __typename: 'PhoneId', value: any } }> } | undefined> };

export type SurveyLinkByIdQueryQueryVariables = Schema.Exact<{
  surveyLinkId: Schema.SurveyLinkIdInput;
}>;


export type SurveyLinkByIdQueryQuery = { __typename: 'Query', surveyLinkById: { __typename: 'SurveyLink', id: { __typename: 'SurveyLinkId', value: any }, completedSurvey: { __typename: 'CompleteSurvey', id: { __typename: 'CompleteSurveyId', value: any }, sections: Array<{ __typename: 'CompleteSurveySection', id: { __typename: 'CompleteSurveySectionId', value: any }, inputs: Array<{ __typename: 'CompleteSurveySectionNumberArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionNumberInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringArrayInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } } | { __typename: 'CompleteSurveySectionStringInput', id: { __typename: 'CompleteSurveySectionInputId', value: any } }> }>, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } } | undefined, surveyTemplate: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } } | undefined };

export type SurveyTemplateByIdQueryQueryVariables = Schema.Exact<{
  surveyTemplateId: Schema.SurveyTemplateIdInput;
}>;


export type SurveyTemplateByIdQueryQuery = { __typename: 'Query', surveyTemplateById: { __typename: 'SurveyTemplate', title: string, subtitle: string | undefined, id: { __typename: 'SurveyTemplateId', value: any }, sections: Array<{ __typename: 'SurveyTemplateSection', title: string, subtitle: string | undefined, calculationId: number, id: { __typename: 'SurveyTemplateSectionId', value: any }, inputs: Array<{ __typename: 'SurveyTemplateInput', calculationId: number, category: Schema.SurveyTemplateCategory | undefined, label: string, options: Array<string> | undefined, placeholder: string | undefined, required: boolean, type: Schema.SurveyTemplateInputType, uploadAccept: Schema.TemplateInputUploadType | undefined, uploadMultiple: boolean | undefined, id: { __typename: 'SurveyTemplateInputId', value: any } }> }>, calculations: Array<{ __typename: 'SurveyTemplateCalculation', calculation: string | undefined, index: number, inputType: Schema.SurveyTemplateInputType | undefined, label: string | undefined, showOnComplete: boolean | undefined, id: { __typename: 'SurveyTemplateCalculationId', value: any } }> } | undefined };



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
  AddressId: ResolverTypeWrapper<Schema.AddressId>;
  AddressType: Schema.AddressType;
  Base64: ResolverTypeWrapper<Schema.Scalars['Base64']>;
  Boolean: ResolverTypeWrapper<Schema.Scalars['Boolean']>;
  Clinic: ResolverTypeWrapper<Schema.Clinic>;
  ClinicId: ResolverTypeWrapper<Schema.ClinicId>;
  ClinicIdInput: Schema.ClinicIdInput;
  CompleteSurvey: ResolverTypeWrapper<Schema.CompleteSurvey>;
  CompleteSurveyId: ResolverTypeWrapper<Schema.CompleteSurveyId>;
  CompleteSurveyIdInput: Schema.CompleteSurveyIdInput;
  CompleteSurveyInputType: Schema.CompleteSurveyInputType;
  CompleteSurveySection: ResolverTypeWrapper<Schema.CompleteSurveySection>;
  CompleteSurveySectionId: ResolverTypeWrapper<Schema.CompleteSurveySectionId>;
  CompleteSurveySectionInput: ResolversTypes['CompleteSurveySectionNumberArrayInput'] | ResolversTypes['CompleteSurveySectionNumberInput'] | ResolversTypes['CompleteSurveySectionStringArrayInput'] | ResolversTypes['CompleteSurveySectionStringInput'];
  CompleteSurveySectionInputId: ResolverTypeWrapper<Schema.CompleteSurveySectionInputId>;
  CompleteSurveySectionNumberArrayInput: ResolverTypeWrapper<Schema.CompleteSurveySectionNumberArrayInput>;
  CompleteSurveySectionNumberInput: ResolverTypeWrapper<Schema.CompleteSurveySectionNumberInput>;
  CompleteSurveySectionStringArrayInput: ResolverTypeWrapper<Schema.CompleteSurveySectionStringArrayInput>;
  CompleteSurveySectionStringInput: ResolverTypeWrapper<Schema.CompleteSurveySectionStringInput>;
  Countries: Schema.Countries;
  CreateAddress: Schema.CreateAddress;
  CreateClinic: Schema.CreateClinic;
  CreateCompleteSurvey: Schema.CreateCompleteSurvey;
  CreateCompleteSurveyInput: Schema.CreateCompleteSurveyInput;
  CreateCompleteSurveySection: Schema.CreateCompleteSurveySection;
  CreateContact: Schema.CreateContact;
  CreateDoctor: Schema.CreateDoctor;
  CreateDoctorUser: Schema.CreateDoctorUser;
  CreateDoctorUserType: Schema.CreateDoctorUserType;
  CreateEmail: Schema.CreateEmail;
  CreateNameInfo: Schema.CreateNameInfo;
  CreatePatient: Schema.CreatePatient;
  CreatePhone: Schema.CreatePhone;
  CreateSurveyLink: Schema.CreateSurveyLink;
  CreateSurveyTemplate: Schema.CreateSurveyTemplate;
  CreateSurveyTemplateCalculation: Schema.CreateSurveyTemplateCalculation;
  CreateSurveyTemplateSection: Schema.CreateSurveyTemplateSection;
  CreateSurveyTemplateSectionInput: Schema.CreateSurveyTemplateSectionInput;
  CreateUser: Schema.CreateUser;
  Doctor: ResolverTypeWrapper<Schema.Doctor>;
  DoctorId: ResolverTypeWrapper<Schema.DoctorId>;
  DoctorIdInput: Schema.DoctorIdInput;
  Duration: ResolverTypeWrapper<Schema.Scalars['Duration']>;
  EditClinic: Schema.EditClinic;
  EditDoctor: Schema.EditDoctor;
  EditPatient: Schema.EditPatient;
  Email: ResolverTypeWrapper<Schema.Email>;
  EmailId: ResolverTypeWrapper<Schema.EmailId>;
  EmailType: Schema.EmailType;
  Ethnicity: Schema.Ethnicity;
  Int: ResolverTypeWrapper<Schema.Scalars['Int']>;
  LocalDate: ResolverTypeWrapper<Schema.Scalars['LocalDate']>;
  LocalDateTime: ResolverTypeWrapper<Schema.Scalars['LocalDateTime']>;
  MediqGroup: ResolverTypeWrapper<Schema.MediqGroup>;
  MediqGroupId: ResolverTypeWrapper<Schema.MediqGroupId>;
  MediqGroupIdInput: Schema.MediqGroupIdInput;
  MediqUser: ResolverTypeWrapper<Schema.MediqUser>;
  MediqUserId: ResolverTypeWrapper<Schema.MediqUserId>;
  MediqUserUniqueIdInput: Schema.MediqUserUniqueIdInput;
  Mutation: ResolverTypeWrapper<{}>;
  NameInfo: ResolverTypeWrapper<Schema.NameInfo>;
  Patient: ResolverTypeWrapper<Schema.Patient>;
  PatientId: ResolverTypeWrapper<Schema.PatientId>;
  PatientIdInput: Schema.PatientIdInput;
  Phone: ResolverTypeWrapper<Schema.Phone>;
  PhoneId: ResolverTypeWrapper<Schema.PhoneId>;
  PhoneType: Schema.PhoneType;
  Query: ResolverTypeWrapper<{}>;
  Range: Schema.Range;
  Relationship: Schema.Relationship;
  Sex: Schema.Sex;
  String: ResolverTypeWrapper<Schema.Scalars['String']>;
  SurveyLink: ResolverTypeWrapper<Schema.SurveyLink>;
  SurveyLinkId: ResolverTypeWrapper<Schema.SurveyLinkId>;
  SurveyLinkIdInput: Schema.SurveyLinkIdInput;
  SurveyTemplate: ResolverTypeWrapper<Schema.SurveyTemplate>;
  SurveyTemplateCalculation: ResolverTypeWrapper<Schema.SurveyTemplateCalculation>;
  SurveyTemplateCalculationId: ResolverTypeWrapper<Schema.SurveyTemplateCalculationId>;
  SurveyTemplateCategory: Schema.SurveyTemplateCategory;
  SurveyTemplateId: ResolverTypeWrapper<Schema.SurveyTemplateId>;
  SurveyTemplateIdInput: Schema.SurveyTemplateIdInput;
  SurveyTemplateInput: ResolverTypeWrapper<Schema.SurveyTemplateInput>;
  SurveyTemplateInputId: ResolverTypeWrapper<Schema.SurveyTemplateInputId>;
  SurveyTemplateInputType: Schema.SurveyTemplateInputType;
  SurveyTemplateLinkIdInput: Schema.SurveyTemplateLinkIdInput;
  SurveyTemplateSection: ResolverTypeWrapper<Schema.SurveyTemplateSection>;
  SurveyTemplateSectionId: ResolverTypeWrapper<Schema.SurveyTemplateSectionId>;
  SurveyTemplateSectionIdInput: Schema.SurveyTemplateSectionIdInput;
  SurveyTemplateSectionInputIdInput: Schema.SurveyTemplateSectionInputIdInput;
  TemplateInputUploadType: Schema.TemplateInputUploadType;
  UUID: ResolverTypeWrapper<Schema.Scalars['UUID']>;
  UpdateNameInfo: Schema.UpdateNameInfo;
  UserAccountDetails: ResolverTypeWrapper<Schema.UserAccountDetails>;
};

/** Mapping between all available schema types and the resolvers parents */
export type ResolversParentTypes = {
  Address: Schema.Address;
  AddressId: Schema.AddressId;
  Base64: Schema.Scalars['Base64'];
  Boolean: Schema.Scalars['Boolean'];
  Clinic: Schema.Clinic;
  ClinicId: Schema.ClinicId;
  ClinicIdInput: Schema.ClinicIdInput;
  CompleteSurvey: Schema.CompleteSurvey;
  CompleteSurveyId: Schema.CompleteSurveyId;
  CompleteSurveyIdInput: Schema.CompleteSurveyIdInput;
  CompleteSurveySection: Schema.CompleteSurveySection;
  CompleteSurveySectionId: Schema.CompleteSurveySectionId;
  CompleteSurveySectionInput: ResolversParentTypes['CompleteSurveySectionNumberArrayInput'] | ResolversParentTypes['CompleteSurveySectionNumberInput'] | ResolversParentTypes['CompleteSurveySectionStringArrayInput'] | ResolversParentTypes['CompleteSurveySectionStringInput'];
  CompleteSurveySectionInputId: Schema.CompleteSurveySectionInputId;
  CompleteSurveySectionNumberArrayInput: Schema.CompleteSurveySectionNumberArrayInput;
  CompleteSurveySectionNumberInput: Schema.CompleteSurveySectionNumberInput;
  CompleteSurveySectionStringArrayInput: Schema.CompleteSurveySectionStringArrayInput;
  CompleteSurveySectionStringInput: Schema.CompleteSurveySectionStringInput;
  CreateAddress: Schema.CreateAddress;
  CreateClinic: Schema.CreateClinic;
  CreateCompleteSurvey: Schema.CreateCompleteSurvey;
  CreateCompleteSurveyInput: Schema.CreateCompleteSurveyInput;
  CreateCompleteSurveySection: Schema.CreateCompleteSurveySection;
  CreateContact: Schema.CreateContact;
  CreateDoctor: Schema.CreateDoctor;
  CreateDoctorUser: Schema.CreateDoctorUser;
  CreateEmail: Schema.CreateEmail;
  CreateNameInfo: Schema.CreateNameInfo;
  CreatePatient: Schema.CreatePatient;
  CreatePhone: Schema.CreatePhone;
  CreateSurveyLink: Schema.CreateSurveyLink;
  CreateSurveyTemplate: Schema.CreateSurveyTemplate;
  CreateSurveyTemplateCalculation: Schema.CreateSurveyTemplateCalculation;
  CreateSurveyTemplateSection: Schema.CreateSurveyTemplateSection;
  CreateSurveyTemplateSectionInput: Schema.CreateSurveyTemplateSectionInput;
  CreateUser: Schema.CreateUser;
  Doctor: Schema.Doctor;
  DoctorId: Schema.DoctorId;
  DoctorIdInput: Schema.DoctorIdInput;
  Duration: Schema.Scalars['Duration'];
  EditClinic: Schema.EditClinic;
  EditDoctor: Schema.EditDoctor;
  EditPatient: Schema.EditPatient;
  Email: Schema.Email;
  EmailId: Schema.EmailId;
  Int: Schema.Scalars['Int'];
  LocalDate: Schema.Scalars['LocalDate'];
  LocalDateTime: Schema.Scalars['LocalDateTime'];
  MediqGroup: Schema.MediqGroup;
  MediqGroupId: Schema.MediqGroupId;
  MediqGroupIdInput: Schema.MediqGroupIdInput;
  MediqUser: Schema.MediqUser;
  MediqUserId: Schema.MediqUserId;
  MediqUserUniqueIdInput: Schema.MediqUserUniqueIdInput;
  Mutation: {};
  NameInfo: Schema.NameInfo;
  Patient: Schema.Patient;
  PatientId: Schema.PatientId;
  PatientIdInput: Schema.PatientIdInput;
  Phone: Schema.Phone;
  PhoneId: Schema.PhoneId;
  Query: {};
  Range: Schema.Range;
  String: Schema.Scalars['String'];
  SurveyLink: Schema.SurveyLink;
  SurveyLinkId: Schema.SurveyLinkId;
  SurveyLinkIdInput: Schema.SurveyLinkIdInput;
  SurveyTemplate: Schema.SurveyTemplate;
  SurveyTemplateCalculation: Schema.SurveyTemplateCalculation;
  SurveyTemplateCalculationId: Schema.SurveyTemplateCalculationId;
  SurveyTemplateId: Schema.SurveyTemplateId;
  SurveyTemplateIdInput: Schema.SurveyTemplateIdInput;
  SurveyTemplateInput: Schema.SurveyTemplateInput;
  SurveyTemplateInputId: Schema.SurveyTemplateInputId;
  SurveyTemplateLinkIdInput: Schema.SurveyTemplateLinkIdInput;
  SurveyTemplateSection: Schema.SurveyTemplateSection;
  SurveyTemplateSectionId: Schema.SurveyTemplateSectionId;
  SurveyTemplateSectionIdInput: Schema.SurveyTemplateSectionIdInput;
  SurveyTemplateSectionInputIdInput: Schema.SurveyTemplateSectionInputIdInput;
  UUID: Schema.Scalars['UUID'];
  UpdateNameInfo: Schema.UpdateNameInfo;
  UserAccountDetails: Schema.UserAccountDetails;
};

export type AddressResolvers<ContextType = any, ParentType extends ResolversParentTypes['Address'] = ResolversParentTypes['Address']> = {
  address?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  city?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  country?: Resolver<ResolversTypes['Countries'], ParentType, ContextType>;
  id?: Resolver<ResolversTypes['AddressId'], ParentType, ContextType>;
  postalCode?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  province?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  type?: Resolver<Schema.Maybe<ResolversTypes['AddressType']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type AddressIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['AddressId'] = ResolversParentTypes['AddressId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface Base64ScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['Base64'], any> {
  name: 'Base64';
}

export type ClinicResolvers<ContextType = any, ParentType extends ResolversParentTypes['Clinic'] = ResolversParentTypes['Clinic']> = {
  id?: Resolver<ResolversTypes['ClinicId'], ParentType, ContextType>;
  name?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type ClinicIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['ClinicId'] = ResolversParentTypes['ClinicId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveyResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurvey'] = ResolversParentTypes['CompleteSurvey']> = {
  id?: Resolver<ResolversTypes['CompleteSurveyId'], ParentType, ContextType>;
  sections?: Resolver<Array<ResolversTypes['CompleteSurveySection']>, ParentType, ContextType>;
  surveyTemplate?: Resolver<ResolversTypes['SurveyTemplate'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveyIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveyId'] = ResolversParentTypes['CompleteSurveyId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySection'] = ResolversParentTypes['CompleteSurveySection']> = {
  id?: Resolver<ResolversTypes['CompleteSurveySectionId'], ParentType, ContextType>;
  inputs?: Resolver<Array<ResolversTypes['CompleteSurveySectionInput']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionId'] = ResolversParentTypes['CompleteSurveySectionId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionInput'] = ResolversParentTypes['CompleteSurveySectionInput']> = {
  __resolveType: TypeResolveFn<'CompleteSurveySectionNumberArrayInput' | 'CompleteSurveySectionNumberInput' | 'CompleteSurveySectionStringArrayInput' | 'CompleteSurveySectionStringInput', ParentType, ContextType>;
  id?: Resolver<ResolversTypes['CompleteSurveySectionInputId'], ParentType, ContextType>;
};

export type CompleteSurveySectionInputIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionInputId'] = ResolversParentTypes['CompleteSurveySectionInputId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionNumberArrayInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionNumberArrayInput'] = ResolversParentTypes['CompleteSurveySectionNumberArrayInput']> = {
  id?: Resolver<ResolversTypes['CompleteSurveySectionInputId'], ParentType, ContextType>;
  numberArray?: Resolver<Array<ResolversTypes['Int']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionNumberInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionNumberInput'] = ResolversParentTypes['CompleteSurveySectionNumberInput']> = {
  id?: Resolver<ResolversTypes['CompleteSurveySectionInputId'], ParentType, ContextType>;
  number?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionStringArrayInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionStringArrayInput'] = ResolversParentTypes['CompleteSurveySectionStringArrayInput']> = {
  id?: Resolver<ResolversTypes['CompleteSurveySectionInputId'], ParentType, ContextType>;
  stringArray?: Resolver<Array<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type CompleteSurveySectionStringInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['CompleteSurveySectionStringInput'] = ResolversParentTypes['CompleteSurveySectionStringInput']> = {
  id?: Resolver<ResolversTypes['CompleteSurveySectionInputId'], ParentType, ContextType>;
  string?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type DoctorResolvers<ContextType = any, ParentType extends ResolversParentTypes['Doctor'] = ResolversParentTypes['Doctor']> = {
  addresses?: Resolver<Array<ResolversTypes['Address']>, ParentType, ContextType>;
  dateOfBirth?: Resolver<Schema.Maybe<ResolversTypes['LocalDate']>, ParentType, ContextType>;
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  id?: Resolver<Schema.Maybe<ResolversTypes['DoctorId']>, ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phoneNumbers?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['Base64']>, ParentType, ContextType>;
  title?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type DoctorIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['DoctorId'] = ResolversParentTypes['DoctorId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface DurationScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['Duration'], any> {
  name: 'Duration';
}

export type EmailResolvers<ContextType = any, ParentType extends ResolversParentTypes['Email'] = ResolversParentTypes['Email']> = {
  email?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  id?: Resolver<ResolversTypes['EmailId'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['EmailType'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type EmailIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['EmailId'] = ResolversParentTypes['EmailId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface LocalDateScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['LocalDate'], any> {
  name: 'LocalDate';
}

export interface LocalDateTimeScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['LocalDateTime'], any> {
  name: 'LocalDateTime';
}

export type MediqGroupResolvers<ContextType = any, ParentType extends ResolversParentTypes['MediqGroup'] = ResolversParentTypes['MediqGroup']> = {
  id?: Resolver<ResolversTypes['MediqGroupId'], ParentType, ContextType>;
  name?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type MediqGroupIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['MediqGroupId'] = ResolversParentTypes['MediqGroupId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type MediqUserResolvers<ContextType = any, ParentType extends ResolversParentTypes['MediqUser'] = ResolversParentTypes['MediqUser']> = {
  accountDetails?: Resolver<Schema.Maybe<ResolversTypes['UserAccountDetails']>, ParentType, ContextType>;
  group?: Resolver<Schema.Maybe<ResolversTypes['MediqGroup']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['MediqUserId'], ParentType, ContextType>;
  name?: Resolver<ResolversTypes['NameInfo'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type MediqUserIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['MediqUserId'] = ResolversParentTypes['MediqUserId']> = {
  value?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type MutationResolvers<ContextType = any, ParentType extends ResolversParentTypes['Mutation'] = ResolversParentTypes['Mutation']> = {
  addClinic?: Resolver<ResolversTypes['Clinic'], ParentType, ContextType, RequireFields<Schema.MutationAddClinicArgs, 'clinic'>>;
  addDoctor?: Resolver<Schema.Maybe<ResolversTypes['Doctor']>, ParentType, ContextType, Partial<Schema.MutationAddDoctorArgs>>;
  addPatient?: Resolver<Schema.Maybe<ResolversTypes['Patient']>, ParentType, ContextType, Partial<Schema.MutationAddPatientArgs>>;
  addSurveyTemplate?: Resolver<ResolversTypes['SurveyTemplate'], ParentType, ContextType, RequireFields<Schema.MutationAddSurveyTemplateArgs, 'surveyTemplate'>>;
  createCompleteSurvey?: Resolver<Schema.Maybe<ResolversTypes['CompleteSurvey']>, ParentType, ContextType, Partial<Schema.MutationCreateCompleteSurveyArgs>>;
  createMediqUser?: Resolver<Schema.Maybe<ResolversTypes['MediqUser']>, ParentType, ContextType, Partial<Schema.MutationCreateMediqUserArgs>>;
  createSurveyLink?: Resolver<ResolversTypes['SurveyLink'], ParentType, ContextType, Partial<Schema.MutationCreateSurveyLinkArgs>>;
  editClinic?: Resolver<Schema.Maybe<ResolversTypes['Clinic']>, ParentType, ContextType, RequireFields<Schema.MutationEditClinicArgs, 'clinic'>>;
  editDoctor?: Resolver<Schema.Maybe<ResolversTypes['Doctor']>, ParentType, ContextType, Partial<Schema.MutationEditDoctorArgs>>;
  editPatient?: Resolver<Schema.Maybe<ResolversTypes['Patient']>, ParentType, ContextType, Partial<Schema.MutationEditPatientArgs>>;
};

export type NameInfoResolvers<ContextType = any, ParentType extends ResolversParentTypes['NameInfo'] = ResolversParentTypes['NameInfo']> = {
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PatientResolvers<ContextType = any, ParentType extends ResolversParentTypes['Patient'] = ResolversParentTypes['Patient']> = {
  addresses?: Resolver<Array<ResolversTypes['Address']>, ParentType, ContextType>;
  dateOfBirth?: Resolver<ResolversTypes['LocalDate'], ParentType, ContextType>;
  emails?: Resolver<Array<ResolversTypes['Email']>, ParentType, ContextType>;
  ethnicity?: Resolver<Schema.Maybe<ResolversTypes['Ethnicity']>, ParentType, ContextType>;
  firstName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  gender?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['PatientId'], ParentType, ContextType>;
  insuranceNumber?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  lastName?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  middleName?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  phoneNumbers?: Resolver<Array<ResolversTypes['Phone']>, ParentType, ContextType>;
  sex?: Resolver<ResolversTypes['Sex'], ParentType, ContextType>;
  thumbnail?: Resolver<Schema.Maybe<ResolversTypes['Base64']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PatientIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['PatientId'] = ResolversParentTypes['PatientId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PhoneResolvers<ContextType = any, ParentType extends ResolversParentTypes['Phone'] = ResolversParentTypes['Phone']> = {
  id?: Resolver<ResolversTypes['PhoneId'], ParentType, ContextType>;
  number?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['PhoneType'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type PhoneIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['PhoneId'] = ResolversParentTypes['PhoneId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type QueryResolvers<ContextType = any, ParentType extends ResolversParentTypes['Query'] = ResolversParentTypes['Query']> = {
  clinicByClinicId?: Resolver<Schema.Maybe<ResolversTypes['Clinic']>, ParentType, ContextType, RequireFields<Schema.QueryClinicByClinicIdArgs, 'clinicId'>>;
  completeSurveyById?: Resolver<Schema.Maybe<ResolversTypes['CompleteSurvey']>, ParentType, ContextType, Partial<Schema.QueryCompleteSurveyByIdArgs>>;
  completeSurveyByRange?: Resolver<Array<ResolversTypes['CompleteSurvey']>, ParentType, ContextType, RequireFields<Schema.QueryCompleteSurveyByRangeArgs, 'range'>>;
  doctorsByDoctorIds?: Resolver<Array<Schema.Maybe<ResolversTypes['Doctor']>>, ParentType, ContextType, RequireFields<Schema.QueryDoctorsByDoctorIdsArgs, 'doctorIds'>>;
  doctorsByRange?: Resolver<Array<ResolversTypes['Doctor']>, ParentType, ContextType, RequireFields<Schema.QueryDoctorsByRangeArgs, 'range'>>;
  mockingbirdIsAlive?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  patientsByPatientId?: Resolver<Array<Schema.Maybe<ResolversTypes['Patient']>>, ParentType, ContextType, RequireFields<Schema.QueryPatientsByPatientIdArgs, 'patientIds'>>;
  patientsByRange?: Resolver<Array<ResolversTypes['Patient']>, ParentType, ContextType, RequireFields<Schema.QueryPatientsByRangeArgs, 'range'>>;
  surveyLinkById?: Resolver<Schema.Maybe<ResolversTypes['SurveyLink']>, ParentType, ContextType, RequireFields<Schema.QuerySurveyLinkByIdArgs, 'surveyLinkId'>>;
  surveyTemplateById?: Resolver<Schema.Maybe<ResolversTypes['SurveyTemplate']>, ParentType, ContextType, RequireFields<Schema.QuerySurveyTemplateByIdArgs, 'surveyTemplateId'>>;
  surveyTemplateByRange?: Resolver<Array<ResolversTypes['SurveyTemplate']>, ParentType, ContextType, RequireFields<Schema.QuerySurveyTemplateByRangeArgs, 'range'>>;
  userByUserUniqueId?: Resolver<Schema.Maybe<ResolversTypes['MediqUser']>, ParentType, ContextType, RequireFields<Schema.QueryUserByUserUniqueIdArgs, 'uniqueId'>>;
};

export type SurveyLinkResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyLink'] = ResolversParentTypes['SurveyLink']> = {
  completedSurvey?: Resolver<Schema.Maybe<ResolversTypes['CompleteSurvey']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['SurveyLinkId'], ParentType, ContextType>;
  patient?: Resolver<Schema.Maybe<ResolversTypes['Patient']>, ParentType, ContextType>;
  surveyTemplate?: Resolver<ResolversTypes['SurveyTemplate'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyLinkIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyLinkId'] = ResolversParentTypes['SurveyLinkId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplate'] = ResolversParentTypes['SurveyTemplate']> = {
  calculations?: Resolver<Array<ResolversTypes['SurveyTemplateCalculation']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['SurveyTemplateId'], ParentType, ContextType>;
  sections?: Resolver<Array<ResolversTypes['SurveyTemplateSection']>, ParentType, ContextType>;
  subtitle?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateCalculationResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateCalculation'] = ResolversParentTypes['SurveyTemplateCalculation']> = {
  calculation?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['SurveyTemplateCalculationId'], ParentType, ContextType>;
  index?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  inputType?: Resolver<Schema.Maybe<ResolversTypes['SurveyTemplateInputType']>, ParentType, ContextType>;
  label?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  showOnComplete?: Resolver<Schema.Maybe<ResolversTypes['Boolean']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateCalculationIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateCalculationId'] = ResolversParentTypes['SurveyTemplateCalculationId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateId'] = ResolversParentTypes['SurveyTemplateId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateInputResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateInput'] = ResolversParentTypes['SurveyTemplateInput']> = {
  calculationId?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  category?: Resolver<Schema.Maybe<ResolversTypes['SurveyTemplateCategory']>, ParentType, ContextType>;
  id?: Resolver<ResolversTypes['SurveyTemplateInputId'], ParentType, ContextType>;
  label?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  options?: Resolver<Schema.Maybe<Array<ResolversTypes['String']>>, ParentType, ContextType>;
  placeholder?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  required?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  type?: Resolver<ResolversTypes['SurveyTemplateInputType'], ParentType, ContextType>;
  uploadAccept?: Resolver<Schema.Maybe<ResolversTypes['TemplateInputUploadType']>, ParentType, ContextType>;
  uploadMultiple?: Resolver<Schema.Maybe<ResolversTypes['Boolean']>, ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateInputIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateInputId'] = ResolversParentTypes['SurveyTemplateInputId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateSectionResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateSection'] = ResolversParentTypes['SurveyTemplateSection']> = {
  calculationId?: Resolver<ResolversTypes['Int'], ParentType, ContextType>;
  id?: Resolver<ResolversTypes['SurveyTemplateSectionId'], ParentType, ContextType>;
  inputs?: Resolver<Array<ResolversTypes['SurveyTemplateInput']>, ParentType, ContextType>;
  subtitle?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  title?: Resolver<ResolversTypes['String'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type SurveyTemplateSectionIdResolvers<ContextType = any, ParentType extends ResolversParentTypes['SurveyTemplateSectionId'] = ResolversParentTypes['SurveyTemplateSectionId']> = {
  value?: Resolver<ResolversTypes['UUID'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export interface UuidScalarConfig extends GraphQLScalarTypeConfig<ResolversTypes['UUID'], any> {
  name: 'UUID';
}

export type UserAccountDetailsResolvers<ContextType = any, ParentType extends ResolversParentTypes['UserAccountDetails'] = ResolversParentTypes['UserAccountDetails']> = {
  email?: Resolver<Schema.Maybe<ResolversTypes['String']>, ParentType, ContextType>;
  isRegistered?: Resolver<ResolversTypes['Boolean'], ParentType, ContextType>;
  __isTypeOf?: IsTypeOfResolverFn<ParentType, ContextType>;
};

export type Resolvers<ContextType = any> = {
  Address?: AddressResolvers<ContextType>;
  AddressId?: AddressIdResolvers<ContextType>;
  Base64?: GraphQLScalarType;
  Clinic?: ClinicResolvers<ContextType>;
  ClinicId?: ClinicIdResolvers<ContextType>;
  CompleteSurvey?: CompleteSurveyResolvers<ContextType>;
  CompleteSurveyId?: CompleteSurveyIdResolvers<ContextType>;
  CompleteSurveySection?: CompleteSurveySectionResolvers<ContextType>;
  CompleteSurveySectionId?: CompleteSurveySectionIdResolvers<ContextType>;
  CompleteSurveySectionInput?: CompleteSurveySectionInputResolvers<ContextType>;
  CompleteSurveySectionInputId?: CompleteSurveySectionInputIdResolvers<ContextType>;
  CompleteSurveySectionNumberArrayInput?: CompleteSurveySectionNumberArrayInputResolvers<ContextType>;
  CompleteSurveySectionNumberInput?: CompleteSurveySectionNumberInputResolvers<ContextType>;
  CompleteSurveySectionStringArrayInput?: CompleteSurveySectionStringArrayInputResolvers<ContextType>;
  CompleteSurveySectionStringInput?: CompleteSurveySectionStringInputResolvers<ContextType>;
  Doctor?: DoctorResolvers<ContextType>;
  DoctorId?: DoctorIdResolvers<ContextType>;
  Duration?: GraphQLScalarType;
  Email?: EmailResolvers<ContextType>;
  EmailId?: EmailIdResolvers<ContextType>;
  LocalDate?: GraphQLScalarType;
  LocalDateTime?: GraphQLScalarType;
  MediqGroup?: MediqGroupResolvers<ContextType>;
  MediqGroupId?: MediqGroupIdResolvers<ContextType>;
  MediqUser?: MediqUserResolvers<ContextType>;
  MediqUserId?: MediqUserIdResolvers<ContextType>;
  Mutation?: MutationResolvers<ContextType>;
  NameInfo?: NameInfoResolvers<ContextType>;
  Patient?: PatientResolvers<ContextType>;
  PatientId?: PatientIdResolvers<ContextType>;
  Phone?: PhoneResolvers<ContextType>;
  PhoneId?: PhoneIdResolvers<ContextType>;
  Query?: QueryResolvers<ContextType>;
  SurveyLink?: SurveyLinkResolvers<ContextType>;
  SurveyLinkId?: SurveyLinkIdResolvers<ContextType>;
  SurveyTemplate?: SurveyTemplateResolvers<ContextType>;
  SurveyTemplateCalculation?: SurveyTemplateCalculationResolvers<ContextType>;
  SurveyTemplateCalculationId?: SurveyTemplateCalculationIdResolvers<ContextType>;
  SurveyTemplateId?: SurveyTemplateIdResolvers<ContextType>;
  SurveyTemplateInput?: SurveyTemplateInputResolvers<ContextType>;
  SurveyTemplateInputId?: SurveyTemplateInputIdResolvers<ContextType>;
  SurveyTemplateSection?: SurveyTemplateSectionResolvers<ContextType>;
  SurveyTemplateSectionId?: SurveyTemplateSectionIdResolvers<ContextType>;
  UUID?: GraphQLScalarType;
  UserAccountDetails?: UserAccountDetailsResolvers<ContextType>;
};


export const DoctorFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'doctor' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Doctor' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'dateOfBirth' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'thumbnail' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'addresses' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'address' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'city' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'country' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'postalCode' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'province' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'phoneNumbers' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'number' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } }] } }] } as unknown as DocumentNode<DoctorFragment, unknown>;
export const PartialTemplateFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'partialTemplateFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyTemplate' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'subtitle' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sections' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'inputs' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'label' } }] } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'calculations' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'label' } }] } }] } }] } as unknown as DocumentNode<PartialTemplateFragmentFragment, unknown>;
export const PartialCompleteSurveyFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'partialCompleteSurveyFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CompleteSurvey' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sections' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'inputs' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplate' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialTemplateFragment' } }] } }] } }] } as unknown as DocumentNode<PartialCompleteSurveyFragmentFragment, unknown>;
export const PartialDoctorFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'partialDoctor' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Doctor' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'dateOfBirth' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'thumbnail' } }] } }] } as unknown as DocumentNode<PartialDoctorFragment, unknown>;
export const PartialPatientFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'partialPatient' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Patient' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'dateOfBirth' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'gender' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sex' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'thumbnail' } }] } }] } as unknown as DocumentNode<PartialPatientFragment, unknown>;
export const NameFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'name' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'NameInfo' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } }] } }] } as unknown as DocumentNode<NameFragment, unknown>;
export const PartialUserFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'partialUser' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'MediqUser' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'group' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'name' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'accountDetails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'isRegistered' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'name' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'name' } }] } }] } }] } as unknown as DocumentNode<PartialUserFragment, unknown>;
export const PatientFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'patient' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Patient' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'firstName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'middleName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'lastName' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'dateOfBirth' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'gender' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sex' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'thumbnail' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'insuranceNumber' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'ethnicity' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'addresses' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'address' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'city' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'country' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'postalCode' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'province' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'emails' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'email' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'phoneNumbers' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'number' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } }] } }] } }] } as unknown as DocumentNode<PatientFragment, unknown>;
export const TemplateFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'templateFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyTemplate' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'subtitle' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sections' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'title' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'subtitle' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'calculationId' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'inputs' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'calculationId' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'category' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'label' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'options' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'placeholder' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'required' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'type' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'uploadAccept' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'uploadMultiple' } }] } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'calculations' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'calculation' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'index' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'inputType' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'label' } },{ 'kind':'Field','name':{ 'kind':'Name','value':'showOnComplete' } }] } }] } }] } as unknown as DocumentNode<TemplateFragmentFragment, unknown>;
export const CompleteSurveyFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'completeSurveyFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CompleteSurvey' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'sections' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'inputs' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplate' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'templateFragment' } }] } }] } }] } as unknown as DocumentNode<CompleteSurveyFragmentFragment, unknown>;
export const SurveyLinkFragmentFragmentDoc = { 'kind':'Document','definitions':[{ 'kind':'FragmentDefinition','name':{ 'kind':'Name','value':'surveyLinkFragment' },'typeCondition':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyLink' } },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'completedSurvey' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'completeSurveyFragment' } }] } },{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplate' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'templateFragment' } }] } }] } }] } as unknown as DocumentNode<SurveyLinkFragmentFragment, unknown>;
export const AddDoctorMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'addDoctorMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CreateDoctor' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addDoctor' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'createDoctor' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctor' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctor' } }] } }] } },...DoctorFragmentDoc.definitions] } as unknown as DocumentNode<AddDoctorMutationMutation, AddDoctorMutationMutationVariables>;
export const AddPatientDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'addPatient' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CreatePatient' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addPatient' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'createPatient' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patient' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<AddPatientMutation, AddPatientMutationVariables>;
export const CreateCompleteSurveyMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'createCompleteSurveyMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'createCompleteSurvey' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CreateCompleteSurvey' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'createCompleteSurvey' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'createCompleteSurvey' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'createCompleteSurvey' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<CreateCompleteSurveyMutationMutation, CreateCompleteSurveyMutationMutationVariables>;
export const CreateSurveyLinkMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'createSurveyLinkMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'createSurveyLink' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CreateSurveyLink' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'createSurveyLink' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'createSurveyLink' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'createSurveyLink' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<CreateSurveyLinkMutationMutation, CreateSurveyLinkMutationMutationVariables>;
export const EditDoctorDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'editDoctor' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'editDoctor' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'EditDoctor' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'editDoctor' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'editDoctor' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'editDoctor' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<EditDoctorMutation, EditDoctorMutationVariables>;
export const EditPatientDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'editPatient' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'editPatient' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'EditPatient' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'editPatient' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'editPatient' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'editPatient' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<EditPatientMutation, EditPatientMutationVariables>;
export const SurveyTemplateMutationDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'mutation','name':{ 'kind':'Name','value':'surveyTemplateMutation' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplate' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CreateSurveyTemplate' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'addSurveyTemplate' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'surveyTemplate' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplate' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'id' },'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'value' } }] } }] } }] } }] } as unknown as DocumentNode<SurveyTemplateMutationMutation, SurveyTemplateMutationMutationVariables>;
export const CompleteSurveyByIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'completeSurveyByIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'completeSurveyId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CompleteSurveyIdInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'completeSurveyById' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'completeSurveyId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'completeSurveyId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'completeSurveyFragment' } }] } }] } },...CompleteSurveyFragmentFragmentDoc.definitions,...TemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<CompleteSurveyByIdQueryQuery, CompleteSurveyByIdQueryQueryVariables>;
export const CompleteSurveyRangeQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'completeSurveyRangeQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Range' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'completeSurveyByRange' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'completeSurveyFragment' } }] } }] } },...CompleteSurveyFragmentFragmentDoc.definitions,...TemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<CompleteSurveyRangeQueryQuery, CompleteSurveyRangeQueryQueryVariables>;
export const DoctorsByDoctorIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'doctorsByDoctorIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctorIds' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorIdInput' } } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'doctorsByDoctorIds' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'doctorIds' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctorIds' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'doctor' } }] } }] } },...DoctorFragmentDoc.definitions] } as unknown as DocumentNode<DoctorsByDoctorIdQueryQuery, DoctorsByDoctorIdQueryQueryVariables>;
export const PartialCompleteSurveyByIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialCompleteSurveyByIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'completeSurveyId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'CompleteSurveyIdInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'completeSurveyById' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'completeSurveyId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'completeSurveyId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialCompleteSurveyFragment' } }] } }] } },...PartialCompleteSurveyFragmentFragmentDoc.definitions,...PartialTemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PartialCompleteSurveyByIdQueryQuery, PartialCompleteSurveyByIdQueryQueryVariables>;
export const PartialDoctorsByDoctorIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialDoctorsByDoctorIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctorIds' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'DoctorIdInput' } } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'doctorsByDoctorIds' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'doctorIds' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'doctorIds' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialDoctor' } }] } }] } },...PartialDoctorFragmentDoc.definitions] } as unknown as DocumentNode<PartialDoctorsByDoctorIdQueryQuery, PartialDoctorsByDoctorIdQueryQueryVariables>;
export const PartialDoctorsByRangeQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialDoctorsByRangeQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Range' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'doctorsByRange' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialDoctor' } }] } }] } },...PartialDoctorFragmentDoc.definitions] } as unknown as DocumentNode<PartialDoctorsByRangeQueryQuery, PartialDoctorsByRangeQueryQueryVariables>;
export const PartialPatientsByPatientIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialPatientsByPatientIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patientIds' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientIdInput' } } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'patientsByPatientId' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'patientIds' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patientIds' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialPatient' } }] } }] } },...PartialPatientFragmentDoc.definitions] } as unknown as DocumentNode<PartialPatientsByPatientIdQueryQuery, PartialPatientsByPatientIdQueryQueryVariables>;
export const PartialPatientsByRangeQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialPatientsByRangeQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Range' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'patientsByRange' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialPatient' } }] } }] } },...PartialPatientFragmentDoc.definitions] } as unknown as DocumentNode<PartialPatientsByRangeQueryQuery, PartialPatientsByRangeQueryQueryVariables>;
export const PartialSurveyTemplateByIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialSurveyTemplateByIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplateId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyTemplateIdInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplateById' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'surveyTemplateId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplateId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialTemplateFragment' } }] } }] } },...PartialTemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PartialSurveyTemplateByIdQueryQuery, PartialSurveyTemplateByIdQueryQueryVariables>;
export const PartialSurveyTemplateByRangeQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialSurveyTemplateByRangeQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'Range' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplateByRange' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'range' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'range' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialTemplateFragment' } }] } }] } },...PartialTemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<PartialSurveyTemplateByRangeQueryQuery, PartialSurveyTemplateByRangeQueryQueryVariables>;
export const PartialUserByUserUniqueIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'partialUserByUserUniqueIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'uniqueId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'String' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'userByUserUniqueId' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'uniqueId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'uniqueId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'partialUser' } }] } }] } },...PartialUserFragmentDoc.definitions,...NameFragmentDoc.definitions] } as unknown as DocumentNode<PartialUserByUserUniqueIdQueryQuery, PartialUserByUserUniqueIdQueryQueryVariables>;
export const PatientsByPatientIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'patientsByPatientIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patientIds' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'ListType','type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'PatientIdInput' } } } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'patientsByPatientId' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'patientIds' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'patientIds' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'patient' } }] } }] } },...PatientFragmentDoc.definitions] } as unknown as DocumentNode<PatientsByPatientIdQueryQuery, PatientsByPatientIdQueryQueryVariables>;
export const SurveyLinkByIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'surveyLinkByIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyLinkId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyLinkIdInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyLinkById' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'surveyLinkId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyLinkId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'surveyLinkFragment' } }] } }] } },...SurveyLinkFragmentFragmentDoc.definitions,...CompleteSurveyFragmentFragmentDoc.definitions,...TemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<SurveyLinkByIdQueryQuery, SurveyLinkByIdQueryQueryVariables>;
export const SurveyTemplateByIdQueryDocument = { 'kind':'Document','definitions':[{ 'kind':'OperationDefinition','operation':'query','name':{ 'kind':'Name','value':'surveyTemplateByIdQuery' },'variableDefinitions':[{ 'kind':'VariableDefinition','variable':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplateId' } },'type':{ 'kind':'NonNullType','type':{ 'kind':'NamedType','name':{ 'kind':'Name','value':'SurveyTemplateIdInput' } } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'Field','name':{ 'kind':'Name','value':'surveyTemplateById' },'arguments':[{ 'kind':'Argument','name':{ 'kind':'Name','value':'surveyTemplateId' },'value':{ 'kind':'Variable','name':{ 'kind':'Name','value':'surveyTemplateId' } } }],'selectionSet':{ 'kind':'SelectionSet','selections':[{ 'kind':'FragmentSpread','name':{ 'kind':'Name','value':'templateFragment' } }] } }] } },...TemplateFragmentFragmentDoc.definitions] } as unknown as DocumentNode<SurveyTemplateByIdQueryQuery, SurveyTemplateByIdQueryQueryVariables>;
export type AddDoctorMutationMutationStore = OperationStore<AddDoctorMutationMutation, AddDoctorMutationMutationVariables>;
export type AddPatientMutationStore = OperationStore<AddPatientMutation, AddPatientMutationVariables>;
export type CreateCompleteSurveyMutationMutationStore = OperationStore<CreateCompleteSurveyMutationMutation, CreateCompleteSurveyMutationMutationVariables>;
export type CreateSurveyLinkMutationMutationStore = OperationStore<CreateSurveyLinkMutationMutation, CreateSurveyLinkMutationMutationVariables>;
export type EditDoctorMutationStore = OperationStore<EditDoctorMutation, EditDoctorMutationVariables>;
export type EditPatientMutationStore = OperationStore<EditPatientMutation, EditPatientMutationVariables>;
export type SurveyTemplateMutationMutationStore = OperationStore<SurveyTemplateMutationMutation, SurveyTemplateMutationMutationVariables>;
export type CompleteSurveyByIdQueryQueryStore = OperationStore<CompleteSurveyByIdQueryQuery, CompleteSurveyByIdQueryQueryVariables>;
export type CompleteSurveyRangeQueryQueryStore = OperationStore<CompleteSurveyRangeQueryQuery, CompleteSurveyRangeQueryQueryVariables>;
export type DoctorsByDoctorIdQueryQueryStore = OperationStore<DoctorsByDoctorIdQueryQuery, DoctorsByDoctorIdQueryQueryVariables>;
export type PartialCompleteSurveyByIdQueryQueryStore = OperationStore<PartialCompleteSurveyByIdQueryQuery, PartialCompleteSurveyByIdQueryQueryVariables>;
export type PartialDoctorsByDoctorIdQueryQueryStore = OperationStore<PartialDoctorsByDoctorIdQueryQuery, PartialDoctorsByDoctorIdQueryQueryVariables>;
export type PartialDoctorsByRangeQueryQueryStore = OperationStore<PartialDoctorsByRangeQueryQuery, PartialDoctorsByRangeQueryQueryVariables>;
export type PartialPatientsByPatientIdQueryQueryStore = OperationStore<PartialPatientsByPatientIdQueryQuery, PartialPatientsByPatientIdQueryQueryVariables>;
export type PartialPatientsByRangeQueryQueryStore = OperationStore<PartialPatientsByRangeQueryQuery, PartialPatientsByRangeQueryQueryVariables>;
export type PartialSurveyTemplateByIdQueryQueryStore = OperationStore<PartialSurveyTemplateByIdQueryQuery, PartialSurveyTemplateByIdQueryQueryVariables>;
export type PartialSurveyTemplateByRangeQueryQueryStore = OperationStore<PartialSurveyTemplateByRangeQueryQuery, PartialSurveyTemplateByRangeQueryQueryVariables>;
export type PartialUserByUserUniqueIdQueryQueryStore = OperationStore<PartialUserByUserUniqueIdQueryQuery, PartialUserByUserUniqueIdQueryQueryVariables>;
export type PatientsByPatientIdQueryQueryStore = OperationStore<PatientsByPatientIdQueryQuery, PatientsByPatientIdQueryQueryVariables>;
export type SurveyLinkByIdQueryQueryStore = OperationStore<SurveyLinkByIdQueryQuery, SurveyLinkByIdQueryQueryVariables>;
export type SurveyTemplateByIdQueryQueryStore = OperationStore<SurveyTemplateByIdQueryQuery, SurveyTemplateByIdQueryQueryVariables>;