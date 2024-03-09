package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.Devis;
import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;

public class DevisCtrl extends AbstractCtrl {

    @FXML
    public Label lbBenef;
    public GridPane gpOP;

    public Devis devis;


    @Override
    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        this.devis = tournoi.getDevis();

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        gpOP.getChildren().clear();

        int i = 0;
        for (Map.Entry<String, Double> entry : devis.getOperations().entrySet()) {
            gpOP.add(new Label(entry.getKey() + " : "), 0, i);
            gpOP.add(new Label(entry.getValue() + " €"), 1, i);
            i++;
            lbBenef.setText(" " + devis.getBenefice() + " ");
        }
    }

    @FXML
    private void onAjouterButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        String address = "src/main/resources/fr/uga/iut2/genevent/vue/Ajout-operation.fxml";
        InputStream fxmlStream = new FileInputStream(address);
        Parent root = fxmlLoader.load(fxmlStream);

        AjouterOpCtrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(genEvent, tournoi);
        ctrl.initDevis(devis);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }

    @FXML
    private void onRetourButtonClicked(ActionEvent event) {
        infosCtrl.initVue(this.genEvent, this.tournoi);

        Stage fenetre = (Stage) retourBtn.getScene().getWindow();

        fenetre.close();
        Logging.LOGGER.log(Level.INFO, "Retour au informations");
    }

}
