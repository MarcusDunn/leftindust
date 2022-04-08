/*
 * Bridge Component for Electron
 * This exists for easy access to native api calls
 * */

import type { SetStorageParameters } from '@framework/bridge/modules/types/StorageModuleTypes';
import type { MessageBoxParameters } from '@framework/bridge/modules/types/DialogModuleTypes';

import { ipcMain, BrowserWindow, dialog } from 'electron';
import ElectronStore from 'electron-store';

const getStore = (defaults?: any) => {
    if (defaults) {
        return new ElectronStore({
            defaults: {
                ...defaults,
            },
        });
    }

    return new ElectronStore();
};

const receiver = (win: BrowserWindow): void => {
    /*
           * Storage
           * */

    ipcMain.on(
        'set-storage',
        (event, params: SetStorageParameters) => {
            getStore(params.defaults).set({
                [params.key]: params.value,
            });

            event.reply(
                'set-storage-return',
                getStore().get(params.key),
            );
        },
    );

    ipcMain.on('get-storage', (event, params: { key: string }) => {
        getStore().get(params.key);

        event.reply('get-storage-return', getStore().get(params.key));
    });

    ipcMain.on('delete-storage', (event, params: { key: string }) => {
        getStore().delete(params.key);

        event.reply(
            'delete-storage-return',
            getStore().get(params.key),
        );
    });

    /*
           * Dialogs
           * */
    ipcMain.on(
        'display-alert',
        async (event, params: MessageBoxParameters) => {
            const result = await dialog.showMessageBox(win, params);

            event.reply('display-alert-return', result.response);
        },
    );
};

export default receiver;
