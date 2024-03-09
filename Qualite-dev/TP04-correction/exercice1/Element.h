#ifndef ELEMENT_H
#define ELEMENT_H

#include <string>
#include <vector>
#include "Usager.h"
////////////////////////////////////////////////////////////////////////////////
class Element {
public:
    Element(const std::string & nom, const Usager & proprietaire);
    virtual const std::string & getNom() const;     // virtual pas indispensable
    virtual const Usager & getProprietaire() const; // virtual pas indispensable
    virtual unsigned int getTaille() const = 0;     // =0 : méthode pure (non implémentée dans cette classe)
    virtual const std::string & getDateModification() const = 0; // méthode pure
    virtual void afficher() const;                  // virtual pas indispensable
    virtual void ajouter(Element * element);        // pourrait être pure, ou pas (implémentation "par défaut")
    virtual std::string rechercher(const std::string & nom) const =0; // méthode pure
    virtual ~Element() {}                           // bonne pratique en situation de polymorphisme: destructeur virtuel
private:
    std::string m_nom;
    const Usager * m_proprietaire; // Agregation 1..1 mais pointeur nécessaire pour pouvoir changer de propriétaire

    // Attention : on ne met pas d'attributs taille et dateModification ici car tous les éléments n'ont pas ces attributs
    // (Dans la classe Repertoire, taille et DateModification sont calculées "dynamiquement")
};

#endif /* ELEMENT_H */

