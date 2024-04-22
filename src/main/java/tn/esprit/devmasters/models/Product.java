package tn.esprit.devmasters.models;

public class Product {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private int totalSales;
    private String image;
    private int userID;
    private int categoryID;

    public Product(int id, String name, String description, int quantity, double price, int totalSales, String image, int userID, int categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.totalSales = totalSales;
        this.image = image;
        this.userID = userID;
        this.categoryID = categoryID;
    }

    public Product(String name, String description, int quantity, double price, int totalSales, String image, int userID, int categoryID) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.totalSales = totalSales;
        this.image = image;
        this.userID = userID;
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalSales=" + totalSales +
                ", image='" + image + '\'' +
                '}';
    }
}
