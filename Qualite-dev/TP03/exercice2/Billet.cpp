#include <iostream>
#include "Billet.h"
using namespace std;

Billet::Billet(const Trajet& unTrajet, const Tarif& unTarif)
: m_trajet(unTrajet), m_tarif(unTarif) {
}

const Trajet& Billet::getTrajet() const {
    return this->m_trajet;
}

const Tarif & Billet::getTarif() const {
    return this->m_tarif;
}

float Billet::getPrix() const {
    return m_trajet.getDistance() * m_tarif.getPrixAuKm();
}

void Billet::afficher(std::ostream & sortie) const {
    sortie << "Billet : "    << endl
           << " - Trajet : " << getTrajet() << endl
           << " - Tarif  : " << getTarif() << endl
           << " - Prix   : " << getPrix() << " €" << endl;
}

std::ostream & operator<<(std::ostream & sortie, const Billet & billet) {
    billet.afficher(sortie);
    return sortie;
}
