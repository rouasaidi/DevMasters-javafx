package tests;

import controls.afficheruser;
import entites.user;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.usercrud;

import java.io.IOException;
import java.sql.SQLException;


public class testcrud {
    public static void main(String[] args) throws SQLException {

        user p = new user(50,12, 123, "achref", "556565", "klkvllkblbkl", "ljlfajfljlbjblj","vklkblbklbklk", "jsjlbjlbjelbej",false,false);
        usercrud p1 = new usercrud();
        p1.ajouteruser(p);
        p1.supprimer(p);

       /* LocalDate currentDate = LocalDate.now();
        Comment c=new Comment(23,2,3,"this my first comment",currentDate);
        CommentCRUD go=new CommentCRUD();
        ArrayList<Comment>comments=go.afficherAll
ewh-esir-vcj*/

    }
}
