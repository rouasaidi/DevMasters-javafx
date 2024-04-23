package tests;

import Services.ServiceCommentaire;

public class Main {
    public static void main(String[] args) {



        /*                    ajouter article ////////////////////////////////////////////////////
        Article nouvelArticle = new Article();
        nouvelArticle.setName_article("aaa");
        nouvelArticle.setType_article("Teteze");
        nouvelArticle.setPic_article("tezte");
        nouvelArticle.setDesc_article("reter");

        ArticleImpl articleService = new ArticleImpl();

        int result = articleService.Add(nouvelArticle);

        if (result > 0) {
            System.out.println("L'article a été ajouté avec succès à la base de données.");
        } else {
            System.out.println("Une erreur s'est produite lors de l'ajout de l'article à la base de données.");
        }



        /* editer article ////////////////////////////////////////////////////
        ArticleImpl articleService1 = new ArticleImpl();

        Article articleToUpdate = new Article();
        articleToUpdate.setId(6); // Remplacez 1 par l'ID de l'article que vous souhaitez modifier
        articleToUpdate.setName_article("Nouveau nom de l'article");
        articleToUpdate.setType_article("Nouveau type d'article");
        articleToUpdate.setPic_article("Nouvelle image de l'article");
        articleToUpdate.setDesc_article("Nouvelle description de l'article");

        int rowsAffected = articleService1.Edit(articleToUpdate);

        if (rowsAffected > 0) {
            System.out.println("L'article a été modifié avec succès dans la base de données.");
        } else {
            System.out.println("La modification de l'article a échoué.");
        }



         */

/* supprimer article ////////////////////////////////////////////////////////////////////////
        ArticleImpl articleService2 = new ArticleImpl();

        int articleIdToDelete = 13;

        int rowsAffected1 = articleService2.Delete(articleIdToDelete);

        if (rowsAffected1 > 0) {
            System.out.println("L'article avec l'ID " + articleIdToDelete + " a été supprimé avec succès de la base de données.");
        } else {
            System.out.println("La suppression de l'article a échoué.");
        }


*/




        ServiceCommentaire commentaireService = new ServiceCommentaire();
/*ajouter commentaire /////////////////////////////////////////////////////////////////////
        Commentaire commentaire = new Commentaire();
        commentaire.setDesc_comments("Ceci est un nouveau commentaire."); // Assurez-vous que la description est correctement définie

        int articleId = 22;

        Article article = new Article();
        article.setId(articleId);
        commentaire.setArticle_id(article);

        int ajoutCommentaire = commentaireService.Add(commentaire);

        if (ajoutCommentaire > 0) {
            System.out.println("Le commentaire a été ajouté avec succès à l'article d'ID " + articleId + ".");
        } else {
            System.out.println("Une erreur s'est produite lors de l'ajout du commentaire à l'article d'ID " + articleId + ".");
        }
*/

    /*    editer un commentaire /////////////////////////////////////////////////
        Commentaire commentaire = new Commentaire();
        commentaire.setId(12); // ID du commentaire à éditer
        commentaire.setDesc_comments("Nouveau contenu du commentaire"); // Nouveau contenu du commentaire

        int articleId = 22;

        Article article = new Article();
        article.setId(articleId);
        commentaire.setArticle_id(article);

        int editCommentaire = commentaireService.Edit(commentaire);

        if (editCommentaire > 0) {
            System.out.println("Le commentaire a été modifié avec succès.");
        } else {
            System.out.println("Une erreur s'est produite lors de la modification du commentaire.");
        }*/


//suppression commentaire /////////////////////////////////////////////////
        int commentaireId = 13;

        int suppressionCommentaire = commentaireService.Delete(commentaireId);

        if (suppressionCommentaire > 0) {
            System.out.println("Le commentaire a été supprimé avec succès.");
        } else {
            System.out.println("Une erreur s'est produite lors de la suppression du commentaire.");
        }











    }
}




