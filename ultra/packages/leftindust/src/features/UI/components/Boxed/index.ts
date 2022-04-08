export type BoxedDimensions = {
  width: number;
  height: number;
  fontSize: number;
};

export const BoxedSizes: Record<string, BoxedDimensions> = {
  Smallest: {
    width: 17,
    height: 17,
    fontSize: 14,
  },
  Small: {
    width: 30,
    height: 30,
    fontSize: 17,
  },
  Medium: {
    width: 44,
    height: 44,
    fontSize: 18,
  },
  Large: {
    width: 55,
    height: 55,
    fontSize: 24,
  },
  Largest: {
    width: 90,
    height: 90,
    fontSize: 40,
  },
};