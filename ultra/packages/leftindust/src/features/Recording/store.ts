import { LanguageCode, Specialty } from '@aws-sdk/client-transcribe-streaming';
import { writable } from 'svelte/store';

export const recordingTimer = writable<'0' | '3' | '5' | '10' | '15'>('0');
export const defaultAudioDeviceId = writable<string>();

export const recordingLanguage = writable<LanguageCode>(LanguageCode.EN_US);
export const recordingMedicalSpecialty = writable<Specialty>(Specialty.PRIMARYCARE);
export const recordingConversation = writable<boolean>(false);

export const recordingSequenceStarted = writable(false);
export const forceRecordingSequence = writable(false);