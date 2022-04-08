import type { SetStorageParameters } from './modules/types/StorageModuleTypes';

import type { MessageBoxParameters } from './modules/types/DialogModuleTypes';

import { isElectron } from './modules/ElectronModule';

import DialogElectronModule from './modules/electron/DialogElectronModule';
import StorageElectronModule from './modules/electron/StorageElectronModule';
import SystemElectronModule from './modules/electron/SystemElectronModule';

import DialogWebModule from './modules/web/DialogWebModule';
import StorageWebModule from './modules/web/StorageWebModule';
import SystemWebModule from './modules/web/SystemWebModule';

export type NativeAPIs = {
  Dialog: {
    alert: (options: MessageBoxParameters) => Promise<number>;
  };
  System: {
    platform: () => string;
  };
  Storage: {
    set: (store: SetStorageParameters) => Promise<any>;
    get: (store: { key: string }) => Promise<any>;
  };
};

export const getNativeAPI = (forceWebAPI?: boolean): NativeAPIs => {
  // For now we're just returning electron apis
  if (isElectron() && !forceWebAPI) {
    return {
      Dialog: DialogElectronModule,
      System: SystemElectronModule,
      Storage: StorageElectronModule,
    };
  }

  return {
    Dialog: DialogWebModule,
    System: SystemWebModule,
    Storage: StorageWebModule,
  };
};

export default getNativeAPI;
