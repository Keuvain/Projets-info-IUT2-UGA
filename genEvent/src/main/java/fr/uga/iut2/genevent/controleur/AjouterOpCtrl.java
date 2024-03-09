package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.Devis;
import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;

public class AjouterOpCtrl extends AbstractCtrl {

    @FXML
    public TextField tfNomOp;
    public TextField tfValOp;
    public CheckBox cbDepense;

    DevisCtrl devisCtrl;

    Devis devis;

    public void initCtrl(DevisCtrl devisCtrl) {
        this.devisCtrl = devisCtrl;
    }

    public void initDevis(Devis devis) {
        this.devis = devis;
    }

    @Override
    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void retourButtonAction() {
        ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.WARNING, "L'opération ne seront pas enregistrées. \n Continuer ?", annuler,continuer);
        dialogC.setTitle("Annuler l'ajout");
        Logging.LOGGER.log(Level.WARNING, "Les informations présente à cette étape ne seront pas sauvegardés");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == continuer) {

            devisCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) retourBtn.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Retour au informations");
        }
    }

    @FXML
    private void validerButtonAction() {
        if (donneesValides()){
            enregistrerInfo();

            devisCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
        }
    }

    private boolean donneesValides() {
        boolean verif = true ;
        if (tfNomOp.getText().isEmpty() ) {
            tfNomOp.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            verif = false;
        }
        if (tfValOp.getText().isEmpty() ) {
            tfValOp.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            verif = false;
        }
        return verif ;
    }

    private void enregistrerInfo() {
        devis.addOperation(tfNomOp.getText(), Double.parseDouble(tfValOp.getText()), cbDepense.isSelected());
        devis.sommeOperations();
    }
}
