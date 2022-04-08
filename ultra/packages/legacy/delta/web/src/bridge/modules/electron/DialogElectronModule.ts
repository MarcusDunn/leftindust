import type { MessageBoxParameters } from '../types/DialogModuleTypes';
import { ipcRenderer } from '../ElectronModule';

const ipc = ipcRenderer();

const alert = (options: MessageBoxParameters): Promise<number> =>
  new Promise((resolve) => {
    if (ipc) {
      ipc.send('display-alert', options);

      ipc.once('display-alert-return', (_event, response) => {
        resolve(response);
      });
    }
  });

export default {
  alert,
};
