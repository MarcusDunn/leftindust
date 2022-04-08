import { f7, f7ready } from 'framework7-svelte';

import type { MessageBoxParameters } from '../types/DialogModuleTypes';

const alert = (options: MessageBoxParameters): Promise<number> =>
  new Promise((resolve) => {
    f7ready(() => {
      f7.dialog
        .create({
          title: options.message,
          text: options.detail,
          verticalButtons: options.buttons.length > 2,
          buttons: options.buttons.map((button, index) => ({
            text: button,
            bold: options.defaultId
              ? index === options.defaultId
              : index === 0,
            onClick: () => {
              resolve(index);
            },
          })),
        })
        .open();
    });
  });

export default {
  alert,
};
