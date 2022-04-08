import { Layouts } from '../../types';

export interface UserSettings {
  setup: boolean;
  options: {
    layout: Layouts;
    dateFormat: string;
    showToolbarLabels: boolean;
    views: {
      clients: {
        patientContactInformationExpanded: boolean;
      };
    };
  };
  theme: {
    transparency: boolean;
    mode: 'auto' | 'dark' | 'light';
  };
}

const USER_SETTINGS_TEMPLATE: Readonly<UserSettings> = {
  setup: false,
  options: {
    layout: Layouts.Bundled,
    dateFormat: 'dd/mm/yyyy',
    showToolbarLabels: true,
    views: {
      clients: {
        patientContactInformationExpanded: false,
      },
    },
  },
  theme: {
    transparency: true,
    mode: 'auto',
  },
};

export default USER_SETTINGS_TEMPLATE;
