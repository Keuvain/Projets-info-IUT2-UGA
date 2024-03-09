#ifndef ENTIERCONTRAINT_H
#define ENTIERCONTRAINT_H

#include <iostream>
#include <limits>

class EntierContraint {
public:
    EntierContraint(int valeur = VAL_DEF, int min = MIN_DEF, int max = MAX_DEF);
    int getMin() const;
    int getMax() const;
    int getVal() const;
    void setVal(int valeur);
    void saisir(std::istream & entree = std::cin);
    void afficher(std::ostream & sortie = std::cout) const;
    operator int() const; // Opérateur de cast (convertit *this en int)

private:
    // Attributs d'Instance
    int m_min;
    int m_max;
    int m_val;
    // Constantes de Classe (static)
    static constexpr int VAL_DEF = 0;
    static constexpr int MIN_DEF = std::numeric_limits<int>::min(); // le plus petit int possible
    static constexpr int MAX_DEF = std::numeric_limits<int>::max(); // le plus grand int possible
};

// ATTENTION : ce sont des fonctions (pas des méthodes)
// fonction operator << : pour écrire un EntierContraint sur un flux
std::ostream & operator<<(std::ostream & sortie, const EntierContraint & ec);
// fonction operator >> : pour lire un EntierContraint sur un flux
std::istream & operator>>(std::istream & entree, EntierContraint & ec);

#endif /* ENTIERCONTRAINT_H */

