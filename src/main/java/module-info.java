module com.example.leila {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.leila.Models to javafx.base;
    opens com.example.leila.Controller.User to javafx.fxml;
    opens com.example.leila.Controller.admin to javafx.fxml;
<<<<<<< HEAD
=======
    requires java.mail;
>>>>>>> 5f185cf (Heeeeeeello)

    exports com.example.leila;
    exports com.example.leila.Controller.User;
    exports com.example.leila.Controller.admin;


}
