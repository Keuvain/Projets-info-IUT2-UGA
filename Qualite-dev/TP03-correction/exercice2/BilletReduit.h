#ifndef BILLETREDUIT_H
#define BILLETREDUIT_H

#include "Billet.h"
#include "Promotion.h"

class BilletReduit : public Billet {
public:
    BilletReduit(const Trajet& unTrajet, const Tarif& unTarif, const Promotion& unePromo);
    const Promotion & getPromotion() const;
    float getPrix() const override;
    void afficher(std::ostream & sortie) const override;
private:
    const Promotion& m_promotion; // agrégation 1..1
};
#endif