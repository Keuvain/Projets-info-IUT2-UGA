#ifndef POINT_H
#define POINT_H

#include <iostream>
#include <string>
// Pas de using namespace dans un fichier .h !
class Point {
public: 
    // Dans Point, nom doit être passé en param. par réf. (&) constante
    Point(const std::string & nom = NOM_DEF, int x = X_DEF, int y = Y_DEF);
    ~Point();
    // getNom doit renvoyer en résultat une string par réf. (&) constante
    const std::string & getNom() const;   // les getters sont "const"
    int getX() const;
    int getY() const;
    // Dans setNom, nom doit être passé en param. par réf. (&) constante
    void setNom(const std::string & nom); // les setters ne sont pas "const"
    void setX(int x);
    void setY(int y);
    void saisir(std::istream & entree = std::cin); // saisir n'est pas const
    void afficher(std::ostream & sortie = std::cout) const; // afficher est const

private: 
    // Attributs d'instance
    std::string m_nom; // On n'initialise pas les attributs ici mais dans le constructeur !
    int m_x;
    int m_y;
    // Constantes de classe
    static const std::string NOM_DEF; // On donnera la valeur des constantes de classe dans le .cpp
    static const int X_DEF;
    static const int Y_DEF;
};

#endif /* POINT_H */

/*
 * Notions de base
 */

/*
 * Généricité : templates, STL
 */

/*
 * Composition, Agrégation, Héritage, Polymorphisme
 */

/*
 * Design Patterns: Composite, Singleton
 */

/*
 * Exceptions
 */

/*
 * Petite Introduction aux Tests Unitaires, Google Test
 */


