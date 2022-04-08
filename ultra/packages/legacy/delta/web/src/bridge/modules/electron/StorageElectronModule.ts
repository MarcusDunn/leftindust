import type { SetStorageParameters } from '../types/StorageModuleTypes';
import { ipcRenderer } from '../ElectronModule';

const ipc = ipcRenderer();

const set = (store: SetStorageParameters): Promise<unknown> =>
  new Promise((resolve) => {
    if (ipc) {
      ipc.send('set-storage', store);

      ipc.once('set-storage-return', (_event, response) => {
        resolve(response);
      });
    }
  });

const get = (store: { key: string }): Promise<unknown> =>
  new Promise((resolve) => {
    if (ipc) {
      ipc.send('get-storage', store);

      ipc.once('get-storage-return', (_event, response) => {
        resolve(response);
      });
    }
  });

const remove = (store: { key: string }): Promise<unknown> =>
  new Promise((resolve) => {
    if (ipc) {
      ipc.send('delete-storage', store);

      ipc.once('delete-storage-return', (_event, response) => {
        resolve(response);
      });
    }
  });

export default {
  set,
  get,
  remove,
};
