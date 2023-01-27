export function getDisplayOutput(rawOutput: number) {
  if (Math.abs(rawOutput) < 1e-15) return 0;

  if (rawOutput.toString().length > 5) {
    return (Math.abs(rawOutput) < 0.1) ? rawOutput.toExponential(3) : rawOutput.toPrecision(4);
  }

  return rawOutput;
}