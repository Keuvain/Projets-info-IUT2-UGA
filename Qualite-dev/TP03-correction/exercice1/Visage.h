#ifndef VISAGE_H
#define VISAGE_H
#include "AttributsVisage.h"
#include <vector>

class Visage {
public:
  Visage(int longueurNez, const Ethnie & uneEthnie);
  void setMoustache(int largeur);
  void addBouton(int diametre);
  void setChapeau(const Chapeau & unChapeau);
  void addBijou(const Bijou & unBijou);
  const Visage & operator=(const Visage & unVisage);
  Visage(const Visage & unVisage);
  ~Visage();
  
private:
  Nez m_monNez;                          // composition 1..1
  const Ethnie & m_monEthnie;            // agregation 1..1
  Moustache* m_maMoustache;              // composition 0..1
  const Chapeau* m_monChapeau;           // agregation 0..1
  std::vector<Bouton> m_mesBoutons;      // composition 0..*
  std::vector<const Bijou*> m_mesBijoux; // agregation 0..*
  
  // fonction amie : aura accès aux attributs "private" (pour éviter d'écrire les getters)
  // attention ce n'est pas une méthode !
  friend std::ostream & operator << (std::ostream & sortie, const Visage & visage);
};
#endif /* VISAGE_H */

