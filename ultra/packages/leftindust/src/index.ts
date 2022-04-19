import type { Framework7Parameters } from 'framework7/types';

import { initMain } from './features/App';

import 'framework7-icons';
import '@/style/fonts/fonts.css';
import '@/style/index.scss';
import 'framework7/css/bundle';

import 'svelte';

import App from './App.svelte';
import { setupI18n } from '@/language';
import { isLoading } from 'svelte-i18n';

const f7params: Framework7Parameters = {
  theme: 'ios',
  autoDarkMode: true,
  name: 'leftindust',
  id: 'com.leftindust.leftindust',
};

initMain(App, f7params);