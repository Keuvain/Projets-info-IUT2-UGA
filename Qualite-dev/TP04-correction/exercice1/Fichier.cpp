#include "Fichier.h"
#include <string>
using namespace std;

Fichier::Fichier(const string & nom, unsigned int taille, const string & dateModification, const Usager & proprietaire)
: Element(nom, proprietaire), m_taille(taille), m_dateModification(dateModification) {
}

unsigned int Fichier::getTaille() const {
    return m_taille;
}

const string & Fichier::getDateModification() const {
    return m_dateModification;
}

std::string Fichier::rechercher(const std::string & nom) const {
    // si le fichier est l'élément recherché on renvoie son nom, sinon chaine vide
    return (this->getNom()==nom) ? this->getNom() : "";
}
