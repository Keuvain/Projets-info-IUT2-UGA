package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.logging.Level;


public class Etape1Ctrl extends AbstractCtrl{

    @FXML
    public TextField tfNom;
    public TextField tfLieu;
    public DatePicker dpDateDebut;
    public DatePicker dpDateFin;
    public Label lErreurNom;
    public Label lErreurLieu;
    public Label lErreurDateDebut;
    public Label lErreurDateFin;



    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        final Callback<DatePicker, DateCell> datesAntAujo =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.now().plusDays(1)
                                )) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        dpDateDebut.setDayCellFactory(datesAntAujo);



        dpDateFin.setDayCellFactory(datesAntAujo);
        
//        dpDateDebut.setDayCellFactory(param -> new DateCell() {
//            public void udpateItem(LocalDate date, boolean empty) {
//                super.updateItem(date,empty);
////                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
//                if (date.isBefore(LocalDate.now())) {
//                    setDisable(true);
//                }
//            }
//        });


        if(tournoi != null && tournoi.getNom() != null ) {
            affichageInfos();
        }
    }
    @FXML
    private void resetNom(MouseEvent event){
            lErreurNom.setText("");
        tfNom.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetLieu(MouseEvent event){
        lErreurLieu.setText("");
        tfLieu.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetDateDebut(MouseEvent event){
        lErreurDateDebut.setText("");
        dpDateDebut.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }
    @FXML
    private void resetDateFin(MouseEvent event){
        lErreurDateFin.setText("");
        dpDateFin.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
    }

    @FXML
    private void validerButtonAction(ActionEvent event) throws IOException {

        if (donneesValides()){
            enregistrerInfos();

            FXMLLoader fxmlLoader = new FXMLLoader();
            String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Creer-tournoi-002.fxml";
            InputStream fxmlStream = new FileInputStream(adress);
            Parent root = fxmlLoader.load(fxmlStream);

            Etape2Ctrl ctrl = fxmlLoader.getController();
            ctrl.initVue(this.genEvent,this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            fenetre.setScene(scene);
            fenetre.show();
            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
        }
    }

    /**
     * Vérifier si les données saisies sont valides pour être enregistrées.
     * Contraintes :
     * - Nom :champ non vide
     * - Lieu : champ non vide
     * - Dates : champs non vides, format respecté et date début <= date fin
     * @return true si les données sont validées, false sinon
     */
    private boolean donneesValides() {
        boolean verif = true ;
        if (tfNom.getText().isEmpty() || tfNom.getText().isBlank()){  // Si le nom est vide
            tfNom.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lErreurNom.setText("Mauvaise saisie");

            verif = false;
        }
        if (tfLieu.getText().isEmpty()){    // Si le lieu est vide
            tfLieu.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lErreurLieu.setText("Lieu non valide");
            verif = false;
        }

        if (dpDateDebut.getEditor().getText() == null){   // Si la date de début est vide

            dpDateDebut.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222; ");
            lErreurDateDebut.setText("date manquante");
            verif = false;
        } else {
            try {   // Vérifie le format de la date
                LocalDate date = LocalDate.parse(dpDateDebut.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                dpDateDebut.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                verif = false;
                lErreurDateDebut.setText("Date non valide");
            }
        }

        if (dpDateFin.getEditor().getText() == null){     // Si la date de fin est vide
            dpDateFin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            lErreurDateFin.setText("Sélectionnez une date");
            verif = false;
        } else {
            try {       // Vérifie le format de la date et si date debut < date fin
                LocalDate dateDebut = LocalDate.parse(dpDateDebut.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dateFin = LocalDate.parse(dpDateFin.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (dateFin.isBefore(dateDebut)) {
                    dpDateFin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    lErreurDateFin.setText("Impossible");
                    verif = false;
                }
            } catch (DateTimeParseException e) {
                dpDateFin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                lErreurDateFin.setText("Date non valide");
                verif = false;
            }
        }

        return verif ;
    }

    private void enregistrerInfos() {
        this.tournoi.setNom(tfNom.getText());
        this.tournoi.setLieu(tfLieu.getText());
        LocalDate dateDebut = LocalDate.parse(dpDateDebut.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateFin = LocalDate.parse(dpDateFin.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.tournoi.setDate(dateDebut,dateFin);
    }

    public void affichageInfos() {
        tfNom.setText(this.tournoi.getNom());
        tfLieu.setText(this.tournoi.getLieu());
        dpDateDebut.setValue(this.tournoi.getDateDebut());
        dpDateFin.setValue(this.tournoi.getDateFin());
    }

}
