import type { Popover as PopoverType } from 'framework7/types';
import type { View } from 'framework7-svelte';

import { f7 } from 'framework7-svelte';
import { VIEW_PREVIOUS, WIZARD_ACTIVE } from '../store';
import language from '../languages';
import getNativeAPI from '../bridge';
import { get } from 'svelte/store';
import { AppPopups, AppViews, APP_ACTIVE_TAB } from './AppModule';

const { Dialog } = getNativeAPI();

export type Path =
  | '/dashboard/'
  | '/calendar/'
  | '/login/'
  | '/clients/'
  | '/patient/:selectable/'
  | '/doctor/:selectable/'
  | '/people/:selectables/'
  | '/settings/'
  | '/forms/'
  | '/form/'
  | '/users/'
  | '/user/:selectable/'
  | '/event/:selectable/'
  | '/visit/:selectable/'
  | '/events/:selectable/'
  | '/visits/:selectable/'
  | '/wizard/survey/preview/'
  | '/attachments/'
  | '/wizard/survey/'
  | '/wizard/visit/'
  | '/wizard/event/'
  | '/wizard/patient/'
  | '/wizard/doctor/'
  | '/wizard/user/'
  | '/wizard/survey/template/'
  | '/setup/'
  | '/blank/'
  | '(.*)';

export const openPopup = (
  url: Path | string,
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

export const openWizard = (
  url: Path | string,
  props = {},
): boolean => {
  const router: View['View']['router'] = f7.views.get('#view-wizard').router;

  // Set previous tab
  const tab = f7.$('.views > .tab-active').attr('id');

  if (tab) {
    if (tab !== 'view-wizard' && tab !== 'view-wizard-master-detail')
      VIEW_PREVIOUS.set(`#${tab}`);

    f7.popover.close();
    f7.popup.open(`#${AppPopups.Wizard}`);


    // Navigate to wizard view
    router.navigate(url, {
      props,
      force: true,
      animate: false,
      ignoreCache: true,
    });

    f7.$(`#${AppPopups.Wizard}`).once('popup:opened', () => {
      f7.$('html').addClass('wizard-open');
      WIZARD_ACTIVE.set(true);
    });

    return true;
  }

  return false;
};

export const closeWizard = (): void => {
  WIZARD_ACTIVE.set(false);
  f7.$('html').removeClass('wizard-open');

  f7.popover.close();
  f7.popup.close();

  // Reset views
  f7.$(`#${AppPopups.Wizard}`).once('popup:closed', () => {
    f7.views.get('#view-wizard')
      .router
      .navigate('/blank/', {
        reloadAll: true,
      });
  });
};

export const openPopover = (popup: PopoverType.Popover, event: Event | HTMLElement | string): unknown =>
  // @ts-expect-error
  popup.open(event.currentTarget ? event.currentTarget : event, true);

export const safelyExitWizard = async (): Promise<boolean> => {
  if (get(WIZARD_ACTIVE)) {
    const result = await Dialog.alert({
      message: language().headers.unsavedChanges.text,
      detail: language().wizard.placeholders.unsavedChanges.text,
      buttons: [language().buttons.continue.text, language().buttons.cancel.text],
      defaultId: 0,
      cancelId: 1,
    });

    if (result === 0) {
      closeWizard();

      f7.$('.panel')
        .once('panel:opened', () => {
          return true;
        });
    }
  } else {
    return true;
  }

  return false;
};

export const safeNavigate = async (url: string, view: AppViews, props = {}): Promise<void> => {
  f7.popover.close();

  if (await safelyExitWizard()) {
    f7.popup.close();

    APP_ACTIVE_TAB.set(view);

    f7.views.get(`#${view}`).router.navigate(url, {
      force: true,
      ignoreCache: true,
      props: {
        ...props,
      },
    });
  }
};
