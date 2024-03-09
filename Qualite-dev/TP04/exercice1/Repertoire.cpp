#include "Repertoire.h"
#include <string>
#include <iostream>
using namespace std;

// Constantes de classe
const string Repertoire::DATE_DEFAUT = "1970-01-01";
const unsigned int Repertoire::TAILLE_DOSSIER = 4;

Repertoire::Repertoire(const string & nom, const Usager & proprietaire)
: Element(nom, proprietaire), m_contenu() // m_contenu() est facultatif : sera fait par défaut
{ 
}

unsigned int Repertoire::getTaille() const {
    unsigned int taille = TAILLE_DOSSIER;
    for (Element* ptrElement : m_contenu)
        taille += ptrElement->getTaille();
    return taille;
}

const string & Repertoire::getDateModification() const {
    // Cette méthode recherche la date maximum dans le contenu du répertoire
    // Pour dateMax, on ne peut utiliser une variable référence (&) car on va devoir modifier cette variable
    // Il faut donc utiliser un pointeur (*)
    const string * dateMax = & DATE_DEFAUT;

    // recherche d'un maximum dans un vecteur
    for (Element * ptrElement : m_contenu) {
        const string & dateElement = ptrElement->getDateModification();
        if (dateElement > *dateMax) dateMax = &dateElement;
    }
    return *dateMax; // Il faut déréférencer le pointeur !
}

void Repertoire::ajouter(Element* element) {
    try {
        m_contenu.push_back(element);
    } catch (bad_alloc exception) { // Exception levée si plus de place dans le tas
        // on affiche une trace sur le flux d'erreur standard, cerr
        cerr << "Repertoire::ajouter : échec ajout, plus de mémoire disponible dans le tas";
        // on propage l'exception
        throw;
    }
}

std::string Repertoire::rechercher(const std::string & nom) const {
    if (this->getNom() == nom) { // Si le répertoire est l'élément recherché
        return this->getNom() + "/"; //  on retourne son nom suivi de '/'
    } else { // Sinon on cherche la 1ère occurence dans le contenu du répertoire
        string chemin = "";
        for (int i = 0; i < this->m_contenu.size() && chemin == ""; i++) {
            chemin = this->m_contenu[i]->rechercher(nom);
        }
        // Si on a trouvé dans le contenu, on ajoute le nom du répertoire en préfixe au chemin
        return chemin == "" ? "" : (this->getNom() + "/" + chemin);
    }
}