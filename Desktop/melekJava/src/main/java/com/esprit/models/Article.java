package com.esprit.models;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int likes;
    private int dislikes;
    private LocalDate date;
    private String image;

    private int rating ;

    // Constructors
    public Article() {
    }

    public Article(int id, int userId, String title, String content, int likes, int dislikes, LocalDate date, String image) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
        this.image = image;
    }

    public Article(int id, int userId, String title, String content, int likes, int dislikes, LocalDate date, String image, int rating) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
        this.image = image;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ToString
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", date=" + date +
                ", image='" + image + '\'' +
                '}';
    }
}
