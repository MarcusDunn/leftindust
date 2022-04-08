import type { Framework7Icon } from '../..';

export enum AppbarPositions {
  Left = 'left',
  Center = 'center',
  Right = 'right',
}

export type AppbarClose = boolean | {
  popover?: boolean;
  popup?: boolean;
}

export type AppbarButton = {
  icon: Framework7Icon;
  title: string;
  onClick?: () => void;
  condense?: boolean;
};

export type AppbarTitle = {
  title: string;
  position?: AppbarPositions;
};