package Entities;

import java.util.Date;
import java.util.List;

public class Article {
    private int id ;
    private String name_article ,type_article,pic_article,desc_article ;
    private List<Commentaire> commentaires ;


    public Article(int id, String name_article, String type_article, String pic_article, String desc_article, List<Commentaire> commentaires) {
        this.id = id;
        this.name_article = name_article;
        this.type_article = type_article;
        this.pic_article = pic_article;
        this.desc_article = desc_article;
        this.commentaires =commentaires;
    }

    // Getters and setters for id, content, titre, and datepubarticle

    public List<Commentaire> getCommentaires() {
        return (List<Commentaire>) commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires =commentaires;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_article() {
        return name_article;
    }

    public void setName_article(String name_article) {
        this.name_article = name_article;
    }

    public String getType_article() {
        return type_article;
    }

    public void setType_article(String type_article) {
        this.type_article = type_article;
    }
    public String getPic_article() {
        return pic_article;
    }

    public void setPic_article(String pic_article) {
        this.pic_article = pic_article;
    }
    public String getDesc_article() {
        return desc_article;
    }

    public void setDesc_article(String desc_article) {
        this.desc_article = desc_article;
    }

    public Article(String name_article, String type_article, String desc_article, String pic_article) {
        this.name_article = name_article;
        this.type_article = type_article;
        this.desc_article = desc_article;
        this.pic_article = pic_article;

    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name_article='" + name_article + '\'' +
                ", type_article='" + type_article + '\'' +
                ", pic_article=" + pic_article +
                ", desc_article=" + desc_article +
                ",Commentaire:{"+commentaires+"}"+
                '}';
    }
}
