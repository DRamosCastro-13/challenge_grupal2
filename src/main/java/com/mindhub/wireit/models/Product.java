package com.mindhub.wireit.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, brand, image_url;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    private double price, discount;

    private int stock;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductOrder> ProductOrders = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String brand, String image_url, String description, ProductCategory category, double price, double discount, int stock) {
        this.name = name;
        this.brand = brand;
        this.image_url = image_url;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
    }

    public void addProductOrder(ProductOrder productOrder){
        productOrder.setProduct(this);
        this.ProductOrders.add(productOrder);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<ProductOrder> getProductOrders() {
        return ProductOrders;
    }

    public void setProductOrders(List<ProductOrder> ProductOrders) {
        this.ProductOrders = ProductOrders;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
