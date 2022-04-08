import { f7, f7ready } from 'framework7-svelte';
import type { Router } from 'framework7/types';
import { get } from 'svelte/store';

import { history, currentHistoryIndexes, forwardHistoryEventCalled } from './store';

export type History = Record<string, (Router.Route & { master: boolean })[]>;

export const goBackInHistory = (router: Router.Router): void => {
  router.back();
};

export const goForwardInHistory = (router: Router.Router): void => {
  const historyValue = get(history);

  const { view } = router;
  const index = get(currentHistoryIndexes)[view.name];

  if (view.name) {
    const route = historyValue[view.name][index + 1];

    // This is a bit nasty but it'll tell the subscription in the "page:afterin" listener
    // a way to differentiate whether the route is new, or from forward history
    forwardHistoryEventCalled.set(true);

    router.navigate(route.url, route.props);
  }
};

export const observeHistory = (): void => {
  f7ready(() => {
    f7.$(document)
      .on('page:afterin', (event) => {
        const page: Router.Page = (<CustomEvent>event).detail;
        const { direction, view } = page;
        const { url, props, route } = page.route;
        const { master } = route;

        const historyValue = get(history);
        const indexes = get(currentHistoryIndexes);

        const incrementIndex = (amount: number) => {
          currentHistoryIndexes.set({ ...get(currentHistoryIndexes), [view.name]: indexes[view.name] + amount });
        };

        if (view.name) {
          historyValue[view.name] ??= [];
          indexes[view.name] ??= -1;

          if (direction === 'backward') {
            incrementIndex(-1);
          } else {
            if (!get(forwardHistoryEventCalled)) {
              historyValue[view.name].length = indexes[view.name] + 1;
              // The error suppression is due to "props" being typed as "object | Record<string, unknown>", which
              // is redundant. This is also due to newer versions of typescript disliking "object" as a type
              // @ts-expect-error
              history.set({ ...historyValue, [view.name]: [...historyValue[view.name], { url, props, master }] });
            }

            incrementIndex(1);
          }

          forwardHistoryEventCalled.set(false);
        }
      });
  });
};