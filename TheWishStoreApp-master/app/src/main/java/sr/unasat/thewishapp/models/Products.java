package sr.unasat.thewishapp.models;

public class Products {

    private int id;
    private int pictureResource;
    private String name;
    private double price;
    private String magnitude;
    private String brand;

    public Products(int id, int pictureResource, String name, double price, String magnitude, String brand) {
        this.id = id;
        this.pictureResource = pictureResource;
        this.name = name;
        this.price = price;
        this.magnitude = magnitude;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPictureResource() {
        return pictureResource;
    }

    public void setPictureResource(int pictureResource) {
        this.pictureResource = pictureResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
