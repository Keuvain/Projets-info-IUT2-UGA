/**
 * Return the factorial of i.
 * @param {number} i - an integer number.
 * @return {number} the factorial of i.
 */

let factorial = function (i) {
  if (i === 0) {
    return 1;
  } else if (i < 0 || !Number.isInteger(i)) {
    throw new Error('Invalid input');
  } else {
    let result = 1;
    for (let n = 2; n <= i; n++) {
      result *= n;
    }
    return result;
  }
};

module.exports = factorial;
