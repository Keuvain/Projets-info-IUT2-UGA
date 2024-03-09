package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Devis d'un tournoi. Un devis résume l'ensemble des dépense et des revenus liés au tournoi.
 */
public class Devis implements Serializable {

    private static final long serialVersionUID = 1L;
    private Tournoi tournoi;
    private HashMap<String, Double> operations = new HashMap<>();
    private Double benefice;

    /**
     * détérmine toutes les opérations d'un tournois
     * @param tournoi tournoi sur lequel est caculé le devis
     */
    public Devis(Tournoi tournoi) {
        this.tournoi = tournoi;
        operations.put("Terrain", tournoi.getPrixTerrain()*(-1));
        double coutIntervenants = (tournoi.getNbSecouristeReco()*90L +tournoi.getNbAgentSecuriteReco()*80L)*tournoi.getDuree();
        operations.put("Intervenants", coutIntervenants*(-1));
        operations.put("Récompense", tournoi.getRecompense()*(-1));
        operations.put("Participations", tournoi.getPrixParticipants()*tournoi.getNbParticipants());
        operations.put("Spectateurs", tournoi.getPrixPlacesSpec()*tournoi.getNbPlacesSpec());
        double mlTot = 0;
        for (Stand stand : tournoi.getStands()) {
            mlTot += stand.getmLineaire();
        }
        operations.put("Stands", tournoi.getPrixMetreLineaire()*mlTot);
        sommeOperations();
    }

    /**
     * ajoute une opération au tournoi
     * @param label label de l'opération
     * @param cout cout de l'opération (n'est pas nécessairement une dépense)
     * @param depense booleen déterminant si l'opération est une dépense ou non
     */
    public void addOperation(String label, double cout, boolean depense) {
        if (depense) {
            operations.put(label, cout*(-1));
        } else {
            operations.put(label, cout);
        }
        sommeOperations();
    }

    /**
     * Fais la somme des opérations pour déterminer le bénéfice du tournoi.
     */
    public void sommeOperations() {
        Collection<Double> opes = operations.values();
        double sommeOpes = 0;
        for (Double ope : opes) {
            sommeOpes += ope;
        }
        this.benefice = Math.round(sommeOpes*100.0)/100.0;
    }

    /**
     * Getter du benefice
     * @return bénéfice du tournoi, est négatif si le tournoi est à perte
     */
    public double getBenefice() {
        return benefice;
    }

    public HashMap<String, Double> getOperations() {
        return operations;
    }
}
