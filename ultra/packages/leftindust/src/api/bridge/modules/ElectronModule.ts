import type { IpcRenderer } from 'electron';

export const isElectron = (): boolean => {
  // Renderer process
  if (
    typeof window !== 'undefined' &&
    typeof window.process === 'object' &&
    window.process?.type === 'renderer'
  ) {
    return true;
  }

  // Detect the user agent when the `nodeIntegration` option is set to true
  return (
    typeof navigator === 'object' &&
    navigator.userAgent.indexOf('Electron') >= 0
  );
};

export const ipcRenderer = (): IpcRenderer | undefined => {
  let renderer: IpcRenderer;

  if (typeof window['require'] !== 'undefined') {
    const electron = window['require']('electron');
    renderer = electron.ipcRenderer;
    return renderer;
  }

  return undefined;
};
