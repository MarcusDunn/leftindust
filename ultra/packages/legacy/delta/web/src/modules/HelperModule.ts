export const resolveObjectPath = (object: Record<string | number, unknown>, path: string, defaultValue?: unknown): unknown => path
  .split('.')
  // @ts-expect-error
  .reduce((o, p) => o ? o[p] : defaultValue, object);