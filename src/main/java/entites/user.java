package entites;

import java.util.Objects;

public class user { private int id;
    private int phone;
    private int cin ;
    private String name,password,reset_token,image,email;
    private String   roles;
    private  boolean is_banned, is_verified;

    public user() {
    }

    public user(int id, int phone, int cin, String name, String password, String reset_token, String image, String email, String roles, boolean is_banned, boolean is_verified) {
        this.id = id;
        this.phone = phone;
        this.cin = cin;
        this.name = name;
        this.password = password;
        this.reset_token = reset_token;
        this.image = image;
        this.email = email;
        this.roles = roles;
        this.is_banned = true;
        this.is_verified = false;
    }
    public user(int phone, int cin, String name, String password, String image, String email, String roles) {
        this.phone = phone;
        this.cin = cin;
        this.name = name;
        this.password = password;
      //  this.reset_token = reset_token;
        this.image = image;
        this.email = email;
        this.roles = roles;
        this.is_verified = false;
        //this.is_banned = is_banned;
       // this.is_verified = is_verified;
        this.is_banned = true;
    }

    public user(String name, String email, int cin, int phone, String roles, String image,String password) {
        this.name=name;
        this.email=email;
        this.cin=cin;
        this.phone=phone;
        this.roles=roles;
        this.image=image;
        this.password=password;
    }


    public  int getId() {
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

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", phone=" + phone +
                ", cin=" + cin +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", reset_token='" + reset_token + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", is_banned=" + is_banned +
                ", is_verified=" + is_verified +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof user user)) return false;
        return getId() == user.getId() && getPhone() == user.getPhone() && getCin() == user.getCin() && isIs_banned() == user.isIs_banned() && isIs_verified() == user.isIs_verified() && Objects.equals(getName(), user.getName()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getReset_token(), user.getReset_token()) && Objects.equals(getImage(), user.getImage()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhone(), getCin(), getName(), getPassword(), getReset_token(), getImage(), getEmail(), getRoles(), isIs_banned(), isIs_verified());
    }
}



