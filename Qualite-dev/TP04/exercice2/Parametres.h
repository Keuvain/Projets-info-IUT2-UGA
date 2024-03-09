#ifndef PARAMETRES_H
#define PARAMETRES_H
#include <string>
#include "Theme.h"
#include "Joueur.h"

// On va réaliser une autre implémentation du Pattern Singleton que celle vue en cours
// L'unique instance est ici allouée par allocation statique
// => l'unique instance est déclarée comme variable locale statique dans la méthode getInstance()
//    (cf l'implémentation de getInstance)
// Il n'y a donc pas besoin d'un attribut de classe uniqueInstance dans la spécification de la classe
// Avantage     : l'unique instance sera proprement détruite à la fin du programme, avec appel du destructeur
// Inconvénient : l'unique instance sera allouée même si on n'appelle jamais getInstance

class Parametres {
public:
    const Theme & getTheme() const;
    void setTheme(const Theme & theme);
    const Joueur * getJoueur() const;
    void setJoueur(const Joueur & joueur);
    void afficher() const;
    static Parametres & getInstance();             // Accèder à l'unique instance
    Parametres(Parametres const &) = delete;       // =delete => supprime le constructeur de recopie par défaut
    void operator=(Parametres const &) = delete;   // =delete => supprime l'opérateur = par défaut
    virtual ~Parametres();
protected:
    Parametres(const std::string & intituleTheme = "Par Défaut"); // constructeur protected ou private
private:
    Theme m_theme;
    const Joueur* m_joueur;
};

#endif /* PARAMETRES_H */
