type ArrayLengthMutationKeys =
  | 'splice'
  | 'push'
  | 'pop'
  | 'shift'
  | 'unshift'
  | number;

type ArrayItems<T extends Array<any>> = T extends Array<infer TItems>
  ? TItems
  : never;

export type Mutable<T> = { -readonly [P in keyof T]: T[P] };

export type Partial<T> = {
  [P in keyof T]?: T[P] extends Record<string, unknown>
  ? Partial<T[P]>
  : T[P];
};

export type RecursivePartial<T> = {
  [P in keyof T]?:
    T[P] extends (infer U)[] ? RecursivePartial<U>[] :
    T[P] extends object ? RecursivePartial<T[P]> :
    T[P];
};

export type Action<T extends any[], R> = (...args: T) => R;

export interface DOMEvent<T extends EventTarget> extends Event {
  readonly target: T;
}

export type FixedLengthArray<T extends any[]> = Pick<
  T,
  Exclude<keyof T, ArrayLengthMutationKeys>
> & {
  [Symbol.iterator]: () => IterableIterator<ArrayItems<T>>;
};

export type Coordinates = {
  x: number;
  y: number;
};

export type Dimensions = {
  w: number;
  h: number;
};

export type RequireAtLeastOne<T> = { [K in keyof T]-?: Required<Pick<T, K>> & Partial<Pick<T, Exclude<keyof T, K>>>; }[keyof T]