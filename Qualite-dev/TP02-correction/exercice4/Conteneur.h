#ifndef CONTENEUR_H
#define CONTENEUR_H

#include <vector>
#include <iostream>
#include <stdexcept>

template <class T>
class Conteneur {
public:
    Conteneur();
    Conteneur(const Conteneur<T> & unConteneur);
    Conteneur<T> & operator=(const Conteneur<T> & unConteneur);
    virtual ~Conteneur();
    void ajouter(T * element);
    void afficher(std::ostream & sortie = std::cout) const;
    T & choisir(std::istream & entree = std::cin);

private:
    std::vector<T*> m_contenu;
    void clear(); // vider le conteneur
    void clone(const Conteneur<T> & unConteneur); // clone unConteneur sur this
};

// Implémentez ici les méthodes du template (pas de fichier .cpp pour un template)

template <class T>
Conteneur<T>::Conteneur()
: m_contenu() {
}

template <class T>
void Conteneur<T>::clone(const Conteneur<T> & unConteneur) {
    // on clone le contenu de unConteneur dans this
    for (T* element : unConteneur.m_contenu) {
        this->m_contenu.push_back(new T(*element));
    }
}

template <class T>
void Conteneur<T>::clear() {
    for (T* element : this->m_contenu) {
        delete element;
    }
    m_contenu.clear();
}

template <class T>
Conteneur<T>::Conteneur(const Conteneur<T> & unConteneur) {
    this->clone(unConteneur);
}

template <class T>
Conteneur<T> & Conteneur<T>::operator=(const Conteneur<T> & unConteneur) {
    this->clear();
    this->clone(unConteneur);
    return *this;
}

template <class T>
Conteneur<T>::~Conteneur() {
    this->clear();
}

template <class T>
void Conteneur<T>::ajouter(T * element) {
    this->m_contenu.push_back(element);
}

template <class T>
void Conteneur<T>::afficher(std::ostream & sortie) const {
    unsigned int num = 1;
    for (T* element : this->m_contenu) {
        sortie << num++ << " - " << *element << std::endl;
    }
}

template <class T>
T & Conteneur<T>::choisir(std::istream & entree) {
    unsigned int i;
    entree >> i;
    entree.ignore(256, '\n'); // pour "consommer" le retour chariot (\n) tapé après l'entier saisi
    if (i < 1 || i > this->m_contenu.size()) {
        throw std::range_error("Element Inexistant"); // l'exception range_error est pertinente pour ce type de situation
    }
    return *(this->m_contenu[i - 1]);
}

#endif /* CONTENEUR_H */

