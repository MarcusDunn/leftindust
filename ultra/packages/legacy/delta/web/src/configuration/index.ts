export type Server = {
  address: string;
  port: string;
};

export type FirebaseConfig = {
  apiKey: string;
  authDomain: string;
  databaseURL: string;
  projectId: string;
  storageBucket: string;
  messagingSenderId: string;
  appId: string;
};

export type AppSettings = {
  language: string;
};

export type AppConfiguration = {
  name: string;
  id: string;
  port: string;
};

export type Configuration = {
  app: AppConfiguration;
  appSettings: AppSettings;
  server: {
    leftindust: Server;
    firebase: FirebaseConfig;
  };
};

const app: AppConfiguration = {
  name: 'MedIQ',
  id: 'com.leftindust.mediq',
  // Port to start the frontend dev server
  port: '5000',
};

const appSettings: AppSettings = {
  language: 'en-US',
};

const CONFIGURATION: Configuration = {
  app,
  appSettings,
  server: {
    leftindust: {
      address: 'http://192.168.0.11',
      port: '8080',
    },
    firebase: {
      apiKey: 'AIzaSyDwHAnFFB6ouYkL068TmFubVpa8Mlp8UBE',
      authDomain: 'mediq-backend.firebaseapp.com',
      databaseURL: 'https://mediq-backend.firebaseio.com',
      projectId: 'mediq-backend',
      storageBucket: 'mediq-backend.appspot.com',
      messagingSenderId: '710125939146',
      appId: '1:710125939146:web:b4d6cebab35d37d7f5c733',
    },
  },
};

export default CONFIGURATION;
