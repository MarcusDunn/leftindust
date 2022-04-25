export type Color = 'primary' | 'red' | 'green' | 'blue' | 'pink' | 'yellow' | 'orange' | 'purple'
  | 'deeppurple' | 'lightblue' | 'teal' | 'lime' | 'deeporange' | 'gray' | 'white' | 'black';

export type Framework7Icon = {
  f7?: string;
  ios?: string;
  md?: string;
  color?: Color;
};

export type Url = {
  label: string;
  href: string;
};

// TODO: remove this once framework7 fixes this issue
// https://github.com/framework7io/framework7/issues/4007
export const initAutoDarkTheme = () => {
  const append = (isDarkMode: boolean) => {
    if (isDarkMode) {
      document.getElementsByTagName('html')[0].classList.add('dark');
    } else {
      document.getElementsByTagName('html')[0].classList.remove('dark');
    }
  };

  window
    .matchMedia('(prefers-color-scheme: dark)')
    .addEventListener('change', (e) => append(e.matches));

  append(window.matchMedia('(prefers-color-scheme: dark)').matches);
};