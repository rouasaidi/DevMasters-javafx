package com.esprit.models;

import java.time.LocalDate;
import java.util.Objects;

public class Comment {
    private int id;
    private Integer articleId;  // Using Integer to allow null values
    private Integer userId;     // Using Integer to allow null values
    private String content;
    private LocalDate dateCmt;

    // Constructors
    public Comment() {
    }

    public Comment(int id, Integer articleId, Integer userId, String content, LocalDate dateCmt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.dateCmt = dateCmt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateCmt() {
        return dateCmt;
    }

    public void setDateCmt(LocalDate dateCmt) {
        this.dateCmt = dateCmt;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ToString
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", dateCmt=" + dateCmt +
                '}';
    }
}
