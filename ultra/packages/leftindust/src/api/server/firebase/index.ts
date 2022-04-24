import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';
import { getDatabase } from 'firebase/database';
import config from '@/../config.json';

export const firebase = initializeApp(config.firebase);
export const auth = getAuth(firebase);
export const database = getDatabase(firebase);