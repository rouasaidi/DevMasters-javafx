module tn.esprit.devmasters {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.mail;
    requires layout;
    requires kernel;

    opens tn.esprit.devmasters to javafx.fxml;


    opens tn.esprit.devmasters.gui to javafx.fxml;


    opens tn.esprit.devmasters.models to javafx.fxml;

    exports tn.esprit.devmasters.gui;
    exports tn.esprit.devmasters;
    exports tn.esprit.devmasters.models;

}