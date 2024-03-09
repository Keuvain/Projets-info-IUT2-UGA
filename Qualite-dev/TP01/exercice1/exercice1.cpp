#include <cstdlib>
#include <iostream>
#include <string>
#include "Point.h"
using namespace std;

void testClassePoint(const string &nom, int x, int y) {
    Point p(nom, x, y); // allocation automatique
    // tester getNom()
    cout << "--- Test de getNom()"                  << endl
         << "Valeur attendue : " << nom             << endl
         << "Valeur obtenue  : " << p.getNom()      << endl
         << (p.getNom()==nom ? "Succes" : "Echec")  << endl;
    // tester getX()
    cout << "--- Test de getX()"                    << endl
         << "Valeur attendue : " << x               << endl
         << "Valeur obtenue  : " << p.getX()        << endl
         << (p.getX()==x ? "Succes" : "Echec")      << endl;
    // tester getY()
    cout << "--- Test de getNom()"                  << endl
         << "Valeur attendue : " << y               << endl
         << "Valeur obtenue  : " << p.getY()        << endl
         << (p.getY()==y ? "Succes" : "Echec")      << endl;
    // tester setX()
    int newX = 30;
    p.setX(newX);
    cout << "--- Test de setX()"                    << endl
         << "Valeur affectee a x : " << newX        << endl
         << "Valeur apres setX   : " << p.getX()    << endl
         << (p.getX()==newX ? "Succes" : "Echec")   << endl;
    // etc... 
    // Pour tester saisir et afficher "automatiquement" (sans intervention de l'utilisateur,
    // il faut saisir/afficher avec des fichiers (fstream)
    // Un étudiant l'a fait mais ce n'est pas demandé bien sûr !
    // Juste réfléchier à la manière de tester des E/S (cf Tests Unitaires, vus plus tard)
}

void testClassePointDynamique(const string &nom, int x, int y) {
    Point* ptrP = new Point(nom, x, y); // allocation dynamique
    // tester getNom()
    cout << "--- Test de getNom()"                     << endl
         << "Valeur attendue : " << nom                << endl
         << "Valeur obtenue  : " << ptrP->getNom()     << endl
         << (ptrP->getNom()==nom ? "Succes" : "Echec") << endl;
    // tester getNom()
    cout << "--- Test de getX()"                       << endl
         << "Valeur attendue : " << x                  << endl
         << "Valeur obtenue  : " << ptrP->getX()       << endl
         << (ptrP->getX()==x ? "Succes" : "Echec")     << endl;
    // tester getNom()
    cout << "--- Test de getNom()"                     << endl
         << "Valeur attendue : " << y                  << endl
         << "Valeur obtenue  : " << ptrP->getY()       << endl
         << (ptrP->getY()==y ? "Succes" : "Echec")     << endl;
    // tester setX()
    int newX = 30;
    ptrP->setX(newX);
    cout << "--- Test de setX()"                        << endl
         << "Valeur affectee a x : " << newX            << endl
         << "Valeur apres setX   : " << ptrP->getX()    << endl
         << (ptrP->getX()==newX ? "Succes" : "Echec")   << endl;
    // etc... 
    delete ptrP; // si on l'oublie, pas de message de suppression de P2 => memory leak
}

int main(int argc, char** argv) {
    // On teste la classe Point par allocation automatique
    testClassePoint("P1", 10, 15);
    // On teste la classe Point par allocation dynamique
    testClassePointDynamique("P2", 10, 15);
    
    return EXIT_SUCCESS;   
}

