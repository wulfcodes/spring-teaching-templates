package io.wulfcodes.api.model;

public class Product {
    private Long id;
    private String name;
    private Double price;

    // Constructors, Getters, Setters
    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    @Override
    public int hashCode() {
        return name.hashCode() + price.intValue();
    }
}