package tn.esprit.azizapplicationgui.models;

import java.util.Date;
import java.util.List;

public class Ticket {
    int ref_ticket;
    int price;
    int event_id;
    String type;
    int user_id;
    String event_name;

    String place;
    private Date date_event;

    public Ticket(int ref_ticket, int price, int event_id, String type, int user_id) {
        this.ref_ticket = ref_ticket;
        this.price = price;
        this.event_id = event_id;
        this.type = type;
        this.user_id = user_id;
    }


    public Ticket(int price, int event_id, String type, int user_id) {
        this.price = price;
        this.event_id = event_id;
        this.type = type;
        this.user_id = user_id;
    }
    public Ticket(int price, int event_id, String type) {
        this.price = price;
        this.event_id = event_id;
        this.type = type;
        this.user_id = user_id;
    }

    public Ticket() {

    }


    @Override
    public String toString() {
        return "Ticket{" +
                "ref_ticket=" + ref_ticket +
                ", price=" + price +
                ", event_id=" + event_id +
                ", type='" + type + '\'' +
                ", user_id=" + user_id +
                '}';
    }



    public int getRef_ticket() {
        return ref_ticket;
    }

    public void setRef_ticket(int ref_ticket) {
        this.ref_ticket = ref_ticket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setEvent_name(String eventname) {
        this.event_name = eventname;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }






}

