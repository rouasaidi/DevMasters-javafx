package Entities;

import java.util.Date;

public class Commentaire {
    private int id  ;
    private String desc_comments ;
    private Article article_id ;

    public Commentaire() {
    }

    public Commentaire(int id, String desc_comments, Article article) {
        this.id = id;
        this.desc_comments = desc_comments;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc_comments() {
        return desc_comments;
    }

    public void setDesc_comments(String desc_comments) {
        this.desc_comments = desc_comments;
    }

    public Article getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Article article_id) {
        this.article_id = article_id;
    }

    @Override
    public String toString() {
        String articleId = (article_id != null) ? String.valueOf(article_id.getId()) : "N/A";
        return "Commentaire{" +
                "id=" + id +
                ", desc_comments='" + desc_comments + '\'' +
                ", Article ID=" + articleId+
                '}';
    }
}
