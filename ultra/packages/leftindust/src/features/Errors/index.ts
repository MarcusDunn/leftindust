import { f7 } from 'framework7-svelte';
import { AppPopups } from '../App';

// Last piece of code to execute when the window throws an exception
export const observeWindowErrors = (): void => window.addEventListener('error', () => f7.popup.open(`#${AppPopups.Error}`));