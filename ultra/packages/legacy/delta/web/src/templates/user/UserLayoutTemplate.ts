import type { Selectable } from '../../modules/SelectModule';
import type { PluginTypes } from '../../plugins';
import type { Document, Person } from '../../requests';
import type { SvelteGridConstraints } from '../../ui/layout/PluginGridUI/module';

export type UserLayoutTemplate = {
  pinned: {
    [K in NonNullable<Person | Document>]: Record<string, Selectable[]>;
  };
  grid: {
    [K in PluginTypes]: {
      [K in NonNullable<Person | Document>]: Record<string, Record<string, SvelteGridConstraints>>;
    }
  }
};

const selectable: { [K in NonNullable<Person | Document>]: Record<string, any> } = {
  Patient: {},
  Doctor: {},
  User: {},
  Event: {},
  Visit: {},
  Record: {},
  FirebaseInfo: {},
  EmergencyContact: {},
  IcdLinearizationEntity: {},
  IcdSimpleEntity: {},
  GraphQLFormTemplate: {},
  AssignedSurvey: {},
};

const USER_LAYOUT_TEMPLATE: UserLayoutTemplate = {
  pinned: {
    ...selectable,
  },
  grid: {
    bundles: {
      ...selectable,
    },
    clusters: {
      ...selectable,
    },
    comparables: {
      ...selectable,
    },
    stacks: {
      ...selectable,
    },
    widgets: {
      ...selectable,
    },
    attachments: {
      ...selectable,
    },
    attributes: {
      ...selectable,
    },
  },
};

export default USER_LAYOUT_TEMPLATE;
