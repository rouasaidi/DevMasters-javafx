package services;

import java.io.Serializable;

public class UserSession implements Serializable {
    private int id;
    private int phone;
    private int cin ;
    private String name,password,reset_token,image,email;
    private String   roles;
    private  boolean is_banned, is_verified;
    //private String page;

    // Constructeur

    public UserSession( int phone, int cin, String name, String password, String reset_token, String image, String email, String roles, boolean is_banned, boolean is_verified) {

        this.phone = phone;
        this.cin = cin;
        this.name = name;
        this.password = password;
        this.reset_token = reset_token;
        this.image = image;
        this.email = email;
        this.roles = roles;
        this.is_banned = is_banned;
        this.is_verified = is_verified;
    }

    public UserSession(String name, String email, String roles, String image) {
        this.name=name;
        this.email=email;
        this.roles=roles;
        this.image=image;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
    /*public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }*/
}

