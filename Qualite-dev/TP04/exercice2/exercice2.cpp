#include <iostream>
#include "Parametres.h"
using namespace std;

int main() {
    cout << "--- Début du programme" << endl;
    Parametres & parametres = Parametres::getInstance(); // la variable "parametres" référence l'unique instance
    // On affiche les Paramètres initiaux
    cout << "--- Contenu par défaut du singleton Parametres :" << endl;
    parametres.afficher();
    // On change le thème des Paramètres
    parametres.setTheme(Theme("Urbain"));
    // On instancie un joueur nommé "Yoloutre" et on l'associe aux Paramètres
    Joueur unJoueur("Yoloutre");
    cout << "--- Contenu après modification du singleton Parametres :" << endl;
    parametres.setJoueur(unJoueur);
    // On affiche les nouveaux Paramètres
    parametres.afficher();
    
    // Impossible de construire une autre instance par recopie
    // Parametres p2(Parametres::getInstance()); // ne compilera pas : constructeur par recope "deleted"
    
    // Impossible de recopier l'unique instance
    // Parametres p3;                            // ne compilera pas : constructeur par defaut "protected"
    // p3=Parametres::getInstance();             // ne compilera pas : operateur = "deleted"

    cout << "--- Fin du programme" << endl;
    return 0;
}

