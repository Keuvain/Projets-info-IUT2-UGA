#ifndef FICHIER_H
#define FICHIER_H

#include "Element.h"
#include <string>

class Fichier : public Element {
public:
    Fichier(const std::string & nom, unsigned int taille, const std::string & dateModification, const Usager & proprietaire);
    unsigned int getTaille() const override;
    const std::string & getDateModification() const override;
    std::string rechercher(const std::string & nom) const override;
    virtual ~Fichier() {}
private:
    unsigned int m_taille;
    std::string m_dateModification;
};

#endif /* FICHIER_H */

