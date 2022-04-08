import type {
  Doctor,
  EmergencyContact,
  FirebaseInfo,
  Patient,
  User,
  Event,
  Visit,
  Record,
  IcdSimpleEntity,
  IcdLinearizationEntity,
  GraphQlFormTemplate,
  AssignedSurvey,
} from '../schema/leftindust.schema';

import type { RangeInput } from '../schema/leftindust.schema';

export type Person =
  | Patient['__typename']
  | Doctor['__typename']
  | User['__typename'];

export type Document =
  | Event['__typename']
  | Visit['__typename']
  | Record['__typename']
  | FirebaseInfo['__typename']
  | EmergencyContact['__typename']
  | IcdSimpleEntity['__typename']
  | IcdLinearizationEntity['__typename']
  | GraphQlFormTemplate['__typename']
  | AssignedSurvey['__typename']

export type DataType = Person | Document;

export const defaultRangeInput: RangeInput = {
  from: 0,
  to: 21,
};