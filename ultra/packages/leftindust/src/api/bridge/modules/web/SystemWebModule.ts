// @ts-expect-error
const platform = (): string => (window.matchMedia('(display-mode: standalone)').matches) || (window.navigator.standalone) || document.referrer.includes('android-app://') ? 'pwa' : 'web';

export default {
  platform,
};
