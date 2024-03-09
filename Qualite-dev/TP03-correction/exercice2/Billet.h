#ifndef BILLET_H
#define BILLET_H

#include <iostream>
#include <iomanip>

#include "Trajet.h"
#include "Tarif.h"

class Billet {
public:
    Billet(const Trajet& unTrajet, const Tarif& unTarif);
    const Trajet& getTrajet() const;
    const Tarif& getTarif() const;
    virtual float getPrix() const;
    virtual void afficher(std::ostream & sortie) const;
    // sortie
    friend std::ostream & operator << (std::ostream & sortie, const Billet & billet);
private:
    const Trajet & m_trajet;  // agrégation 1..1
    const Tarif  & m_tarif;   // agrégation 1..1
};
#endif