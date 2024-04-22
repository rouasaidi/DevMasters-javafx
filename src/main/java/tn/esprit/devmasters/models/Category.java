package tn.esprit.devmasters.models;

public class Category {
    private int id;
    private String type;
    private String image;
    private int userID;

    public Category(int id, String type, String image, int userID) {
        this.id = id;
        this.type = type;
        this.image = image;
        this.userID = userID;
    }

    public Category(String type, String image, int userID) {
        this.type = type;
        this.image = image;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
