import { readable } from 'svelte/store';

export const isDarkMode = readable(window.matchMedia('(prefers-color-scheme: dark)').matches, (set) => {
  window
    .matchMedia('(prefers-color-scheme: dark)')
    .addEventListener('change', (e) => set(e.matches));
});
