import firebase from 'firebase/app';
import 'firebase/auth';

import leftindust from './clients/leftindust';
import CONFIGURATION from '../configuration';

import { InMemoryCache } from '@apollo/client';
import { SvelteApolloClient } from 'svelte-apollo-client';

// Initialize Firebase
firebase.initializeApp(CONFIGURATION.server.firebase);

// Initialize realtime db
const realtime = firebase.database();

const client = SvelteApolloClient({
  link: leftindust,
  cache: new InMemoryCache({
    addTypename: true,
  }),
});

export { client, realtime };
