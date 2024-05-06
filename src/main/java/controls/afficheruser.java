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
import java.util.Optional;


public class afficheruser {

    @FXML
    private TextField usernametextfiled;
    @FXML
    private Button back;
    @FXML
    private Button ban;
    @FXML
    private Button excel;


    @FXML
    private TableColumn<user, Boolean> IS_banned;

    @FXML
    private TableColumn<user, String> Reset_token;

    @FXML
    private AnchorPane tableauafficher;


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
    private TextField rehcercher;
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
  /*  private void loadTableData() {
        try {
            List<user> users = new ArrayList<>();
            users = ps.afficheruser();
            tableview.setItems(FXCollections.observableArrayList(users));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            }*/

    @FXML
    void supprimer(ActionEvent event) {
        // Vérifier d'abord si un utilisateur est sélectionné
        if (tableview.getSelectionModel().isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur de sélectionner un utilisateur
            Alert selectAlert = new Alert(Alert.AlertType.WARNING);
            selectAlert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            selectAlert.showAndWait();
            return; // Arrêter l'exécution de la méthode car aucun utilisateur n'est sélectionné
        }

        // Récupérer l'élément sélectionné dans la TableView
        user selectedItem = tableview.getSelectionModel().getSelectedItem();
        // Afficher une alerte de confirmation pour vérifier si l'utilisateur souhaite vraiment supprimer l'utilisateur sélectionné
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Supprimer Utilisateur");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
        }
    }




    @FXML
    void naviguer(ActionEvent event) {
        TableView.TableViewSelectionModel<user> selectionModel = tableview.getSelectionModel();
        if (selectionModel.isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur de sélectionner un utilisateur
            Alert selectAlert = new Alert(Alert.AlertType.WARNING);
            selectAlert.setTitle("Aucune sélection");
            selectAlert.setHeaderText(null);
            selectAlert.setContentText("Vous devez sélectionner un utilisateur avant de faire  la modification.");
            selectAlert.showAndWait();
            return;
        }

        // Récupérer l'utilisateur sélectionné
        user selectedUser = selectionModel.getSelectedItem();

        // Afficher une alerte de confirmation pour vérifier si l'utilisateur souhaite vraiment naviguer vers la modification de l'utilisateur sélectionné
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Navigation vers la modification d'utilisateur");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir faire la modification de cet utilisateur?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Charger la vue de modification de l'utilisateur
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();

                // Passer les données de l'utilisateur sélectionné au contrôleur de modification
                modifieruser updateuser = fxmlLoader.getController();
                updateuser.setID(selectedUser.getId(), selectedUser.getEmail(), selectedUser.getName(), selectedUser.getPhone(), selectedUser.getCin(), selectedUser.getImage(), selectedUser.getRoles(), selectedUser.getPassword());

                // Fermer la fenêtre actuelle
                Stage stageAfficherUser = (Stage) tableauafficher.getScene().getWindow();
                stageAfficherUser.close();

                // Ouvrir la fenêtre de modification
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    @FXML
    void ban(ActionEvent event) {
        user selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // Afficher une alerte indiquant qu'aucun utilisateur n'est sélectionné
            Alert noUserSelectedAlert = new Alert(Alert.AlertType.WARNING);
            noUserSelectedAlert.setTitle("No User Selected");
            noUserSelectedAlert.setHeaderText(null);
            noUserSelectedAlert.setContentText("Please select a user before banning.");
            noUserSelectedAlert.showAndWait();
            return;
        }

        // Vérifier si l'utilisateur est déjà banni
        if (selectedUser.isIs_banned()) {
            // Afficher une alerte pour demander la confirmation pour débannir l'utilisateur
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Unban User");
            confirmationAlert.setContentText("Are you sure you want to unban this user?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Mettez à jour l'attribut is_banned de l'utilisateur sélectionné pour le débannir
                    usercrud crud = new usercrud();
                    crud.updateBannedStatus(selectedUser.getId(), false); // Mettez à false pour débannir l'utilisateur

                    // Actualisez le tableau pour refléter les changements
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Afficher une alerte de confirmation pour vérifier si l'utilisateur souhaite vraiment bannir l'utilisateur sélectionné
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Ban User");
            confirmationAlert.setContentText("Are you sure you want to ban this user?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Mettez à jour l'attribut is_banned de l'utilisateur sélectionné pour le bannir
                    usercrud crud = new usercrud();
                    crud.updateBannedStatus(selectedUser.getId(), true); // Mettez à true pour bannir l'utilisateur

                    // Actualisez le tableau pour refléter les changements
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    void excel(ActionEvent event) throws SQLException {
        usercrud service = new usercrud();
        List<user>users = service.afficheruser();
        ExcelService excelService = new ExcelService();
        excelService.generateExcel(users);

    }
    @FXML
    void rehcercher(ActionEvent event) {
// Ajouter un écouteur de changement de texte au champ de texte
        rehcercher.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appel de la méthode de filtrage avec le nouveau texte saisi comme filtre
            filterTableData(newValue);
        });
    }
    private void filterTableData(String filter) {
        if (filter == null || filter.isEmpty()) {
            loadTableData();  // Recharger toutes les données si le filtre est effacé
            return;
        }

        try {
            List<user> allUsers = ps.afficheruser();
            ArrayList<user> filteredUsers = new ArrayList<>();

            String lowerCaseFilter = filter.toLowerCase();

            for (user user : allUsers) {
                if (user.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                        user.getName().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(user.getPhone()).contains(lowerCaseFilter) ||
                        String.valueOf(user.getCin()).contains(lowerCaseFilter) ||
                        user.getImage().toLowerCase().contains(lowerCaseFilter) ||
                        (user.getReset_token() != null && user.getReset_token().toLowerCase().contains(lowerCaseFilter)) ||
                        String.valueOf(user.isIs_verified()).toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(user.isIs_banned()).toLowerCase().contains(lowerCaseFilter) ||
                        user.getRoles().toLowerCase().contains(lowerCaseFilter) ||
                        user.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                    filteredUsers.add(user);
                }
            }

            tableview.setItems(FXCollections.observableArrayList(filteredUsers)); // Mettre à jour la vue du tableau
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de base de données");
            alert.setContentText("Erreur lors de l'accès à la base de données pour le filtrage : " + e.getMessage());
            alert.showAndWait();
        }
    }




    private void loadTableData() {
        try {
            List<user>users=new ArrayList<>();
            users=ps.afficheruser();
            tableview.setItems(FXCollections.observableArrayList(users));

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}





