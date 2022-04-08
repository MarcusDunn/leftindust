import type { Path } from '@/apps/mediq/routes';
import type { View } from 'framework7-svelte';

import { f7 } from 'framework7-svelte';
import { AppPopups } from '../App';
import { wizardOpen, wizardViewPrevious } from './store';

export const openWizard = (
  url: Path | string,
  props = {},
): boolean => {
  const router: View['View']['router'] = f7.views.get('#view-wizard').router;

  // Set previous tab
  const tab = f7.$('.views > .tab-active').attr('id');

  if (tab) {
    if (tab !== 'view-wizard' && tab !== 'view-wizard-master-detail')
      wizardViewPrevious.set(`#${tab}`);

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
      wizardOpen.set(true);
    });

    return true;
  }

  return false;
};

export const closeWizard = (): void => {
  wizardOpen.set(false);
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