/**
 * Permet la création de monnaie dans certaines devises.
 * @param {number} amount - la valeur de la monnaie
 * @param {string} currency - la devise.
 * @constructor
 */
let Money = function (amount, currency) {
  this.amount = amount | 0.0;
  this.currency = currency || "EUR";
};

/**
 * Additionne une seconde monnaie dans une devise particulière.
 * @param {Money} m2 - la monnaie à additionner.
 * @private
 */
Money.prototype.add = function (m2) {
  if (m2.currency === this.currency) {
    this.amount += m2.amount;
  } else if (m2.currency === "USD") {
    this.amount += m2.amount / 2;
  } else {
    throw "Unknown currency";
  }
};
