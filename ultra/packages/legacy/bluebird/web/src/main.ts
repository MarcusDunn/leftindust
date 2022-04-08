import 'framework7-icons';
import '@framework/assets/fonts/fonts.css';

import '@framework/styles/index.scss';
import 'framework7/framework7-bundle.css';

import { isElectron } from '@framework/bridge/modules/ElectronModule';

import 'svelte';

import Framework7 from 'framework7/lite-bundle';
import Framework7Svelte from 'framework7-svelte';

import CONFIGURATION from '@framework/configuration/index';
import ROUTES from '@/routes';

import getNativeAPI from '@framework/bridge/index';

import App from '@/App.svelte';

Framework7.use(Framework7Svelte);

if (!isElectron()) {
  const { Storage } = getNativeAPI();

  void (async () => {
    await Storage.set({
      key: 'settings',
      defaults: CONFIGURATION.appSettings,
    });
  })();

  // @ts-expect-error
  document.getElementsByTagName('body')[0].style.zoom = '90%';
} else {
  document.getElementsByTagName('body')[0].style.background = 'transparent';
}

const f7params = {
  ...{
    name: CONFIGURATION.app.name,
    id: CONFIGURATION.app.id,
    // TODO: For now, theming will default to iOS
    theme: 'ios',
    autoDarkTheme: true,
    routes: ROUTES,
  },
  routes: ROUTES,
};

new App({
  target: document.body,
  props: {
    f7params,
  },
});
