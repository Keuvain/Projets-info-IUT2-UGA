#include "ObjetContraint.h"
#include "Point.h"
#include <iostream>
using namespace std;

int main(int argc, char** argv) {
    cout << "Test de ObjetContraint<T> avec T=Point" << endl;
    Point p, p1, p2;
    cout << "Entrez le coin bas gauche (P1)" << endl;
    p1.saisir();
    do {
        cout << "Entrez le coin haut droit (P2 >= P1)" << endl;
        p2.saisir();
    } while (p2 < p1);
    do {
        cout << endl << "Entrez un point P (Ctrl-C pour arrêter)" << endl;
        p.saisir(); // on saisit un point p
        try {
            // on essaye de construire un ObjectContraint<Point> oc avec val=p, min=p1, max=p2
            ObjetContraint<Point> oc(p, p1, p2); 
            // si pas d'exception levée, p appartient au rectangle [p1,p]) : p1 <= p <= p2
            cout << endl << p.getNom() <<  " appartient au rectangle [" 
                 << p1.getNom() << "," << p2.getNom() << "]" << endl;
        } catch(domain_error & exception) {
            // si exception levée, p n'est pas dans le rectangle [p1,p2]
            cout << endl << p.getNom() <<  " n'appartient PAS au rectangle [" 
                 << p1.getNom() << "," << p2.getNom() << "]" << endl;
        }
    } while (true); // boucle infinie, pour faire vite ;-)
    return 0;
}

