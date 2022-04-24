import {
  createClient,
  dedupExchange,
  cacheExchange,
  fetchExchange,
  makeOperation,
} from '@urql/svelte';
import { retryExchange } from '@urql/exchange-retry';
import { authExchange } from '@urql/exchange-auth';
import config from '@/../config.json';
import { auth } from '../firebase';
import type { ResolversTypes } from './requests';
import type { RangeInput } from './schema/leftindust.schema';
import type { User } from 'firebase/auth';

export type Data<T = keyof Partial<ResolversTypes>> = {
  type: T;
  id: string;
};

export const client = createClient({
  url: `${config.mockingbird.address}:${config.mockingbird.port}/graphql`,
  maskTypename: true,
  exchanges: [
    dedupExchange,
    cacheExchange,
    retryExchange({
      initialDelayMs: 1000,
      maxDelayMs: 15000,
      randomDelay: true,
      maxNumberAttempts: 2,
      retryIf: (error) => !!(error && error.networkError),
    }),
    authExchange<{ token: string | undefined } | undefined>({
      getAuth: async ({ authState }) => {
        const user: User = await new Promise((resolve, reject) => {
          const unsubscribe = auth.onAuthStateChanged((u) => {
            unsubscribe();
            if (u) resolve(u);
          }, reject);
        });
        
        const token = await user?.getIdToken();

        if (!authState) {
          return { token };
        }
      },
      addAuthToOperation: ({ authState, operation }) => {
        if (!authState || !authState.token) {
          return operation;
        }
      
        const fetchOptions =
          typeof operation.context.fetchOptions === 'function'
            ? operation.context.fetchOptions()
            : operation.context.fetchOptions || {};
      
        return makeOperation(operation.kind, operation, {
          ...operation.context,
          fetchOptions: {
            ...fetchOptions,
            headers: {
              ...fetchOptions.headers,
              'mediq-auth-token': authState.token,
            },
          },
        });
      },
    }),
    fetchExchange,
  ],
});

export const defaultRangeInput: RangeInput = {
  from: 0,
  to: 21,
};

export * from './requests';
export * from './schema/leftindust.schema';