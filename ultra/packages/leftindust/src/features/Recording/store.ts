import { writable } from 'svelte/store';

export const recordingTimer = writable<'0' | '3' | '5' | '10' | '15'>('0');
export const recordingSequenceStarted = writable(false);
export const forceRecordingSequence = writable(false);