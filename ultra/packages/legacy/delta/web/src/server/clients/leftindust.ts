// Setup server uri
import { ApolloLink, HttpLink } from '@apollo/client';

import type { OperationDefinitionNode } from 'graphql';

import { onError } from '@apollo/client/link/error';
import { setContext } from '@apollo/client/link/context';
import firebase from 'firebase';

import CONFIGURATION from '../../configuration';

import { getMainDefinition } from '@apollo/client/utilities';

const httpLink = new HttpLink({
  uri: `${CONFIGURATION.server.leftindust.address}:${CONFIGURATION.server.leftindust.port}/graphql`,
  fetch,
});

const errorLink = onError(({ graphQLErrors, networkError }) => {
  if (graphQLErrors) {
    console.log('graphQLErrors', graphQLErrors);
  }
  if (networkError) {
    console.log('networkError', networkError);
  }
});

const withToken = setContext(async () => {
  // Get firebase token and return it
  const user = firebase.auth().currentUser;
  const token = await user?.getIdToken();
  console.log(token);
  return { token };
});

const authMiddleware = new ApolloLink((operation, forward) => {
  // Receive async token and add it to our header
  const { token } = operation.getContext();
  operation.setContext(() => ({
    headers: { 'mediq-auth-token': token },
  }));
  return forward(operation);
});

// To omit __typename from all mutations
const cleanTypenameLink = new ApolloLink((operation, forward) => {
  const omitTypename = (key: string, value: unknown) => (key === '__typename' ? undefined : value);

  const def = getMainDefinition(operation.query);
  if (def && (<OperationDefinitionNode>def).operation === 'mutation') {
    operation.variables = JSON.parse(JSON.stringify(operation.variables), omitTypename);
  }
  return forward ? forward(operation) : null;
});

// Complete finishing up the client
const leftindust = ApolloLink.from([
  withToken,
  errorLink,
  authMiddleware.concat(cleanTypenameLink.concat(httpLink)),
]);

export default leftindust;
