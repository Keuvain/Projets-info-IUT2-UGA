#include "Element.h"
#include "Repertoire.h"
#include "Fichier.h"
#include "Usager.h"
#include <iostream>
#include <exception>
using namespace std;

// Système de Fichiers créé :
//
//                       COVID
//                         |
//           +-------------+-------------+   
//           |             |             |
//         STATS      discours.doc   DECISIONS
//           |
//      +----+-----+
//      |          |
//  isere.xls   rhone.xls

int main(int argc, char** argv) {
    // Construction du composite, allocation dynamique
    Usager* manu = new Usager("Manu", "profs");     // Création de l'usager manu du groupe profs
    Usager* jean = new Usager("Jean", "etudiants"); // Création de l'usager jean du groupe etudiants
    Element* covid = new Repertoire("COVID", *manu); // Création répertoire COVID de Manu
    Element* stats = new Repertoire("STATS", *jean); // Création répertoire STATS de Jean
    covid->ajouter(stats); // Ajout répertoire STATS au répertoire COVID
    Element* isere = new Fichier("isere.xls", 5, "2020-10-01", *jean); // Création fichier isere.xls de Jean
    stats->ajouter(isere); // Ajout fichier isere.xls au répertoire STATS
    Element* rhone = new Fichier("rhone.xls", 15, "2020-10-02", *jean); // Création du fichier rhone.xls de Jean
    stats->ajouter(rhone); // Ajout fichier rhone.xls au répertoire STATS    
    Element* discours = new Fichier("discours.doc", 10, "2020-10-14", *manu); // Création fichier discours.doc de Manu
    covid->ajouter(discours); // Ajout fichier discours.doc au répertoire COVID
    Element* decisions = new Repertoire("DECISIONS", *manu); // Création répertoire DECISIONS de Manu
    covid->ajouter(decisions); // Ajout répertoire STATS au répertoire COVID

    // Affichage de tous les éléments du composite
    covid->afficher();     // Affichage dossier COVID
    stats->afficher();     // Affichage dossier STATS
    isere->afficher();     // Affichage fichier isere.xls
    rhone->afficher();     // Affichage fichier rhone.xls
    discours->afficher();  // Affichage fichier discours.doc
    decisions->afficher(); // Affichage dossier DECISIONS
    
    // On vérifie qu'on ne peut pas ajouter un élément à un fichier
    try {
        isere->ajouter(covid);
    }
    catch (exception & exception) {
        // cerr est le flux standard pour afficher les erreurs (par défaut c'est la console, comme cout)
        cerr << "*** Exception : " << exception.what() << endl; 
    }

    // Facultatif - Recherche d'éléments dans le composite
    vector<string> nomsAChercher = {"isere.xls", "COVID", "DECISIONS", "savoie.xls"};
    for (const string & nomElement : nomsAChercher) {
        string chemin = covid->rechercher(nomElement);
        cout << "Recherche de " << nomElement << " dans COVID : "
             << ((chemin != "") ? chemin : "non trouvé") << endl;
    }
    
    // Pour être rigoureux, on "delete" tout ce qu'on a alloué par "new"
    delete manu;  delete jean;
    delete covid; delete stats;    delete isere;
    delete rhone; delete discours; delete decisions; 
    
    return 0;
}

////////////// TRACE ATTENDUE //////////////////////////////////////////////////////////
// Nom : COVID                - Propriétaire : Manu(profs)          - Taille :    42 Ko - Date : 2020-10-14
// Nom : STATS                - Propriétaire : Jean(etudiants)      - Taille :    24 Ko - Date : 2020-10-02
// Nom : isere.xls            - Propriétaire : Jean(etudiants)      - Taille :     5 Ko - Date : 2020-10-01
// Nom : rhone.xls            - Propriétaire : Jean(etudiants)      - Taille :    15 Ko - Date : 2020-10-02
// Nom : discours.doc         - Propriétaire : Manu(profs)          - Taille :    10 Ko - Date : 2020-10-14
// Nom : DECISIONS            - Propriétaire : Manu(profs)          - Taille :     4 Ko - Date : 1970-01-01
// *** Exception : Element::ajouter : Opération Interdite
// Recherche de isere.xls dans COVID : COVID/STATS/isere.xls
// Recherche de COVID dans COVID : COVID/
// Recherche de DECISIONS dans COVID : COVID/DECISIONS/
// Recherche de savoie.xls dans COVID : non trouvé
////////////////////////////////////////////////////////////////////////////////////////