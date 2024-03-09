#include <iostream>
#include <string>
#include <stdexcept>
#include "Conteneur.h"
#include "Trajet.h"
#include "Tarif.h"
#include "Promotion.h"
#include "Billet.h"
#include "BilletReduit.h"
using namespace std;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Billet * newBillet(Conteneur<Trajet> &    trajets,
                   Conteneur<Tarif> &     tarifs,
                   Conteneur<Promotion> & promotions) {
    // "Factory" pour fabriquer dynamiquement un Billet ou un BilletReduit
    
    bool saisieOK;
    cout << endl << "--- Création d'un Billet ---" << endl;
    // ----- Choix du Trajet
    cout << endl << "-- Liste des Trajets" << endl;
    trajets.afficher();
    const Trajet *leTrajet;
    do {
        try {
            cout << "Quel trajet ? ";
            leTrajet = &trajets.choisir();
            saisieOK = true;
        } catch (range_error & exception) {
            cout << "Numéro incorrect, recommencez" << endl;
            saisieOK = false;
        }
    } while (!saisieOK);

    // ----- Choix du tarif
    cout << endl << "-- Liste des Tarifs" << endl;
    tarifs.afficher();
    const Tarif * leTarif;
    do {
        try {
            cout << "Quel tarif ? ";
            leTarif = & tarifs.choisir();
            saisieOK = true;
        } catch (range_error & exception) {
            cout << "Numéro incorrect, recommencez..." << endl;
            saisieOK = false;
        }
    } while (!saisieOK);

    // ---- Choix de la promotion
    cout << endl << "Ajouter une promotion O/[N] ? ";
    string reponse;
    getline(cin, reponse);
    if (reponse == "o" || reponse == "O") { // Si promotion
        cout << endl << "-- Liste des Promotions" << endl;
        promotions.afficher();
        const Promotion * laPromotion;
        do {
            try {
                cout << "Quelle promotion ? ";
                laPromotion = &promotions.choisir();
                saisieOK = true;
            } catch (range_error & exception) {
                cout << "Numéro incorrect, recommencez..." << endl;
                saisieOK = false;
            }
        } while (!saisieOK);
        return new BilletReduit(*leTrajet, *leTarif, *laPromotion);
    } else { 
        return new Billet(*leTrajet, *leTarif);
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char** argv) {
    // Création des conteneurs de Trajets, TArifs, Promotions
    Conteneur<Trajet> trajets;
    trajets.ajouter(new Trajet("Grenoble", "Lyon", 100));
    trajets.ajouter(new Trajet("Grenoble", "Paris", 600));
    trajets.ajouter(new Trajet("Grenoble", "Marseille", 300));
    Conteneur<Tarif> tarifs;
    tarifs.ajouter(new Tarif("Normal", 2.00));
    tarifs.ajouter(new Tarif("Jeune", 1.00));
    tarifs.ajouter(new Tarif("Vermeil", 3.00));
    Conteneur<Promotion> promotions;
    promotions.ajouter(new Promotion("Noël", 0.5));
    promotions.ajouter(new Promotion("Anniversaire", 0.2));

    // ---- Création de Billets
    Conteneur<Billet> billets;
    string reponse;
    do {
        cout << endl << "Ajouter un billet [O]/N ? ";
        getline(cin, reponse);
        if (reponse == "" || reponse == "o" || reponse == "O") {
            billets.ajouter(newBillet(trajets, tarifs, promotions));
        }
    } while (reponse == "" || reponse == "o" || reponse == "O");
    
    cout << endl << "---- Liste des Billets Créés ----" << endl;
    billets.afficher();
    
    return 0;
}

