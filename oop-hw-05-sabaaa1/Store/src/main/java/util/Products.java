package util;

public class Products {
    private double price;
    private String name;
    private String productId;
    private String imageFile;

    public Products(double price, String name, String productId, String imageFile) {
        this.price = price;
        this.name = name;
        this.productId = productId;
        this.imageFile = imageFile;
    }

    public Products() {

    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public String getImageFile() {
        return imageFile;
    }
}
