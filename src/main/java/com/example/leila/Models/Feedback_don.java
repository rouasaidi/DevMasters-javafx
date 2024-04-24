package com.example.leila.Models;

import java.util.Date;

public class Feedback_don {
    private int id;

    private int donation_id;
    private int user_id;

    private String description;

    private Date date_feedback;

    public Feedback_don() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_feedback() {
        return date_feedback;
    }

    public void setDate_feedback(Date date_feedback) {
        this.date_feedback = date_feedback;
    }

    @Override
    public String toString() {
        return "Feedback_don{" +
                "id=" + id +
                ", donation_id=" + donation_id +
                ", user_id=" + user_id +
                ", description='" + description + '\'' +
                ", date_feedback=" + date_feedback +
                '}';
    }
}
