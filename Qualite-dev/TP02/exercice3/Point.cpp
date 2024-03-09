#include "Point.h"
#include <iostream>
#include <string>
using namespace std;


// A rajouter pour le TP02 exercice 3 : surcharge de l'opérateur <
bool Point::operator < (const Point & p) const {
    return this->getX() < p.getX() || this->getY() < p.getY();
}

// Constantes de classe
const std::string Point::NOM_DEF = "ORIGINE";
const int         Point::X_DEF   = 0;
const int         Point::Y_DEF   = 0;

Point::Point(const std::string & nom, int x, int y)
: m_nom(nom), m_x(x), m_y(y) {
}

Point::~Point() {
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
    int x,y;
    cout << "Nom : "; getline(entree, nom);
    cout << "X : "; entree >> x; entree.ignore(256,'\n');
    cout << "Y : "; entree >> y; entree.ignore(256,'\n');
    this->setNom(nom);
    this->setX(x);
    this->setY(y);
}

void Point::afficher(std::ostream & sortie) const {
    cout << this->getNom() << "("
         << this->getX() << ","
         << this->getY() << ")" 
         << endl;
}

