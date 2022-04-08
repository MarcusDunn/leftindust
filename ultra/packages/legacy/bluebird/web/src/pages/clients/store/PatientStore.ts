import { writable } from 'svelte/store';

export enum PatientsLayout {
  Default = 'PatientsDefaultLayout',
  Search = 'PatientsSearchLayout',
}

export const SelectedPatientsLayout = writable<PatientsLayout>(PatientsLayout.Default);
