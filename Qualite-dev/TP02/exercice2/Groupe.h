#ifndef GROUPE_H
#define GROUPE_H

#include <string>
#include <vector>
#include "Personne.h"

class Groupe {
public:
    Groupe(const std::string & intitule = "Un Groupe");
    void setIntitule(const std::string & intitule);
    void addPersonne(const std::string & nom);
    void setNomPersonne(unsigned int i,
            const std::string nom);
    void affiche() const;
    ////// A RAJOUTER /////////////////////////////////////////////////////////////////////////
    Groupe(const Groupe & unGroupe);             // constructeur de recopie
    Groupe & operator=(const Groupe & unGroupe); // operateur =
    ~Groupe();                                   // destructeur
private:
    std::string m_intitule;
    std::vector<Personne*> m_effectif;           // un Groupe est composé de Personnes
    ////// A RAJOUTER /////////////////////////////////////////////////////////////////////////
    void emptyGroupe();                          // libère les objets Personne de *this
    void copyGroupe(const Groupe & unGroupe);    // duplique les Personnes de unGroupe dans *this
};

#endif /* GROUPE_H */

