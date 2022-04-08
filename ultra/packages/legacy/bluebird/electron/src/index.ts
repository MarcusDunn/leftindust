import {
    app,
    BrowserWindow,
    BrowserWindowConstructorOptions,
    Notification,
} from 'electron';

import macosRelease from 'macos-release';
import { join } from 'path';
import { autoUpdater } from 'electron-updater';
import logger from 'electron-log';

import receiver from '@/receiver';

import language from '@framework/languages';
import CONFIGURATION from '@framework/configuration';
import os from 'os';
import store from '@/utils/store';

const isProd =
    process.env.NODE_ENV === 'production' ||
    !/[\\/]electron/.exec(process.execPath); // !process.execPath.match(/[\\/]electron/);

logger.log('App starting...');

store.set('check', true);
logger.log('Checking if settings store works correctly.');
logger.log(
    store.get('check')
        ? 'Settings store works correctly.'
        : 'Settings store has a problem.',
);
logger.log(store.get('settings'));

let mainWindow: BrowserWindow | null;
let notification: Notification | null;

const isMac = process.platform === 'darwin';

const createWindow = async () => {
    const browserWindowOptions: BrowserWindowConstructorOptions = {
        width: 1000,
        height: 700,
        minHeight: 500,
        minWidth: 901,
        show: false,
        frame: true,
        webPreferences: {
            zoomFactor: 0.9,
            devTools: !isProd,
            contextIsolation: false,
            enableRemoteModule: false,
            nodeIntegration: true,
        },
    };

    // Mac specific browser window options
    if (process.platform === 'darwin') {
        const release = macosRelease(os.release());

        browserWindowOptions.vibrancy = <const>'appearance-based';
        browserWindowOptions.trafficLightPosition = {
            x: 16,
            y: 19,
        };

        browserWindowOptions.transparent = true;
        browserWindowOptions.titleBarStyle =
            isMac &&
                release &&
                release.name === ('Big Sur' || 'Monterey')
                ? <const>'hidden'
                : <const>'hiddenInset';
    } else {
        browserWindowOptions.backgroundColor = '#fff';
        browserWindowOptions.autoHideMenuBar = true;
    }

    // Create window
    mainWindow = new BrowserWindow(browserWindowOptions);

    // Setup IPC bridge
    receiver(mainWindow);

    const url =
        // process.env.NODE_ENV === "production"
        isProd
            ? // in production, use the statically build version of our application
            `file://${join(__dirname, 'public', 'index.html')}`
            : // in dev, target the host and port of the local rollup web server
            'http://localhost:5000';

    mainWindow.loadURL(url).catch((err) => {
        logger.error(JSON.stringify(err));
        app.quit();
    });

    if (!isProd) mainWindow.webContents.openDevTools();

    mainWindow.maximize();
    await mainWindow.show();

    mainWindow.on('closed', () => {
        mainWindow = null;
    });
};

app.on('ready', createWindow);

// those two events are completely optional to subscrbe to, but that's a common way to get the
// user experience people expect to have on macOS: do not quit the application directly
// after the user close the last window, instead wait for Command + Q (or equivalent).
app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit();
});

app.on('activate', async () => {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) {
        await createWindow();
    }
});

if (isProd)
    autoUpdater.checkForUpdates().catch((err) => {
        logger.error(JSON.stringify(err));
    });

autoUpdater.logger = logger;

autoUpdater.on('update-available', async () => {
    notification = new Notification({
        title: CONFIGURATION.app.name,
        body: language(store.get('settings.language')).appUpdates
            .updateAvailable.text,
        silent: true,
        // icon: nativeImage.createFromPath(join(__dirname, "..", "assets", "icon.png"),
    });
    notification.show();
    notification.on('click', () => {
        autoUpdater.downloadUpdate().catch((err) => {
            logger.error(JSON.stringify(err));
        });
    });
});

autoUpdater.on('update-not-available', () => {
    notification = new Notification({
        title: CONFIGURATION.app.name,
        body: language(store.get('settings.language')).appUpdates
            .updateUpToDate.text,
        silent: true,
        // icon: nativeImage.createFromPath(join(__dirname, "..", "assets", "icon.png"),
    });
    notification.show();
});

autoUpdater.on('update-downloaded', () => {
    notification = new Notification({
        title: CONFIGURATION.app.name,
        body: language(store.get('settings.language')).appUpdates
            .updateReady.text,
        silent: true,
        // icon: nativeImage.createFromPath(join(__dirname, "..", "assets", "icon.png"),
    });
    notification.show();
    notification.on('click', () => {
        autoUpdater.quitAndInstall();
    });
});

autoUpdater.on('error', (err) => {
    notification = new Notification({
        title: CONFIGURATION.app.name,
        body: JSON.stringify(err),
        // icon: nativeImage.createFromPath(join(__dirname, "..", "assets", "icon.png"),
    });
    notification.show();
});
