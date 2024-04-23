package controls;

import entites.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.usercrud;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class afficheruser {

    @FXML
    private TextField usernametextfiled;
    @FXML
    private Button back;


    @FXML
    private TableColumn<user, Boolean> IS_banned;

    @FXML
    private TableColumn<user, String> Reset_token;

    @FXML
    private AnchorPane Ap_afficher;


    @FXML
    private TableColumn<user, String> Roles;


    @FXML
    private TableColumn<user,Integer > cin;

    @FXML
    private TableColumn<user, String> email;

    @FXML
    private TableColumn<user, Integer> id;

    @FXML
    private TableColumn<user, String> image;

    @FXML
    private TableColumn<user, Boolean> IS_verified;

    @FXML
    private TableColumn<user, String> name;

    @FXML
    private TableColumn<user, String> password;

    @FXML
    private TableColumn<user, Integer> phone;
    @FXML
    private Button supprimer;


    @FXML
    private Button modifier;

    @FXML
    private TableView<user> tableview;
    public final usercrud ps=new usercrud();
    @FXML
    void initialize() throws SQLException {
        // Appeler la méthode pour charger les données des utilisateurs
        List<user> users=ps.afficheruser();
        ObservableList<user> userList = FXCollections.observableArrayList(users);

        // Ajouter les données dans la TableView
        tableview.setItems(userList);

        // Mapper les propriétés des utilisateurs aux colonnes de la TableView
       // id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        Reset_token.setCellValueFactory(new PropertyValueFactory<>("reset_token"));
        IS_banned.setCellValueFactory(new PropertyValueFactory<>("is_banned"));
        IS_verified.setCellValueFactory(new PropertyValueFactory<>("is_verified"));
    }
    private void loadTableData() {
        try {
            List<user> users = new ArrayList<>();
            users = ps.afficheruser();
            tableview.setItems(FXCollections.observableArrayList(users));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            }

    @FXML

    void supprimer(ActionEvent event) {
        // Récupérer l'élément sélectionné dans la TableView
        user selectedItem = tableview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Supprimer l'élément sélectionné de la base de données
                ps.supprimer(selectedItem);

                // Supprimer l'élément sélectionné de la TableView
                tableview.getItems().remove(selectedItem);

                // Afficher une alerte de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Élément supprimé avec succès.");
                successAlert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la suppression : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Afficher un message si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un élément à supprimer.");
            alert.showAndWait();
        }
    }



    @FXML
    void naviguer (ActionEvent event){
        TableView.TableViewSelectionModel<user> selectionModel = tableview.getSelectionModel();
        if (selectionModel.isEmpty()) {
            System.out.println("Vous devez sélectionner des éléments avant de les supprimer.");
            return;
        }
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            user selectedUser = selectionModel.getSelectedItem();
            modifieruser updateuser = fxmlLoader.getController();
            // Utilisez l'ID ou tout autre attribut de votre objet utilisateur pour passer les données au contrôleur
            updateuser.setID(selectedUser.getId(), selectedUser.getEmail(), selectedUser.getName(), selectedUser.getPhone(), selectedUser.getCin(), selectedUser.getImage(), selectedUser.getRoles(), selectedUser.getPassword());
            System.out.println(selectedUser.getId());

          /*  modifieruser updateuser=fxmlLoader.getController();
            updateuser.setID(selectionModel[0].getRefund_id());*/

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}







}





