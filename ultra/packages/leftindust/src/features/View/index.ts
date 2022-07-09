import type { SvelteComponentDev } from 'svelte/internal';
import type { Popover as PopoverType, Popup as PopupType } from 'framework7/types';
import { f7 } from 'framework7-svelte';

export interface Route<T extends string = string> {
  path: T;
  component: typeof SvelteComponentDev;
  routes?: Array<Route>;
  detailRoutes?: Array<Route>;

  [key: string]: unknown;
}

export const masterDetailBreakpoint = 1300;

export const openPopupUrl = (
  url: string,
  props = {},
): void => {
  const { router } = f7.views.get('#view-popup');

  router.navigate(url, {
    props,
    force: true,
    animate: false,
    ignoreCache: true,
  });

  f7.popup.open('#popup-default');
};

export const openPopup = (popup: PopupType.Popup): PopupType.Popup => popup.open();

export const openPopover = (popover: PopoverType.Popover, event: Event | HTMLElement | string): unknown =>
  // @ts-expect-error
  popover.open(event.currentTarget ? event.currentTarget : event, true);