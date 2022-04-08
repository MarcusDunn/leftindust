import CONFIGURATION, { AppSettings } from '@framework/configuration';
import { app } from 'electron';

import ElectronStore from 'electron-store';

const store = new ElectronStore<{
    check: boolean;
    settings: AppSettings;
}>({
    defaults: {
        check: false,
        settings: {
            ...CONFIGURATION.appSettings,
            language: app.getLocale(),
        },
    },
});

export default store;
