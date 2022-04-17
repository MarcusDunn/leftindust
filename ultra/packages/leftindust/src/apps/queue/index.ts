import 'framework7-icons';
import '@/style/fonts/fonts.css';
import '@/style/index.scss';

import 'framework7/css';

import 'svelte';

import Framework7 from 'framework7';
import Framework7Svelte from 'framework7-svelte';

import App from './App.svelte';
import { setupI18n } from '@/language';
import { isLoading } from 'svelte-i18n';

Framework7.use(Framework7Svelte);

// @ts-expect-error
document.getElementsByTagName('body')[0].style.zoom = '90%';

setupI18n();

const f7params = {
  theme: 'ios',
  autoDarkTheme: true,
  name: 'Queue',
  id: 'com.leftindust.queue',
};

isLoading.subscribe((loading) => {
  if (!loading) new App({
    target: document.body,
    props: {
      f7params,
    },
  });
});