module tn.esprit.devmasters {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens tn.esprit.devmasters to javafx.fxml;
    exports tn.esprit.devmasters;
}