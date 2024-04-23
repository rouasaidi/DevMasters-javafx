package Services;

import Entities.Article;
import Entities.Commentaire;
import Utils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements Isevice<Commentaire>{
    DB db =new DB();
    @Override
    public int Add(Commentaire commentaire) {
        // Assurez-vous que commentaire.getArticle_id() n'est pas null et a un id valide
        if (commentaire.getArticle_id() == null || commentaire.getArticle_id().getId() == 0) {
            System.out.println("Article ID is null or invalid");
            return 0;
        }

        String sql = "INSERT INTO comments (article_id, desc_comments) VALUES (?, ?)";

        int ok = 0;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, commentaire.getArticle_id().getId());
            db.getPstm().setString(2, commentaire.getDesc_comments());

            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("Failed to add comment: " + e.getMessage());
        }
        return ok;
    }



    @Override
    public int Edit(Commentaire commentaire) {
        String sql = "UPDATE comments SET desc_comments=? WHERE id=? AND article_id=?";
        int ok = 0;
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, commentaire.getDesc_comments());
            db.getPstm().setInt(2, commentaire.getId());
            db.getPstm().setInt(3, commentaire.getArticle_id().getId()); // Assurez-vous que getArticle_id() retourne l'ID de l'article
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }


    @Override
    public int Delete(int id){
        String sql = "DELETE FROM comments WHERE id=? ";
        int ok =0 ;
        try {
            //initialisation
            db.initPrepar(sql);
            //passage de valeur
            db.getPstm().setInt(1,id);
            //execution
            ok =db.executeMaj();
            db.closeConnection();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }

    @Override
    public List<Commentaire> getAll(){
        String sql = "SELECT * FROM comments ";
        List<Commentaire> commentaire = new ArrayList<>();
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Commentaire comment = new Commentaire();
                comment.setId(rs.getInt(1));
                comment.setDesc_comments(rs.getString(2));
                Object c = rs.getObject(3);
                comment.setArticle_id(new ServiceArticle().getArticle((Integer) c));
                commentaire.add(comment);
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return commentaire;
    }


    public List<Commentaire> getComments(int id) {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT c.id, c.desc_comments, c.article_id, a.name_article FROM comments c " +
                "JOIN article a ON c.article_id = a.id " +
                "WHERE c.article_id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(rs.getInt("id"));
                commentaire.setDesc_comments(rs.getString("desc_comments"));

                // Set the associated article for the comment
                Article article = new Article();
                article.setId(rs.getInt("article_id"));
                article.setName_article(rs.getString("name_article"));
                commentaire.setArticle_id(article);

                // Add the comment to the list
                commentaires.add(commentaire);
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return commentaires;
    }

    public Commentaire getCommentById(int id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        Commentaire commentaire = null;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            ResultSet rs = db.executeSelect();
            if (rs.next()) {
                commentaire = new Commentaire();
                commentaire.setId(rs.getInt(1));
                commentaire.setDesc_comments(rs.getString(2));
                Object c =rs.getObject(3);
                commentaire.setArticle_id(new ServiceArticle().getArticle((Integer) c));


            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return commentaire;
    }



}
