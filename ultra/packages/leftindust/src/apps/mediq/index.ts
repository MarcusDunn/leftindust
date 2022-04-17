import 'framework7-icons';
import '@/style/fonts/fonts.css';
import '@/style/index.scss';

import 'framework7/css/bundle';

import 'svelte';

import Framework7 from 'framework7/lite-bundle';
import Framework7Svelte from 'framework7-svelte';

import App from './App.svelte';
import { setupI18n } from '@/language';
import routes from './routes';
import { isLoading } from 'svelte-i18n';

// https://framework7.io/svelte/init-app
// @ts-expect-error
Framework7.use(Framework7Svelte);

// @ts-expect-error
document.getElementsByTagName('body')[0].style.zoom = '90%';

setupI18n();

const f7params = {
  theme: 'ios',
  autoDarkTheme: true,
  name: 'MedIQ',
  id: 'com.leftindust.mediq',
  routes,
};

isLoading.subscribe((loading) => {
  if (!loading) new App({
    target: document.body,
    props: {
      f7params,
    },
  });
});