#include "Point.h"
#include <iostream>
#include <string>
using namespace std;

// On donne ici leur valeur aux constantes de classe
const std::string Point::NOM_DEF = "ORIGINE";
const int         Point::X_DEF   = 0;
const int         Point::Y_DEF   = 0;

Point::Point(const std::string & nom, int x, int y) // on ne rappelle pas les valeurs par défaut dans le .cpp
: m_nom(nom), m_x(x), m_y(y) {                      // on initialise les attributs ici, dans la LISTE D'INITIALISATION
    cout << "Le point \"" 
         << this->getNom()
         << "\" a ete instancie."
         << endl;
}

Point::~Point() {
    cout << "Le point \"" << this->getNom() << "\" a ete libere." << endl;
}

const std::string & Point::getNom() const {
    return this->m_nom;
}

int Point::getX() const {
    return this->m_x;
}

int Point::getY() const {
    return this->m_y;
}

void Point::setNom(const std::string & nom) {
    this->m_nom = nom;
}

void Point::setX(int x) {
    this->m_x = x;
}

void Point::setY(int y) {
    this->m_y = y;
}

void Point::saisir(std::istream & entree) {
    string nom;
    int x;
    int y;
    getline(entree, nom); // lit toute la ligne jusqu'au prochain retour chariot ; cin >> nom n'aurait lu que le premier mot
    entree >> x;
    entree >> y;
    this->setNom(nom); // On utilise les setters, s'ils existent, pour modifier les attributs
    this->setX(x);
    this->setY(y);
}

void Point::afficher(std::ostream & sortie) const {
    cout << this->getNom() << "("  // on utilise les getters, s'ils existent, pour consulter les attributs
         << this->getX()   << ","
         << this->getY()   << ")" 
         << endl;
}
