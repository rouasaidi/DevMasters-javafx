package Services;

import Entities.Article;
import Entities.Commentaire;
import Utils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceArticle implements Isevice<Article> {

    DB db = new DB();

    @Override
    public int Add(Article article) {
        String sql = "INSERT INTO article ( name_article, type_article, pic_article, desc_article) VALUES (?,?,?,?)";
        int ok = 0;
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, article.getName_article());
            db.getPstm().setString(2, article.getType_article());
            db.getPstm().setString(3, article.getDesc_article());
            db.getPstm().setString(4, article.getPic_article());

            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }


    @Override
    public int Edit(Article article){
        String sql = "update article set name_article=?,type_article=?,pic_article=?,desc_article=? where id=?";
        int ok = 0;
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, article.getName_article());
            db.getPstm().setString(2, article.getType_article());
            db.getPstm().setString(3, article.getPic_article());
            db.getPstm().setString(4, article.getDesc_article());
            db.getPstm().setInt(5, article.getId());
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }
    @Override
    public int Delete(int id) {
        int ok = 0;

        // Premièrement, supprimer tous les commentaires associés à l'article
        String sqlDeleteComments = "DELETE FROM comments WHERE article_id=?";
        try {
            db.initPrepar(sqlDeleteComments);
            db.getPstm().setInt(1, id);
            ok = db.executeMaj(); // Exécuter la suppression des commentaires
        } catch (Exception e)
        {
            System.out.println("Failed to delete comments: " + e.getMessage());
            return 0; // Échec de la suppression des commentaires
        }

        // Deuxièmement, supprimer l'article
        String sqlDeleteArticle = "DELETE FROM article WHERE id=?";
        try {
            db.initPrepar(sqlDeleteArticle);
            db.getPstm().setInt(1, id);
            ok = db.executeMaj(); // Exécuter la suppression de l'article
        } catch (Exception e) {
            System.out.println("Failed to delete article: " + e.getMessage());
            return 0; // Échec de la suppression de l'article
        }

        db.closeConnection();
        return ok; // Retourner le résultat de la suppression de l'article
    }

    @Override
    public List<Article> getAll(){
        String sql = "SELECT * FROM article";
        List<Article> articles = new ArrayList<>();
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt(1));
                article.setName_article(rs.getString(3));
                article.setType_article(rs.getString(4));
                article.setPic_article(rs.getString(5));
                article.setDesc_article(rs.getString(6));

                List<Commentaire> comments = new ServiceCommentaire().getComments(article.getId());
                for (Commentaire comment : comments) {
                    comment.setArticle_id(article);
                }

                article.setCommentaires(comments);
                articles.add(article);
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return articles;
    }



    public Article getArticle(int id) {
        String sql = "SELECT * FROM article WHERE id = ?";
        Article article = null;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                article = new Article();
                article.setId(rs.getInt(1));
                article.setName_article(rs.getString(2));
                article.setType_article(rs.getString(3));
                article.setPic_article(rs.getString(2));
                article.setDesc_article(rs.getString(3));
                List<Commentaire> comments = new ServiceCommentaire().getComments(article.getId());
                for (Commentaire comment : comments) {
                    comment.setArticle_id(article);
                }

                article.setCommentaires(comments);
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return article;
    }






//    public void addEmployeeSearch() {
//
//        FilteredList<employeeData> filter = new FilteredList<>(addEmployeeList, e -> true);
//
//        addEmployee_search.textProperty().addListener((Observable, oldValue, newValue) -> {
//
//            filter.setPredicate(predicateEmployeeData -> {
//
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                String searchKey = newValue.toLowerCase();
//
//                if (predicateEmployeeData.getEmployeeId().toString().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getPhoneNum().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            });
//        });

//        SortedList<employeeData> sortList = new SortedList<>(filter);
//
//        sortList.comparatorProperty().bind(addEmployee_tableView.comparatorProperty());
//        addEmployee_tableView.setItems(sortList);
//    }
//
//    public ObservableList<employeeData> addEmployeeListData() {
//
//        ObservableList<employeeData> listData = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM employee";
//
//        connect = database.connectDb();
//
//        try {
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//            employeeData employeeD;
//
//            while (result.next()) {
//                employeeD = new employeeData(result.getInt("employee_id"),
//                        result.getString("firstName"),
//                        result.getString("lastName"),
//                        result.getString("gender"),
//                        result.getString("phoneNum"),
//                        result.getString("position"),
//                        result.getString("image"),
//                        result.getDate("date"));
//                listData.add(employeeD);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listData;
//    }
//    private ObservableList<employeeData> addEmployeeList;


    ///////////////////////////
//    public void addEmployeeInsertImage() {
//
//        FileChooser open = new FileChooser();
//        File file = open.showOpenDialog(main_form.getScene().getWindow());
//
//        if (file != null) {
//            getData.path = file.getAbsolutePath();
//
//            image = new Image(file.toURI().toString(), 101, 127, false, true);
//            addEmployee_image.setImage(image);
//        }
//    }
//
//    private String[] positionList = {"Marketer Coordinator", "Web Developer (Back End)", "Web Developer (Front End)", "App Developer"};


}
