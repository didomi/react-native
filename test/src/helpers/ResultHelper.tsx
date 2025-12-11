/**
 * Convert a result from native from any type into a string that can be presented to the user.
 * @param result result to be converted.
 * @returns formatted string.
 */
const convertResultToString = (result: any): string => {
  if (Array.isArray(result) && result.length > 0 && typeof result[0] === "string") {
    // Create a copy before sorting to avoid mutating the original array
    return [...result].sort().toString();
  }
  if (Array.isArray(result) && result.length > 0 && typeof result[0] === "object") {
    // Map and sort without mutating the original array
    return result.map(entity => entity.id).sort().toString();
  }
  return JSON.stringify(result) ?? String(result);
};

export { convertResultToString };
