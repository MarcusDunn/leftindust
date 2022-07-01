import type { SvelteComponent } from 'svelte';
import type { Coordinates, Dimensions } from '@/types';
import type { Data, ResolversTypes } from '@/api/server';

// @ts-expect-error
import gridHelp from 'svelte-grid/build/helper';

// Svelte Grid is not typed
export type SvelteGridConstraints = {
  x: number;
  y: number;
  w: number;
  h: number;
  fixed?: boolean;
  resizable?: boolean;
  draggable?: boolean;
  min?: {
    w: number;
    h: number;
  }
  max?: {
    w: number;
    h: number;
  };
  customDragger?: boolean;
  customResizer?: boolean;
};

export type WidgetGridWidget = {
  id: string;
  component: new (...args: unknown[]) => SvelteComponent;
  // Unique props specific to component
  props?: Record<string, unknown>;
  // Unique constraints specific to component
  constraints?: Partial<SvelteGridConstraints>;
};

export type WidgetGridItem = Record<string, SvelteGridConstraints> & WidgetGridWidget;

export type WidgetGridProps = {
  id: NonNullable<keyof Partial<ResolversTypes>>;
  data: Data | Data[];
} & Record<string, unknown>;

// gridHelp has no type declarations
const { item } = gridHelp as { item: (options: SvelteGridConstraints) => SvelteGridConstraints };

const computeXY = (
  index: number,
  col: number,
  size: { width: number; height: number },
): Coordinates => ({
  x: index < col ? index : index % col,
  y: Math.floor(index / col) * size.height,
});

export const WidgetGridCoordinates = (
  index: number,
  size: { width: number; height: number },
): Record<string, Coordinates> => ({
  1: {
    ...computeXY(index, 1, size),
  },
  2: {
    ...computeXY(index, 2, size),
  },
  3: {
    ...computeXY(index, 3, size),
  },
  4: {
    ...computeXY(index, 4, size),
  },
  5: {
    ...computeXY(index, 5, size),
  },
  6: {
    ...computeXY(index, 6, size),
  },
});

export const WidgetGridGenerate = (
  index: number,
  constraints: Partial<SvelteGridConstraints>,
): Record<string, SvelteGridConstraints> => {
  const coordinates = WidgetGridCoordinates(index, {
    width: constraints.w || 0,
    height: constraints.h || 0,
  });
  return {
    ...Object.keys(coordinates)
      .map((key: string) => ({
        [key]: item({
          x: coordinates[key].x,
          y: coordinates[key].y,
          w: constraints.w || 0,
          h: constraints.h || 0,
          customDragger: true,
          resizable: constraints.resizable ?? false,
          fixed: constraints.fixed ?? false,
        }),
      }))
      .reduce(
        (obj, item) => ({
          ...obj,
          ...item,
        }),
        {},
      ),
  };
};

// Grid cols (relative to container) -> [MAX_WIDTH, NUMBER_OF_ITEMS_TO_DISPLAY]
export const WidgetGridCols = [
  [Math.pow(10, 1000), 6],
  [2200, 5],
  [1800, 4],
  [1200, 3],
  [1000, 2],
  [650, 1],
];

export const WidgetGridSizes: Record<string, Dimensions> = {
  bundle: {
    w: 1,
    h: 5,
  },
  cluster: {
    w: 1,
    h: 1,
  },
  comparable: {
    w: 1,
    h: 1,
  },
  stack: {
    w: 2,
    h: 3,
  },
  card: {
    w: 1,
    h: 2,
  },
};

export const WidgetGridGenerateConstraints = (
  index: number,
  perimeter: { w: number; h: number; },
): Record<string, SvelteGridConstraints> => WidgetGridGenerate(index, perimeter);
