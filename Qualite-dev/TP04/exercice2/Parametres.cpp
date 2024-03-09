#include "Parametres.h"
#include <iostream>
using namespace std;

Parametres::Parametres(const std::string & intituleTheme)
: m_theme(intituleTheme), m_joueur(nullptr) {
    cout << "Le singleton Parametres est construit" << endl;
}

// On montre ici une autre façon d'implémenter le pattern Singleton
// L'unique instance de la classe est déclarée en variable locale dans getInstance

Parametres & Parametres::getInstance() {
    static Parametres uniqueInstance; // uniqueInstance ne sera construit qu'une fois, au premier appel de getInstance
    // Ici c'est le constructeur par défaut (protected) qui est appelé implicitement
    return uniqueInstance;
}

const Theme & Parametres::getTheme() const {
    return this->m_theme;
}

void Parametres::setTheme(const Theme & theme) {
    this->m_theme = theme;
}

const Joueur * Parametres::getJoueur() const {
    return this->m_joueur;
}

void Parametres::setJoueur(const Joueur & joueur) {
    this->m_joueur = & joueur;
}

void Parametres::afficher() const {
    cout << "Paramètres { Theme: '" << getTheme().getIntitule()
            << "', Joueur: '" << (getJoueur() ? getJoueur()->getPseudo() : "Anonyme")
            << "' }" << endl;
}

Parametres::~Parametres() { // Le destructeur sera appelé lorsque le programme se termine
    cout << "Le singleton Parametres est détruit" << endl;
}