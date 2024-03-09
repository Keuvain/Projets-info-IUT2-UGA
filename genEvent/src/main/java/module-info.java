module genevent {
    requires commons.validator;
//    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.logging;

    opens fr.uga.iut2.genevent.vue to javafx.fxml;
    opens fr.uga.iut2.genevent.controleur to javafx.fxml ;

    exports fr.uga.iut2.genevent;
}
