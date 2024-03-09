#include "Conteneur.h"
#include <iostream>
#include <string>
#include <stdexcept>
using namespace std;

int main(int argc, char** argv) {
    // Instanciation du template Conteneur
    // Le code du template sera compilé ici en remplaçant T par string
    Conteneur<string> conteneur;

    // On instancie dynamiquement les éléments du conteneur (new)
    conteneur.ajouter(new string("un"));
    conteneur.ajouter(new string("deux"));
    conteneur.ajouter(new string("trois"));
    conteneur.ajouter(new string("quatre"));
    conteneur.ajouter(new string("cing"));

    // Test des méthodes afficher et choisir
    bool continuer = true;
    do {

        cout << endl << "Contenu : " << endl;
        conteneur.afficher();
        cout << endl << "Numéro choisi (0 pour finir) : ";
        try {
            string & element = conteneur.choisir();
            cout << endl << "Vous avez choisi : " << element << endl;
        } catch (range_error & exception) {
            // si choisir a levé une exception
            cout << "C'est fini..." << endl;
            continuer = false;
        }

    } while (continuer); // boucle infinie...
    
    // Test COPLIEN  
    Conteneur<string>* autreConteneur = new Conteneur<string>(conteneur); // const. par recopie
    Conteneur<string> troisiemeConteneur = *autreConteneur; // opérateur affectation
    delete autreConteneur; // On supprime le conteneur alloué dynamiquement
    cout << "Contenu du conteneur obtenu par affectation d'un conteneur depuis disparu : " << endl;
    troisiemeConteneur.afficher();
    
    return 0;
    // conteneur et troisiemeConteneur, alloués dynamiquement, vont être libérés
    // et leurs destructeurs appelés juste avant
}