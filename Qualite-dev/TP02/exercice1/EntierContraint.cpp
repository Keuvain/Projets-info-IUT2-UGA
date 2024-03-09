#include "EntierContraint.h"
#include <iostream>
using namespace std;

EntierContraint::EntierContraint(int valeur, int min, int max)
: m_min(min), m_max(max) {
    this->setVal(valeur); // Il faut utiliser setVal pour vérifier que m_min<=valeur<=m_max
}

void EntierContraint::setVal(int valeur) {
    if (valeur < getMin() || valeur > getMax()) // exception levée aussi si m_min>m_max
        throw ("Valeur entier contraint hors intervalle");
    this->m_val = valeur;
}

int EntierContraint::getMin() const {
    return this->m_min;
}

int EntierContraint::getMax() const {
    return this->m_max;
}

int EntierContraint::getVal() const {
    return this->m_val;
}

void EntierContraint::saisir(istream& entree) {
    int val;
    entree >> val;
    entree.ignore(256, '\n'); // pour "consommer" le retour chariot tapé après l'entier
    this->setVal(val); // Il faut utiliser setVal pour vérifier que m_min<=valeur<=m_max
}

void EntierContraint::afficher(ostream& sortie) const {
    sortie << this->getVal();
}

EntierContraint::operator int() const {
    return this->m_val;
}

std::ostream & operator<<(std::ostream & sortie, const EntierContraint & ec) {
    ec.afficher(sortie);
    return sortie; // On renvoie le flux en résultat pour permettre l'enchaînement d'opérateurs << 
}

std::istream & operator>>(std::istream & entree, EntierContraint & ec) {
    ec.saisir(entree);
    return entree; // On renvoie le flux en résultat pour permettre l'enchaînement d'opérateurs >> 
}
