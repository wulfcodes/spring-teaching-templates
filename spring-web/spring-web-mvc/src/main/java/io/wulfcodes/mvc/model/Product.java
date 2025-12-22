package io.wulfcodes.mvc.model;

public class Product {

    private String productName;
    private String category;
    private Integer price;
    private Integer discount;
    private Boolean inStock;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Product{" +
            "productName='" + productName + '\'' +
            ", category='" + category + '\'' +
            ", price=" + price +
            ", discount=" + discount +
            ", inStock=" + inStock +
            '}';
    }
}
