package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Participant;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;

public class ParticipantCtrl extends AbstractCtrl{

    private boolean modifier ;
    private Participant participant;
    @FXML
    public TextField tfNom;
    public TextField tfPrenom ;
    public TextField tfMail ;
    public TextField tfTel ;
    public Label lbAriane ;
    public Button btnAnnuler ;
    public Button btnAjouter ;
    public Button btnEnregistrer ;
    public Label lbErreurNom ;
    public Label lbErreurPrenom ;
    public Label lbErreurMail ;
    public Label lbErreurTel ;

    public void initModifier(boolean modifier,Participant participant) {
        this.modifier = modifier;
        this.participant = participant;
        if (modifier) {
            btnAjouter.setVisible(false);
            lbAriane.setText("Informations > Modifier un participant");

            tfNom.setText(participant.getNom());
            tfPrenom.setText(participant.getPrenom());
            tfMail.setText(participant.getEmail());
            tfTel.setText(participant.getNumTel());
        }
    }
    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        lbAriane.setText(lbAriane.getText() + " Ajouter un participant");
        numberOnly(tfTel);
        limitedLength(tfTel,10);

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void resetNom(MouseEvent event){
        lbErreurNom.setText("");
        tfNom.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetPrenom(MouseEvent event){
        lbErreurPrenom.setText("");
        tfPrenom.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetMail(MouseEvent event){
        lbErreurMail.setText("");
        tfMail.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetTel(MouseEvent event){
        lbErreurTel.setText("");
        tfTel.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    private void resetAllText() {
        resetText(tfNom,lbErreurNom);
        resetText(tfPrenom,lbErreurPrenom);
        resetText(tfMail,lbErreurMail);
        resetText(tfTel,lbErreurTel);
    }

    @FXML
    private void ajouterButtonOnAction(ActionEvent event) throws IOException {

        if (donneesValides()){

            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String mail = tfMail.getText();
            String tel = tfTel.getText();

            Participant participant = new Participant(nom,prenom,mail,tel);
            tournoi.addParticipants(participant);

            resetAllText();
            Logging.LOGGER.log(Level.INFO, "Ajout d'un participant");
        }
    }

    @FXML
    private void annulerButtonOnAction()  {

        ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.WARNING, "Le participant ne sera pas enregistré. \n Continuer ?", annuler,continuer);
        dialogC.setTitle("Annuler la modification");
        Logging.LOGGER.log(Level.WARNING, "Les informations présentes à cette étape ne seront pas sauvegardées");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == continuer) {

            infosCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) btnAnnuler.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Retour aux informations");
        }
    }


    @FXML
    private void enregistrerButtonOnAction(ActionEvent event) {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String mail = tfMail.getText();
        String tel = tfTel.getText();
        Participant nouveauParticipant = new Participant(nom, prenom, mail, tel);
        if (donneesValides()) {

            if (modifier) {
                    tournoi.getParticipants().replace(participant.getNom(),participant, nouveauParticipant);
                } else {

                tournoi.addParticipants(nouveauParticipant);

            }
            Stage fenetre = (Stage) btnAnnuler.getScene().getWindow();
            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Ajout d'un participant et retour aux informations");
        }
    }


    private boolean donneesValides() {
        boolean verif = true ;

        if (tfNom.getText().isEmpty() || tfNom.getText().isBlank()) {
            verif = false;
            tfNom.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurNom.setText("Non renseigné");
        }

        if (tfPrenom.getText().isEmpty() || tfPrenom.getText().isBlank()) {
            verif = false;
            tfPrenom.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurPrenom.setText("Non renseigné");
        }

        if (tfMail.getText().isEmpty() || tfMail.getText().isBlank()) {
            verif = false;
            tfMail.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurMail.setText("Non renseigné");
        } else if (!tfMail.getText().contains("@") || (!tfMail.getText().contains(".fr") && !tfMail.getText().contains(".com"))) {
            verif = false;
            tfMail.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurMail.setText("Mauvais format");
        }

        if (tfTel.getText().isEmpty()) {
            verif = false;
            tfTel.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurTel.setText("Non renseigné");
        }if (tfTel.getText().length() != 10) {
            verif = false;
            tfTel.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lbErreurTel.setText("Mauvais format");
        }
        return verif;
    }

}
