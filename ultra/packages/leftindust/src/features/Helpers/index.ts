export const isNonEmptyArrayOfStrings = (value: unknown): value is string[] => {
  return Array.isArray(value) && value.length > 0 && value.every(item => typeof item === 'string');
};

export const isNonEmptyArrayOfNumbers = (value: unknown): value is number[] => {
  return Array.isArray(value) && value.length > 0 && value.every(item => typeof item === 'number');
};