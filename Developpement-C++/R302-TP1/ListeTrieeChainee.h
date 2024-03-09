#ifndef _LISTE_CHAINEE_TRIEE
#define _LISTE_CHAINEE_TRIEE

#include "Shared/ListeTrieeInterface.h"
#include "Shared/Cellule.h"
#include "Shared/PrecondVioleeExcep.h"

using namespace std;

template<class TypeInfo>
class ListeTrieeChainee : public ListeTrieeInterface<TypeInfo> {
private:
    Cellule<TypeInfo>* ptrTete; // Pointeur sur la première cellule de la liste
    int nbCellules; // nombre courant de cellules dans la liste

    // Returne un pointeur sur une copie de la listeChaineeOriginale
    Cellule<TypeInfo>* copieListe(const Cellule<TypeInfo>* laListeOriginale);

    /**
     * Worker récursif d'insertion de nouvelleInfo dans ptrCetteListe
     * @pre ptrCetteListe est triée avant insertion
     * @post ptrCetteListe est triée après insertion
     * @param ptrCetteListe : pointeur (donnée-référence) sur la "liste" de cellules dans laquelle insérer
     * @param nouvelleInfo : valeur à insérer
     */
    void insereRecWorker(Cellule<TypeInfo>*& ptrCetteListe, const TypeInfo& nouvelleInfo);

    /**
     * worker récursif de suppression de la première occurrence d'une uneInfo
     * @param ptrCetteListe : pointeur (donnée-résultat) sur la "liste" de Cellules
     * @param uneInfo : information à supprimer
     * @return : true si suppression possible (uneInfo présente) ; False sinon
     */
    bool supprimePremOccInfoRecWorker(Cellule<TypeInfo>*& ptrCetteListe, const TypeInfo& uneInfo);

    void afficheCroissantRecWorker(const Cellule<TypeInfo>* ptrCetteListe) const;

    void supprimeTeteWorker(Cellule<TypeInfo> *&ptrCetteListe);

    void videRecWorker(Cellule<TypeInfo> *ptrCetteListe);


public:
    // Constructeur par défaut
    ListeTrieeChainee();
    // Constructeur à partir d'une liste chaînée exixtante
    ListeTrieeChainee(const ListeTrieeChainee<TypeInfo>& uneListe);
    // Destructeur
    virtual ~ListeTrieeChainee();

    /**
     *     /!\ ATTENTION /!\
     * 
     *  VOIR LA DOCUMENTATION DANS LA CLASSE ListeTrieeInterface 
     *
     */
    void insereRec(const TypeInfo& nouvelleInfo);

    bool supprimePremOccInfoIter(const TypeInfo& uneInfo);
    bool supprimePremOccInfoRec(const TypeInfo& uneInfo);
    int getPositIter(const TypeInfo& uneInfo) const;

    void supprimeToutesDuplications();

    // Méthodes déjà founies dans ListeTrieeInterface :
    bool estVide() const;
    int getLongueur() const;
    void videRec();

    // Méthodes pour l'affichage
    void afficheCroissantRec() const;

    // Méthodes ensemblistes
    /**
     * Vérifie que cette liste est un ensemble : elle est croissante et il n'y a pas de duplication
     * @return true si cette liste est un ensemble
     */
    bool estEnsemble() const;

    /**
     * Intersection de cet ensemble avec ensembleB
     * @pre  cette liste triée est un ensemble, ensembleB est un ensemble
     * @post  le résultat est un ensemble
     * @param ensembleB
     * @return une liste chaînée triée qui est un ensemble intersection de cet ensemble et ensembleB
     */
    ListeTrieeChainee<TypeInfo>* insersectionAvec(const ListeTrieeChainee<TypeInfo>* ensembleB) const;

    /**
     * Union de cet ensemble avec ensembleB
     * @pre  cette liste triée est un ensemble, ensembleB est un ensemble
     * @post  le résultat est un ensemble
     * @param ensembleB
     * @return une liste chaînée triée qui est un ensemble union de cet ensemble et ensembleB
     */
    ListeTrieeChainee<TypeInfo>* unionAvec(const ListeTrieeChainee<TypeInfo>* ensembleB) const;

}; // end ListeChaineeTriee

template<class TypeInfo>
ListeTrieeChainee<TypeInfo>::ListeTrieeChainee() : ptrTete(nullptr), nbCellules(0) {
} // end constructeur par défaut (une liste videRec)

template<class TypeInfo>
ListeTrieeChainee<TypeInfo>::ListeTrieeChainee(const ListeTrieeChainee<TypeInfo>& uneListe) {
    ptrTete = copieListe(uneListe.ptrTete);
    nbCellules = uneListe.nbCellules;
} // end constructeur par copie

template<class TypeInfo>
Cellule<TypeInfo>* ListeTrieeChainee<TypeInfo>::copieListe(const Cellule<TypeInfo>* laListeOriginale) {
    // création d'une liste videRec pour y ranger la copie
    Cellule<TypeInfo>* laListeCopiee = nullptr;
    if (laListeOriginale != nullptr) { // La liste originale n'est pas videRec
        // construction de la première Cellule de la copie
        // avec l'information portée par la première Cellule de la laListeOriginale
        laListeCopiee = new Cellule<TypeInfo>(laListeOriginale->getInfo());
        // avec comme suivante le résutlat de la copie de la suite de la laListeOriginale
        laListeCopiee->setSuivante(copieListe(laListeOriginale->getSuivante()));
    } // end if
    // rendre la copie
    return laListeCopiee;
} // end copieListe

template<class TypeInfo>
ListeTrieeChainee<TypeInfo>::~ListeTrieeChainee() {
    videRec();
} // end destructeur

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::insereRec(const TypeInfo& nouvelleInfo) {
    insereRecWorker(ptrTete, nouvelleInfo);
}


template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::supprimeToutesDuplications() {
    //On va parcourir séquentiellement la liste jusqu'à l'avant-dernière Cellule
    // et chaque fois que la Cellule suivante de la Cellule courante porte la
    // même information que la Cellule courante on va supprimer la Cellule suivante
    // On peut remarquer qu'il n'y a pas de traitement particulier pour la première Cellule, car elle est toujours conservée
    // Le traitement de la dernière Cellule se fait lorsque l'ont traite l'avant-dernière Cellule
    Cellule<TypeInfo>* ptrCelluleCourante = ptrTete;
    Cellule<TypeInfo>* ptrCelluleAssupprimer;

    while (ptrCelluleCourante != nullptr && ptrCelluleCourante->getSuivante() != nullptr) {
        // au moins deux Cellules à traiter
        if (ptrCelluleCourante->getInfo() == ptrCelluleCourante->getSuivante()->getInfo()) {
            // la Cellule suivante est à supprimer
            ptrCelluleAssupprimer = ptrCelluleCourante->getSuivante();
            // mettre à jour le suivant de la Cellule courante
            ptrCelluleCourante->setSuivante(ptrCelluleCourante->getSuivante()->getSuivante());
            // supprimer la Cellule et décrémenter le nombre de Cellules
            delete ptrCelluleAssupprimer;
            nbCellules--;
            // rester sur place pour l'itération suivante car il y a peut-être encore des valeurs
            // dupliquées de la Cellule courante
        } else {
            // la Cellule suivante est différente de la Cellule courante, alors on avance
            // pour supprimer des duplucations éventuelles de la Cellule suivante
            ptrCelluleCourante = ptrCelluleCourante->getSuivante();
        }
    }
}


template<class TypeInfo>
int ListeTrieeChainee<TypeInfo>::getPositIter(const TypeInfo& uneInfo) const {
    int position = 1; // initialisation
    // pointeur temporaire pour parcourir cette liste sans la détruire
    Cellule<TypeInfo>* ptrTemp = ptrTete;

    // tant que la liste n'est pas videRec et que la valeur courante est strictement inférieure à la valeur à supprimer
    while ((ptrTemp != nullptr) && (uneInfo > ptrTemp->getInfo())) {
        // avancer sur cette liste et dans la position
        ptrTemp = ptrTemp->getSuivante();
        position++;
    } // end while

    // si on n'a pas trouvé (on a la position que la valeur devrait occuper)
    if ((ptrTemp == nullptr) || (uneInfo != ptrTemp->getInfo()))
        position = -position;

    return position;
} // end getPositIter

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::afficheCroissantRec() const {
    afficheCroissantRecWorker(ptrTete);
} // end afficheCroissantRec

template<class TypeInfo>
bool ListeTrieeChainee<TypeInfo>::estVide() const {
    return nbCellules == 0;
} // end estVide

template<class TypeInfo>
int ListeTrieeChainee<TypeInfo>::getLongueur() const {
    return nbCellules;
} // end getLongueur

/*
 * Insertion toujours possible (à moins qu'il n'y ait plus de place dans le tas)
 */
template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::insereRecWorker(Cellule<TypeInfo>*& ptrCetteListe, const TypeInfo& nouvelleInfo) {
    // > ptrCetteListe == nullptr => insertête; màj nb Cellules; *
    // > ptrCetteListe != nullptr
    //    >> ptrCetteListe->getInfo() >= nouvelleInfo => insertête; màj nb Cellules; *
    //    >> ptrCetteListe->getInfo() >= nouvelleInfo => insereRecWorker(ptrCetteListe->getRefSuivante(), nouvelleInfo)

    if (!ptrCetteListe || (ptrCetteListe->getInfo() >= nouvelleInfo)) {
        // nouvelle cellule ; insertion en tête; màj nb Cellules

        // pointeur sur la nouvelle Cellule à insérer (création, construction)
        Cellule<TypeInfo>* ptrNouvelleCellule = new Cellule<TypeInfo>(nouvelleInfo);

        // insérer la Cellule en tête
        ptrNouvelleCellule->setSuivante(ptrCetteListe);
        ptrCetteListe = ptrNouvelleCellule;

        // mettre à jour le nombre de Cellules
        nbCellules = nbCellules + 1;

    } else {
        // insérer sur le reste de la "liste" de Cellules
        insereRecWorker(ptrCetteListe->getRefSuivante(), nouvelleInfo);
    }
}

template<class TypeInfo>
bool ListeTrieeChainee<TypeInfo>::supprimePremOccInfoRecWorker(Cellule<TypeInfo>*& ptrCetteListe, const TypeInfo & uneInfo) {
    // > ptrCetteListe == nullptr => return false *
    // > ptrCetteListe != nullptr
    //    >> ptrCetteListe->getInfo() == uneInfo => supTete; màj nb Cellules; return true *
    //    >> ptrCetteListe->getInfo() > uneInfo => return false *
    //    >> ptrCetteListe->getInfo() < uneInfo => supprimeRecWorker(ptrCetteListe->getRefSuivante(), uneInfo)

    if (!ptrCetteListe || (ptrCetteListe->getInfo() > uneInfo)) {
        return false;
    } else if (ptrCetteListe->getInfo() == uneInfo) {
        // suprimer en tête
        Cellule<TypeInfo>* ptrCellASupprimer = ptrCetteListe;
        ptrCetteListe = ptrCellASupprimer->getSuivante();
        delete(ptrCellASupprimer);

        // mettre à jour le nombre de Cellules
        nbCellules = nbCellules - 1;

        // rendre vrai
        return true;
    } else {
        // supprimer sur le reste de la "liste" de Cellules"
        return supprimePremOccInfoRecWorker(ptrCetteListe->getRefSuivante(), uneInfo);
    }

}

template<class TypeInfo>
bool ListeTrieeChainee<TypeInfo>::supprimePremOccInfoRec(const TypeInfo & uneInfo) {
    return supprimePremOccInfoRecWorker(ptrTete, uneInfo);
}

template<class TypeInfo>
bool ListeTrieeChainee<TypeInfo>::supprimePremOccInfoIter(const TypeInfo& uneInfo) {
    // la liste est vide ou le premier est plus grand que uneInfo
    if (ptrTete == nullptr) {
        return false;
    } else if (ptrTete->getInfo() == uneInfo) {
        // supprimer la première Cellule
        Cellule<TypeInfo>* ptrCellASupprimer = ptrTete;
        ptrTete = ptrTete->getSuivante();
        delete ptrCellASupprimer;
        nbCellules = nbCellules -1;
        return true;
    } else {
        // il ne faut pas supprimer la première Cellule
        // il faut un pointeur sur la Cellule précédant la Cellule à supprimer
        Cellule<TypeInfo>* ptrCellPred = ptrTete;
        //l'invariant d'itération est : les valeurs rencontrées sont strictement inférieures à la valeur cible (uneInfo)
        while (ptrCellPred != nullptr && ptrCellPred->getSuivante() != nullptr && ptrCellPred->getSuivante()->getInfo() < uneInfo) {
            ptrCellPred = ptrCellPred->getRefSuivante();
        }
        // ptrCellPred == nullptr || ptrCellPred->getSuivante() != nullptr || ptrCellPred->getSuivante()->getInfo() <= uneInfo
        if (ptrCellPred != nullptr && ptrCellPred->getSuivante() != nullptr && ptrCellPred->getSuivante()->getInfo() == uneInfo) {
            // on a trouvé, supprimer
            Cellule<TypeInfo>* ptrCellSuppr = ptrCellPred->getSuivante();
            // mise à jour du suivant de la Cellule précédente ; la Cellule qui suite la Cellule suivante
            ptrCellPred->setSuivante(ptrCellPred->getSuivante()->getSuivante());
            // supprimer la Cellule à supprimer et mettre à jour le nombre de Cellules
            delete ptrCellSuppr;
            nbCellules = nbCellules - 1;
            return true;
        } else {
            return false;
        }
    }
}

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::supprimeTeteWorker(Cellule<TypeInfo> *&ptrCetteListe) {
    //  pointeur temporaire sur la première cellule
    Cellule<TypeInfo> *ptrCelluleASupprimer = ptrCetteListe;
    // si la liste n'est pas videRec
    if (ptrCetteListe) {
        // la cellule suivante de la première cellule devient la nouvelle première cellule
        // MODIFICATION du paramètre résultat
        ptrCetteListe = ptrCetteListe->getSuivante();
        // rendre au tas la cellule à supprimer
        ptrCelluleASupprimer->setSuivante(nullptr);
        delete ptrCelluleASupprimer;
        ptrCelluleASupprimer = nullptr;
        nbCellules--; // une cellule en moins
    }
    // si la liste est déjà videRec, ne rien faire
} // end supprimeTeteWorker

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::videRecWorker(Cellule<TypeInfo> *ptrCetteListe) {
    // > ptrCetteListe == nullptr => rien {la liste est videRec} *
    // > ptrCetteListe != nullptr => supprimeTete(ptrCetteListe); nbcellules--; videRecWorker(ptrCetteListe);

    if (ptrCetteListe != nullptr) {
        // supprimer en tête
        supprimeTeteWorker(ptrCetteListe);
        nbCellules--;
        // vider la liste qui reste
        videRecWorker(ptrCetteListe);
    } // end if
} // end videRec

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::videRec() {
    videRecWorker(ptrTete);
    ptrTete = nullptr;
} // end videRec

template<class TypeInfo>
void ListeTrieeChainee<TypeInfo>::afficheCroissantRecWorker(const Cellule<TypeInfo>* ptrCetteListe) const {
    if (ptrCetteListe) {
        cout << ptrCetteListe->getInfo() << " ";
        afficheCroissantRecWorker(ptrCetteListe->getSuivante());
    }
} // end afficheCroissantRec

template<class TypeInfo>
bool ListeTrieeChainee<TypeInfo>::estEnsemble() const {
    // une liste triée croissante de Cellules videRec est un ensemble,
    // une liste triée croissante de cellules ne contenant qu'une cellule est un ensemble
    // une liste triée croissante contenant plus d'une Cellule est un ensemble si elle respecte l'ordre strict (>)

    if (ptrTete == nullptr || ptrTete->getSuivante() == nullptr) {
        //liste de Cellules vide ou avec un seul élément
        return true;
    } else {
        //liste de Cellules contenant au moins deux Cellules
        Cellule<TypeInfo>* ptrCellCour = ptrTete;
        // il faut pouvoir comparer le contenu d'une Cellule avec le contenu de la suivante
        while (ptrCellCour->getSuivante() != nullptr && ptrCellCour->getInfo() < ptrCellCour->getSuivante()->getInfo()) {
            ptrCellCour = ptrCellCour->getSuivante();
        }
        return ptrCellCour->getSuivante() == nullptr;
    }
}

template<class TypeInfo>
ListeTrieeChainee<TypeInfo>* ListeTrieeChainee<TypeInfo>::insersectionAvec(const ListeTrieeChainee<TypeInfo>* ensembleB) const {
    // l'idée est de mettre, si possible une première Cellule dans EnsembleResultat
    // puis on insérera successivment derière la dernière Cellule de EnsembleResultat

    // intitialisation de l'ensemble résultat, il ne contient aucune Cellule son ptrTete == nullptr
    ListeTrieeChainee<TypeInfo>* ptrEnsembleResultat = new ListeTrieeChainee<TypeInfo>();

    //pointeur sur la première Cellule de chacun des deux ensembles
    Cellule<TypeInfo>* ptrListeEnsembleA = this->ptrTete;
    Cellule<TypeInfo>* ptrListeEnsembleB = ensembleB->ptrTete;


    //on met une première Cellule dans EnsembleResultat car ensuite on modifiera le suvant de la dernière Cellule
    //trouver le premier à mettre dans l'intersection (c'est une valeur commune de EnsembleA et EnsembleB)
    // Hypothèse : EnsembleResultat est videRec (aucune Cellule), aucun élément commun entre les parties examinées de EnsembleA et EnsembleB
    // > ptrListeEnsembleA == nullptr -> * (EnsembleResultat ne contient aucune Cellule)
    // > ptrListeEnsembleA != nullptr ->
    //      >> ptrListeEnsembleB == nullptr -> * (EnsembleResultat ne contient aucune Cellule)
    //      >> ptrListeEnsembleB != nullptr ->
    //          >>> ptrListeEnsembleA->info == ptrListeEnsembleB->info -> ranger info dans EnsembleResultat *
    //          >>> ptrListeEnsembleA->info > ptrListeEnsembleB->info -> avancer sur B; => H
    //          >>> ptrListeEnsembleA->info < ptrListeEnsembleB->info -> avancer sur A; => H
    // Itération : tq (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr && (ptrListeEnsembleA->info > ptrListeEnsembleB->inf || ptrListeEnsembleA->info < ptrListeEnsembleB->info))
    // Tableau de sortie inutile !
    //  -> si EnsembleResultat est encore videRec alors l'intersection sera vide sinon il faudra poursuivre le travail

    while (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr && !(ptrListeEnsembleA->getInfo() == ptrListeEnsembleB->getInfo())) {
        if (ptrListeEnsembleA->getInfo() > ptrListeEnsembleB->getInfo()) {
            ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
        } else {
            ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
        } // end if
    } // end while
    //
    if (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr && (ptrListeEnsembleA->getInfo() == ptrListeEnsembleB->getInfo())) {
        // insérer la première valeur commune dans EnsembleResultat
        ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo());
    }
    //sinon EnsembleResultat coninue de contenir aucune Cellule et l'intersection restera videRec

    //si on a pu mettre une première valeur, alors on continue de parcourir EnsembleA et EnsembleB sinon, on a terminé
    if (ptrEnsembleResultat->ptrTete != nullptr) {
        // Hypothèse : EnsembleResultat = intersection des élements examinés de EnsembleA et EnsembleB
        //             ptrDerniereCelluleER pointe sur la dernière Cellule de EnsembleResultat
        // > ptrListeEnsembleA == nullptr -> * (on a fini l'intersection)
        // > ptrListeEnsembleA != nullptr ->
        //      >> ptrListeEnsembleB == nullptr -> * (on a fini l'intersection)
        //      >> ptrListeEnsembleB != nullptr ->
        //          >>> ptrListeEnsembleA->info > ptrListeEnsembleB->info -> avancer sur B; => H
        //          >>> ptrListeEnsembleA->info == ptrListeEnsembleB->info -> ranger info dans EnsembleResultat (suivant de ptrDerniereCelluleER), mettre à jour ptrDerniereCelluleER puis avancer sur A et B; => H
        //          >>> ptrListeEnsembleA->info < ptrListeEnsembleB->info -> avancer sur A; => H
        // Itération : tq (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr)
        // Initilalisation : avancer sur EnsembleA et EnsembleB puisque on a déjà examiner leur élément courant
        // Tableau de sortie inutile !
        //  -> return ptrEnsembleResultat

        //avancer sur EnsembleA et EnsembleB pour "consommer" la valeur que l'on a insérer dans l'intersection
        ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
        ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
        //pointeur sur la dernière Cellule de l'ensemble résutlat (on va ajouter derrière)
        Cellule<TypeInfo>* ptrDerniereCelluleER = ptrEnsembleResultat->ptrTete;

        while (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr) {
            if (ptrListeEnsembleA->getInfo() == ptrListeEnsembleB->getInfo()) {
                // ranger l'info dans EnsembleResultat et avancer sur la liste de Cellules
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer sur EnsembleA et EnsembleB
                ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
                ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
            } else if (ptrListeEnsembleA->getInfo() > ptrListeEnsembleB->getInfo()) {
                // avancer sur EnsembleB
                ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
            } else {
                // avancer sur EnsembleA
                ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
            } // end if
        } // end while
        // on a fini l'intersection
    } // end if

    return ptrEnsembleResultat;
} // end intersectionAvec

template<class TypeInfo>
ListeTrieeChainee<TypeInfo>* ListeTrieeChainee<TypeInfo>::unionAvec(const ListeTrieeChainee<TypeInfo>* ensembleB) const {
    // l'idée est de mettre, si possible une première Cellule dans EnsembleResultat
    // puis on insérera successivment derière la dernière Cellule de EnsembleResultat

    // intitialisation de l'ensemble résultat, il ne contient aucune Cellule son ptrTete == nullptr
    ListeTrieeChainee<TypeInfo>* ptrEnsembleResultat = new ListeTrieeChainee<TypeInfo>();

    //pointeur sur la première Cellule de chacun des deux ensembles
    Cellule<TypeInfo>* ptrListeEnsembleA = this->ptrTete;
    Cellule<TypeInfo>* ptrListeEnsembleB = ensembleB->ptrTete;


    //on met une première Cellule dans EnsembleResultat car ensuite on modifiera le suvant de la dernière Cellule
    //trouver le premier à mettre dans l'union (c'est la plus petite, si elle existe entre EnsembleA et EnsembleB)
    // et le consommer dans l'ensemble ou on l'a pris
    if (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr) {
        // deux premières valeurs parmi lesquelles choisir
        if (ptrListeEnsembleA->getInfo() < ptrListeEnsembleB->getInfo()) {
            // on prend la plus petite dans EnsembleA et on la consomme dans EnsembleA
            ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo());
            ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
        } else if (ptrListeEnsembleA->getInfo() > ptrListeEnsembleB->getInfo()) {
            // on prend la plus petite dans EnsembleB et on la consomme dans EnsembleB
            ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleB->getInfo());
            ptrListeEnsembleA = ptrListeEnsembleB->getSuivante();
        } else {
            // les valeurs sont égales, on la prend et on la consomme dans EnsembleA et EnsembleB
            ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleB->getInfo());
            ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
            ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
        }
    } else if (ptrListeEnsembleA != nullptr && ptrListeEnsembleB == nullptr) {
        // une première valeur dans EnsembleA, EnsembleB est videRec
        // on prend la première valeur de EnsembleA et on la consomme dans EnsembleA
        ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo());
        ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
    } else if (ptrListeEnsembleA == nullptr && ptrListeEnsembleB != nullptr) {
        // une première valeur dans EnsembleB, EnsembleA est videRec
        // on prend la première valeur de EnsembleB et on la consomme dans EnsembleA
        ptrEnsembleResultat->ptrTete = new Cellule<TypeInfo>(ptrListeEnsembleB->getInfo());
        ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
    }
    // sinon EnsembleA et EnsembleB sont vide, leur union sera videRec

    //si on a pu mettre une première valeur, alors on continue de parcourir EnsembleA et EnsembleB sinon, on a terminé
    if (ptrEnsembleResultat->ptrTete != nullptr) {
        // Hypothèse : EnsembleResultat = union des élements examinés de EnsembleA et EnsembleB
        //             ptrDerniereCelluleER pointe sur la dernière Cellule de EnsembleResultat
        // > ptrListeEnsembleA == nullptr -> * (finir de consommer EnsembleB pour faire l'union)
        // > ptrListeEnsembleA != nullptr ->
        //      >> ptrListeEnsembleB == nullptr -> * (finir de consommer EnsembleA pour faire l'union)
        //      >> ptrListeEnsembleB != nullptr ->
        //          >>> ptrListeEnsembleA->info > ptrListeEnsembleB->info -> ranger le plus petit EnsembleResultat (suivant de ptrDerniereCelluleER), mettre à jour ptrDerniereCelluleER puis avancer sur B; => H
        //          >>> ptrListeEnsembleA->info == ptrListeEnsembleB->info -> ranger info dans EnsembleResultat (suivant de ptrDerniereCelluleER), mettre à jour ptrDerniereCelluleER puis avancer sur A et B; => H
        //          >>> ptrListeEnsembleA->info < ptrListeEnsembleB->info -> ranger le plus petit EnsembleResultat (suivant de ptrDerniereCelluleER), mettre à jour ptrDerniereCelluleER puis avancer sur A => H
        // Itération : tq (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr)
        // Initilalisation : sur EnsembleA et EnsembleB on pointe sur le premier élément non traité donc rien à faire
        //                   ptrDerniereCelluleER pointe sur la dernière Cellule de EnsembleResultat
        // Tableau de sortie inutile !
        //  -> return ptrEnsembleResultat

        //pointeur sur la dernière Cellule de l'ensemble résutlat (on va ajouter derrière)
        Cellule<TypeInfo>* ptrDerniereCelluleER = ptrEnsembleResultat->ptrTete;

        while (ptrListeEnsembleA != nullptr && ptrListeEnsembleB != nullptr) {
            if (ptrListeEnsembleA->getInfo() == ptrListeEnsembleB->getInfo()) {
                // ranger l'info commune dans EnsembleResultat
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer sur EnsembleA et EnsembleB
                ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
                ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
            } else if (ptrListeEnsembleA->getInfo() > ptrListeEnsembleB->getInfo()) {
                // ranger la plus petite info dans EnsembleResultat
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleB->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer EnsembleB
                ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
            } else {
                // ranger la plus petite info dans EnsembleResultat
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer sur EnsembleA
                ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
            } // end if
        } // end while

        // l'un des deux restes de EnsembleA ou EnsembleB est videRec ou les deux sont vides
        // recopier le reste qui n'est pas videRec dans EnsembleResultat
        if (ptrListeEnsembleA != nullptr) {
            // le reste de EnsembleA n'est pas videRec, le recopier
            while (ptrListeEnsembleA != nullptr) {
                // ranger l'info courante dans EnsembleResultat
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleA->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer sur EnsembleA
                ptrListeEnsembleA = ptrListeEnsembleA->getSuivante();
            }
        } else if (ptrListeEnsembleB != nullptr) {
            while (ptrListeEnsembleB != nullptr) {
                // ranger l'info courante dans EnsembleResultat
                ptrDerniereCelluleER->setSuivante(new Cellule<TypeInfo>(ptrListeEnsembleB->getInfo()));
                ptrDerniereCelluleER = ptrDerniereCelluleER->getSuivante();
                // avancer sur EnsembleA
                ptrListeEnsembleB = ptrListeEnsembleB->getSuivante();
            }
        }
        // sinon les deux restes sont vides donc rien à faire
    } // end if

    //on a terminé
    return ptrEnsembleResultat;
} // end unionAvec

//  Fin de l'implémentation.

#endif