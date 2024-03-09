// Chargement de la librairie Chai
const chai = require('chai');
// Choix du style des assertions
const assert = require('chai').assert;

const Money = require("../src/money.js");

describe("Money", function () {
  describe("#add()", function () {
    beforeEach(function(){ 
      m1 = new Money(10.0, "EUR");
    });
    it("should correctly add two moneys with the same currency", function () {
       m2 = new Money(20.0, "EUR");

      m1.add(m2); // On additionne m2 à m1. m1 est modifié

      let newAmount = m1.amount, // On récupère la valeur
        oracle = 30.0; // On souhaite comparer par rapport à la valeur théorique

        assert.equal(newAmount,  oracle,

          `m1 vaut ${newAmount}€ alors qu'il devrait valoir ${oracle}€`);
    });

    it("should correctly add two moneys with different currencies", function () {
       m2 = new Money(20.0, "USD");

      m1.add(m2); // On additionne m2 à m1. m1 est modifié

      let newAmount = m1.amount, // On récupère la valeur
        oracle = 20.0; // On souhaite comparer par rapport à la valeur théorique

      chai.expect(newAmount).to.equal(oracle); // On écrit une assertion
    });

    it("should throw an exception when the currency is neither EUR nor USD", function () {
       m2 = new Money(20.0, "BZR"); // BZR : Réal brésilien

      assert.throws(function () {
        // On capture l'exception
        m1.add(m2);
      });
    });
  });
});
