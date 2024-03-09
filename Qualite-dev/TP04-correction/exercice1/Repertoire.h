#ifndef REPERTOIRE_H
#define REPERTOIRE_H

#include"Element.h"
#include <string>

class Repertoire : public Element {
public:
    Repertoire(const std::string & nom, const Usager & proprietaire);
    unsigned int getTaille() const override;
    const std::string & getDateModification() const override;
    void ajouter(Element * element) override;
    std::string rechercher(const std::string & nom) const override;
    // La méthode rechercher renvoie un résultat de type string (et non cont string &)
    //  car cette méthode va devoir fabriquer une chaine contenant le chemin de l'élement recherché
    virtual ~Repertoire() {} // le destructeur ne fait rien (agrégation demandée => le répertoire ne possède pas son contenu)
private:
    std::vector<Element*> m_contenu; 
    static const unsigned int TAILLE_DOSSIER;
    static const std::string DATE_DEFAUT;
};

#endif /* REPERTOIRE_H */

