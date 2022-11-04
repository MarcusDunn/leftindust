import {
  createClient,
  dedupExchange,
  cacheExchange,
  fetchExchange,
  makeOperation,
} from '@urql/svelte';
import { retryExchange } from '@urql/exchange-retry';
import { authExchange } from '@urql/exchange-auth';
import { devtoolsExchange } from '@urql/devtools';
import { config } from '@/features/App';
import { auth } from '../firebase';
import type { ResolversTypes } from './requests';
import type { Range } from './schema/leftindust.schema';
import type { User } from 'firebase/auth';
import { get } from 'svelte/store';

export type Data<T = keyof Partial<ResolversTypes>> = {
  type: T;
  id: string;
};

export const resolversRecord: { [K in keyof ResolversTypes]: Record<string, any> } = {
  AddressType: {},
  Base64: {},
  Boolean: {},
  Clinic: {},
  ClinicId: {},
  ClinicIdInput: {},
  CompleteSurvey: {},
  CompleteSurveyId: {},
  CompleteSurveyIdInput: {},
  CompleteSurveySection: {},
  CompleteSurveySectionId: {},
  CompleteSurveyInputType: {},
  CompleteSurveySectionInput: {},
  CompleteSurveySectionInputId: {},
  Countries: {},
  CreateAddress: {},
  CreateClinic: {},
  CreateCompleteSurvey: {},
  CreateCompleteSurveyInput: {},
  CreateCompleteSurveySection: {},
  CreateSurveyTemplate: {},
  CreateSurveyTemplateCalculation: {},
  CreateSurveyTemplateSection: {},
  CreateSurveyTemplateSectionInput: {},
  CompleteSurveySectionNumberInput: {},
  CompleteSurveySectionStringInput: {},
  CompleteSurveySectionNumberArrayInput: {},
  CompleteSurveySectionStringArrayInput: {},
  Doctor: {},
  DoctorId: {},
  DoctorIdInput: {},
  Duration: {},
  EditClinic: {},
  EditDoctor: {},
  EditPatient: {},
  Ethnicity: {},
  Int: {},
  LocalDate: {},
  LocalDateTime: {},
  MediqGroup: {},
  MediqGroupId: {},
  MediqUser: {},
  MediqUserId: {},
  MediqUserUniqueIdInput: {},
  Mutation: {},
  NameInfo: {},
  Patient: {},
  PatientId: {},
  PatientIdInput: {},
  Query: {},
  Range: {},
  Sex: {},
  String: {},
  SurveyLink: {},
  SurveyLinkId: {},
  SurveyLinkIdInput: {},
  SurveyTemplate: {},
  SurveyTemplateCalculation: {},
  SurveyTemplateCalculationId: {},
  SurveyTemplateCategory: {},
  SurveyTemplateId: {},
  SurveyTemplateIdInput: {},
  SurveyTemplateInput: {},
  SurveyTemplateInputId: {},
  SurveyTemplateInputType: {},
  SurveyTemplateLinkIdInput: {},
  SurveyTemplateSection: {},
  SurveyTemplateSectionId: {},
  SurveyTemplateSectionIdInput: {},
  SurveyTemplateSectionInputIdInput: {},
  TemplateInputUploadType: {},
  UUID: {},
  UpdateNameInfo: {},
  UserAccountDetails: {},
  Address: {},
  AddressId: {},
  CreateContact: {},
  CreateEmail: {},
  CreateNameInfo: {},
  CreatePatient: {},
  CreateDoctor: {},
  CreateDoctorUser: {},
  CreateDoctorUserType: {},
  CreatePhone: {},
  CreateSurveyLink: {},
  CreateUser: {},
  Email: {},
  EmailId: {},
  EmailType: {},
  MediqGroupIdInput: {},
  Phone: {},
  PhoneId: {},
  PhoneType: {},
  Relationship: {},
};

export const resolversArray: { [K in keyof ResolversTypes]: any[] } = {
  AddressType: [],
  Base64: [],
  Boolean: [],
  Clinic: [],
  ClinicId: [],
  ClinicIdInput: [],
  CompleteSurvey: [],
  CompleteSurveyId: [],
  CompleteSurveyIdInput: [],
  CompleteSurveySection: [],
  CompleteSurveySectionId: [],
  CompleteSurveyInputType: [],
  CompleteSurveySectionInput: [],
  CompleteSurveySectionInputId: [],
  Countries: [],
  CreateAddress: [],
  CreateClinic: [],
  CreateCompleteSurvey: [],
  CreateCompleteSurveyInput: [],
  CreateCompleteSurveySection: [],
  CreateSurveyTemplate: [],
  CreateSurveyTemplateCalculation: [],
  CreateSurveyTemplateSection: [],
  CreateSurveyTemplateSectionInput: [],
  CompleteSurveySectionNumberInput: [],
  CompleteSurveySectionStringInput: [],
  CompleteSurveySectionNumberArrayInput: [],
  CompleteSurveySectionStringArrayInput: [],
  Doctor: [],
  DoctorId: [],
  DoctorIdInput: [],
  Duration: [],
  EditClinic: [],
  EditDoctor: [],
  EditPatient: [],
  Ethnicity: [],
  Int: [],
  LocalDate: [],
  LocalDateTime: [],
  MediqGroup: [],
  MediqGroupId: [],
  MediqUser: [],
  MediqUserId: [],
  MediqUserUniqueIdInput: [],
  Mutation: [],
  NameInfo: [],
  Patient: [],
  PatientId: [],
  PatientIdInput: [],
  Query: [],
  Range: [],
  Sex: [],
  String: [],
  SurveyLink: [],
  SurveyLinkId: [],
  SurveyLinkIdInput: [],
  SurveyTemplate: [],
  SurveyTemplateCalculation: [],
  SurveyTemplateCalculationId: [],
  SurveyTemplateCategory: [],
  SurveyTemplateId: [],
  SurveyTemplateIdInput: [],
  SurveyTemplateInput: [],
  SurveyTemplateInputId: [],
  SurveyTemplateInputType: [],
  SurveyTemplateLinkIdInput: [],
  SurveyTemplateSection: [],
  SurveyTemplateSectionId: [],
  SurveyTemplateSectionIdInput: [],
  SurveyTemplateSectionInputIdInput: [],
  TemplateInputUploadType: [],
  UUID: [],
  UpdateNameInfo: [],
  UserAccountDetails: [],
  Address: [],
  AddressId: [],
  CreateContact: [],
  CreateEmail: [],
  CreateNameInfo: [],
  CreatePatient: [],
  CreateDoctor: [],
  CreateDoctorUser: [],
  CreateDoctorUserType: [],
  CreatePhone: [],
  CreateSurveyLink: [],
  CreateUser: [],
  Email: [],
  EmailId: [],
  EmailType: [],
  MediqGroupIdInput: [],
  Phone: [],
  PhoneId: [],
  PhoneType: [],
  Relationship: [],
};

export type Client = {
  authentication: boolean;
}


export const client = (options: Client = { authentication: true }) => createClient({
  url: `${config.mockingbird.address}/graphql`,
  maskTypename: true,
  exchanges: [
    devtoolsExchange,
    dedupExchange,
    cacheExchange,
    retryExchange({
      initialDelayMs: 1000,
      maxDelayMs: 15000,
      randomDelay: true,
      maxNumberAttempts: 2,
      retryIf: (error) => !!(error && error.networkError),
      retryWith: (error, operation) => {
        if (error.networkError) {
          const context = { ...operation.context };
          return { ...operation, context };
        }
        return null;
      },
    }),
    ...options.authentication ? [
      authExchange<{ token: string | undefined } | undefined>({
        getAuth: async () => {
          const user: User = await new Promise((resolve, reject) => {
            const unsubscribe = auth.onAuthStateChanged((u) => {
              unsubscribe();
              if (u) resolve(u);
            }, reject);
          });
          
          const token = await user?.getIdToken();
  
          const refreshToken = await user?.getIdToken();
          
          return { token, refreshToken };
        },
        addAuthToOperation: ({ authState, operation }) => {
          if (!authState || !authState.token) return operation;
        
          const fetchOptions =
            typeof operation.context.fetchOptions === 'function'
              ? operation.context.fetchOptions()
              : operation.context.fetchOptions || {};
        
          return makeOperation(operation.kind, operation, {
            ...operation.context,
            fetchOptions: {
              ...fetchOptions,
              headers: {
                ...fetchOptions.headers,
                // Authorization: authState.token,
              },
            },
          });
        },
      }),
    ] : [],
    fetchExchange,
  ],
});

export const defaultRangeInput: Range = {
  from: 0,
  to: 21,
};

export * from './requests';
export * from './schema/leftindust.schema';