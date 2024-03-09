package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class AbstractCtrl {

    @FXML public ImageView logo;
    @FXML
    public Button validerBtn ;
    public Button retourBtn ;
    protected GenEvent genEvent ;
    protected Tournoi tournoi ;
    protected InformationCtrl infosCtrl;
    @FXML public Button accueil;

    @FXML
    private void retourAccueil(ActionEvent event) throws IOException {
        ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir revenir a l'accueil ?");
        dialogC.setTitle("Retour a l'accueil");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            String adress = "src/main/resources/fr/uga/iut2/genevent/vue/main-view.fxml";
            InputStream fxmlStream = new FileInputStream(adress);
            Parent root = fxmlLoader.load(fxmlStream);

            AccueilCtrl ctrl = fxmlLoader.getController();
            ctrl.initVue(this.genEvent,this.tournoi);

            Stage fenetre = (Stage) accueil.getScene().getWindow();
            Scene scene = new Scene(root);
            fenetre.setScene(scene);
            fenetre.show();
        }
    }

    public void logoInitialize() throws URISyntaxException {
        Image image = new Image("file:doc/imgs/Logo_TractEvent.png");
        logo.setImage(image);
    }

    public void initCtrl(InformationCtrl infoCtrl) {
        this.infosCtrl = infoCtrl;
    }

    public abstract void initVue(GenEvent genEvent, Tournoi tournoi);

    public void initModele(GenEvent genevent) {
        this.genEvent = genevent ;
    }

    public void initTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    protected Stage changementVue(String adresse, AbstractCtrl ctrl) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream fxmlStream = new FileInputStream(adresse);
        Scene scene = new Scene(fxmlLoader.load(fxmlStream));

        ctrl.initModele(genEvent);
        ctrl.initTournoi(tournoi);

        Stage fenetre = (Stage) validerBtn.getScene().getWindow();

        fenetre.setScene(scene);

        return fenetre ;
    }

    /**
     * Retourne le Node situé dans une cellule d'un GridPane. Pour utiliser cette méthode, il est obligatoire qu'une
     * cellule de la GridPane ne possède qu'un seul Node.
     * La méthode a besoin de :
     * @param column l'indice de la colonne du Node recherché
     * @param row l'indice de la ligne du Node recherché
     * @param gridPane la GridPane dans laquelle on souhaite faire la recherhce
     * @return un Node si il y en a un dans la cellule définie, null sinon
     */
    public Node getNodeByRowColumnIndex (final int column, final int row, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    public void numberOnly(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            tf.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }


    public void limitedLength(TextField tf, int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }


    public void resetText(TextField tf, Label lb) {
        tf.setText("");lb.setText("");
    }

}
