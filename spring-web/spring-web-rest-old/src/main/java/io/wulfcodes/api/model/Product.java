package io.wulfcodes.api.model;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private String type;

    // Constructors, Getters, Setters
    public Product(Long id, String name, Double price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + price.intValue();
    }
}