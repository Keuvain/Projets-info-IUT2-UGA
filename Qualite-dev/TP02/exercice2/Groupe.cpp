#include "Groupe.h"
#include "Personne.h"
#include <string>
#include <iostream>
using namespace std;

Groupe::Groupe(const std::string & intitule)
: m_intitule(intitule) {
}
///////////////////////////////////////////////////
void Groupe::setIntitule(const std::string & intitule) {
  this->m_intitule = intitule;
}
///////////////////////////////////////////////////
void Groupe::addPersonne(const std::string & nom) {
  this->m_effectif.push_back(new Personne(nom));
}
///////////////////////////////////////////////////
void Groupe::setNomPersonne(unsigned int i, 
                            const std::string nom){
  if (i<this->m_effectif.size()) 
    this->m_effectif[i]->setNom(nom);
}
///////////////////////////////////////////////////
void Groupe::affiche() const {
  cout << "Groupe " << this->m_intitule << " = { ";
  for (Personne* personne : this->m_effectif) 
    cout << personne->getNom() << " ";
  cout << "}" << endl;
}
////// A RAJOUTER /////////////////////////////////
void Groupe::emptyGroupe() {
    // On libère chaque Personne du groupe
    for (Personne* personne : this->m_effectif) delete personne;
    // et on vide le vecteur de pointeurs !!!
    this->m_effectif.clear() ;  
}
///////////////////////////////////////////////////
void Groupe::copyGroupe(const Groupe & unGroupe) {
    // on duplique l'intitule
    this->m_intitule = unGroupe.m_intitule;
    // on duplique chaque Personne
    for (Personne* personne : unGroupe.m_effectif)
        this->addPersonne(personne->getNom());
}
///////////////////////////////////////////////////
Groupe::Groupe(const Groupe & unGroupe) {
    // Dans le constructeur par recopie,
    // on recopie dans le groupe *this, qui n'est pas encore construit,
    // le contenu de unGroupe
    this->copyGroupe(unGroupe);
}
///////////////////////////////////////////////////
Groupe & Groupe::operator=(const Groupe & unGroupe) {
    // Dans l'opérateur = , on commence par "vider" *this qui est un groupe
    // déjà construit et ensuite on recopie dans le *this
    // le contenu de unGroupe    
    this->emptyGroupe();
    this->copyGroupe(unGroupe);
    return *this;
}
///////////////////////////////////////////////////
Groupe::~Groupe() {
    this->emptyGroupe();
}

