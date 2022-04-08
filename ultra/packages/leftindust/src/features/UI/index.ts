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
