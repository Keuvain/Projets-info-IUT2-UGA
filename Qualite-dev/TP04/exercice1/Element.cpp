#include "Element.h"
#include <string>
#include <iostream>
#include <iomanip>
#include <stdexcept>
using namespace std;

Element::Element(const string & nom, const Usager & proprietaire) 
: m_nom(nom), m_proprietaire(&proprietaire) {
}

const string & Element::getNom() const {
    return m_nom;
}

const Usager & Element::getProprietaire() const {
    return *m_proprietaire;
}

void Element::afficher() const {
    cout << "Nom : " << setw(20) << left << getNom()
         << " - Propriétaire : " << setw(20) << left << getProprietaire().getNom()+"("+getProprietaire().getGroupe()+")"
         << " - Taille : " << setw(5) << right << getTaille() << " Ko"
         << " - Date : " << getDateModification() << endl;
}

void Element::ajouter(Element* element) { 
    // on implémente un comportement "par défaut"
    // On lève une exception standard (https://en.cppreference.com/w/cpp/error/exception)
    throw logic_error("Element::ajouter : Opération Interdite");
}