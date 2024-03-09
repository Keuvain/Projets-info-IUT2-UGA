#include <iostream>
#include "BilletReduit.h"
using namespace std;

BilletReduit::BilletReduit(const Trajet& unTrajet, const Tarif& unTarif, const Promotion& unePromo)
: Billet(unTrajet, unTarif), m_promotion(unePromo) {

}

const Promotion & BilletReduit::getPromotion() const {
    return this->m_promotion;
}

float BilletReduit::getPrix() const {
    return m_promotion.calculePrixReduit(Billet::getPrix());
}

void BilletReduit::afficher(std::ostream & sortie) const {
    Billet::afficher(sortie); // on appelle le afficher de la classe mère
    sortie << " - Promo  : " << getPromotion() << " €" << endl;
}
