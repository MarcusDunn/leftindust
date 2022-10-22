import type { Route } from '@/features/View';

import IrisLoginPage from '@/features/Account/IrisLoginPage.svelte';

import SettingsPage from '@/features/Settings/SettingsPage.svelte';
import IrisPage from '@/features/Iris/IrisPage.svelte';
import RecordingsPage from '@/features/Recordings/RecordingsPage.svelte';
import RecordingOptionsPage from '@/features/Recording/RecordingOptionsPage.svelte';

const routes: Route[] = [
  {
    path: '/',
    component: RecordingsPage,
    master: true,
  },
  {
    path: '/options/',
    component: RecordingOptionsPage,
    master: true,
  },
  {
    path: '/account/login/',
    component: IrisLoginPage,
  },
  {
    path: '/settings/',
    component: SettingsPage,
  },
];

export default routes;
