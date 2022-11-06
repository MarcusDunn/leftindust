import type { Framework7Parameters } from 'framework7/types';
import 'framework7-icons';
import '@/style/fonts/fonts.css';
import '@/style/index.scss';

import 'framework7/css/bundle';

import 'svelte';

import App from './App.svelte';
import { initMain } from '@/features/App';

const f7params: Framework7Parameters = {
  theme: 'ios',
  autoDarkMode: true,
  name: 'Marissa',
  id: 'com.leftindust.marissa',
};

initMain(App, f7params, () => import('./routes'));