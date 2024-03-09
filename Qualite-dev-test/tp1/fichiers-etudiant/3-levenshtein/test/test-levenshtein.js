const assert = require('assert');
const levenshtein = require('../src/levenshtein.js');

describe('#levenshtein()', function () {
  it("La distance de levenshtein entre 'niche' et 'chiens' est de 5", function () {
    assert.equal(levenshtein('niche', 'chiens').distance, 5);
  });

  it("La distance de levenshtein entre 'chiens' et 'niche' est de 5", function () {
    assert.equal(levenshtein('chiens', 'niche').distance, 5);
  });

  it("La distance de levenshtein entre '' et 'niche' est de 5", function () {
    assert.equal(levenshtein('', 'niche').distance, 5);
  });

  it("La distance de levenshtein entre 'niche' et '' est de 5", function () {
    assert.equal(levenshtein('niche', '').distance, 5);
  });
});
