package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;

public class Modifier2Ctrl extends AbstractCtrl{

    @FXML
    public TextField tfPlacesSpec;
    public TextField tfPlacesParticipants;
    public Label lNbSpec;
    public Label lNbPart;


    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        numberOnly(tfPlacesSpec);
        numberOnly(tfPlacesParticipants);

        if ( tournoi.getNbPlacesSpec() != null) {
            affichageInfos();
        }
    }

    @FXML
    private void resetSpec(MouseEvent event){
        lNbSpec.setText("");
        tfPlacesSpec.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }

    @FXML
    private void resetPart(MouseEvent event){
        lNbPart.setText("");
        tfPlacesParticipants.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }


    @FXML
    private void validerButtonAction(ActionEvent event) throws IOException {

        if (donneesValides()){
            enregistrerInfos();

            infosCtrl.initVue(this.genEvent,this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
        }
    }
    @FXML
    private void retourButtonAction() throws IOException {
        ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.WARNING, "Les modifications ne seront pas enregistrées. \n Continuer ?", annuler,continuer);
        dialogC.setTitle("Annuler la modification");
        Logging.LOGGER.log(Level.WARNING, "Les informations présente à cette étape ne seront pas sauvegardés");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == continuer) {

            infosCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) retourBtn.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Retour au informations");
        }
    }

    private boolean donneesValides() {
        boolean verif = true ;
        if (tfPlacesSpec.getText().isEmpty() ) {
            tfPlacesSpec.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lNbSpec.setText("Valeur non saisie");
            verif = false;
        }
        if (tfPlacesParticipants.getText().isEmpty() ) {
            tfPlacesParticipants.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lNbPart.setText("Valeur non saisie");
            verif = false;
        }

        return verif ;
    }

    private void enregistrerInfos() {
        this.tournoi.setNbPlacesSpec(Integer.parseInt(tfPlacesSpec.getText()));
        this.tournoi.setNbParticipants(Integer.parseInt(tfPlacesParticipants.getText()));
        tournoi.optimiserTournoi();
        tournoi.genererDevis();
    }

    public void affichageInfos() {
        tfPlacesSpec.setText(this.tournoi.getNbPlacesSpec().toString());
        tfPlacesParticipants.setText(this.tournoi.getNbParticipants().toString());
    }


}

