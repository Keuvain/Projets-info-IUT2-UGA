#include "Visage.h"
#include <iostream>
using namespace std;

Visage::Visage(int longueurNez, const Ethnie & uneEthnie)
: m_monNez(longueurNez), m_monEthnie(uneEthnie),
  m_maMoustache(nullptr), m_monChapeau(nullptr),
  m_mesBoutons(), m_mesBijoux() // appels const. par défaut facultatifs
{
}

void Visage::setMoustache(int largeur) {
    if (m_maMoustache != nullptr) {
        delete m_maMoustache;
    }
    m_maMoustache = new Moustache(largeur);
}

void Visage::addBouton(int diametre) {
    m_mesBoutons.push_back(Bouton(diametre));
}

void Visage::setChapeau(const Chapeau & unChapeau) {
    m_monChapeau = &unChapeau;
}

void Visage::addBijou(const Bijou & unBijou) {
    m_mesBijoux.push_back(& unBijou);
}

const Visage & Visage::operator=(const Visage & unVisage) {
    m_monNez = unVisage.m_monNez;
    // On ne peut pas changer l'ethnie qui est une référence
    // => l'opérateur = ne peut pas changer l'objet référencé par un attribut de type référence
    // Si this avait une moustache, on la supprime
    if (m_maMoustache != nullptr) delete m_maMoustache;
    // On clone la moustache de unVisage s'il en a une
    if (unVisage.m_maMoustache != nullptr) {
        m_maMoustache = new Moustache(*(unVisage.m_maMoustache));
    } else {
        m_maMoustache = nullptr;
    }
    m_monChapeau = unVisage.m_monChapeau; // le chapeau est partagé
    m_mesBoutons = unVisage.m_mesBoutons; // les boutons seront clonés  (vecteur d'objets)
    m_mesBijoux  = unVisage.m_mesBijoux;  // les bijoux seront partagés (vecteur de pointeurs)
    return *this;
}

Visage::Visage(const Visage & unVisage)
: m_monNez(unVisage.m_monNez), m_monEthnie(unVisage.m_monEthnie) {
    if (unVisage.m_maMoustache != nullptr) {
        m_maMoustache = new Moustache(*(unVisage.m_maMoustache));
    } else {
        m_maMoustache = nullptr;
    }
    m_monChapeau = unVisage.m_monChapeau; // le chapeau est partagé
    m_mesBoutons = unVisage.m_mesBoutons; // les boutons seront clonés  (vecteur d'objets)
    m_mesBijoux  = unVisage.m_mesBijoux;  // les bijoux seront partagés (vecteur de pointeurs)
}

Visage::~Visage() { // on libère les objets qu'on a alloués dans la classe (composition)
    if (m_maMoustache != nullptr) delete m_maMoustache;
}

std::ostream & operator<<(std::ostream & sortie, const Visage & visage) {
    sortie << "Attributs du Visage : " << endl;
    sortie << "- " << visage.m_monEthnie << endl;
    sortie << "- " << visage.m_monNez << endl;
    if (visage.m_maMoustache == nullptr)
        sortie << "- pas de moustache " << endl;
    else
        sortie << "- " << *visage.m_maMoustache << endl;
    if (visage.m_monChapeau == nullptr)
        sortie << "- pas de chapeau " << endl;
    else
        sortie << "- " << *visage.m_monChapeau << endl;
    if (visage.m_mesBoutons.empty())
        sortie << "- pas de boutons" << endl;
    else {
        for(const Bouton & unBouton : visage.m_mesBoutons) // const Bouton & car visage est const !
            sortie << "- " << unBouton << endl;
    }
    if (visage.m_mesBijoux.empty())
        sortie << "- pas de bijoux" << endl;
    else {
        for(const Bijou * unBijou : visage.m_mesBijoux)
            sortie << "- " << *unBijou << endl;
    }
    return sortie;
}