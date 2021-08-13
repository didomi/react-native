/**
 * Convert a result from native from any type into a string that can be presented to the user.
 * @param result result to be converted.
 * @returns formatted string.
 */
const convertResultToString = (result: any) => {
  if (Array.isArray(result) && typeof result[0] == "string") {
    return result.sort().toString();
  }
  if (Array.isArray(result) && typeof result[0] == "object") {
    return result.map(entity => entity.id).sort().toString();
  }
  return JSON.stringify(result) || result
};

export { convertResultToString };