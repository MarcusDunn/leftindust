const BASE10 = 10;
const BASE2 = 2;
export const DEFAULT_LOG_BASE = 10;
/**
 * Computes the log of a number.
 * @param {number} antilog The value to get the log of.
 * @param {number} [base] The base of the log, defaults to 10.
 */
export const log = (antilog: number, base: number = DEFAULT_LOG_BASE): number => {
  switch (base) {
    case BASE10: return Math.log10(antilog);
    case BASE2: return Math.log2(antilog);
    default: return Math.log(antilog) / Math.log(base);
  }
}

export const DEFAULT_ROOT_DEGREE = 2;
/**
 * Computes the nth root of a number.
 * @param {number} radicand The value to get the nth root of.
 * @param {number} [degree] The degree of the root, defaults to square root (2).
 */
export const nRoot = (radicand: number, degree: number = DEFAULT_ROOT_DEGREE): number =>
  degree === 2 ? Math.sqrt(radicand) : Math.pow(radicand, 1 / degree);

export const DEFAULT_DELIMITER = ' ';
/**
 * Joins the given strings together into a single string with the given delimiter separating each string.
 * @param {string} delimiter The delimiter to separate the joined text.
 * @param {...string[]} strings The strings to join.
 */
export const joinStr = (delimiter: string = DEFAULT_DELIMITER, ...strings: string[]): string => strings.join(delimiter);