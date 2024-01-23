package com.mindhub.wireit.models;

import jakarta.persistence.*;
import com.mindhub.wireit.models.enums.productCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, brand, image_url, description;

    @Enumerated(EnumType.STRING)
    private productCategory productCategory;

    private double price, discount;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Supplier> suppliers = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductOrder> ProductOrders = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String brand, String image_url, String description, com.mindhub.wireit.models.enums.productCategory productCategory, double price, double discount) {
        this.name = name;
        this.brand = brand;
        this.image_url = image_url;
        this.description = description;
        this.productCategory = productCategory;
        this.price = price;
        this.discount = discount;
    }

    public void addProductOrder(ProductOrder productOrder){
        productOrder.setProduct(this);
        this.ProductOrders.add(productOrder);
    }

    public void addSupplier(Supplier supplier){
        supplier.setProduct(this);
        this.suppliers.add(supplier);
    }

    public void setId(Long id) {
        this.id = id;
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

    public com.mindhub.wireit.models.enums.productCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(com.mindhub.wireit.models.enums.productCategory productCategory) {
        this.productCategory = productCategory;
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

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<ProductOrder> getProductOrders() {
        return ProductOrders;
    }

    public void setProductOrders(List<ProductOrder> ProductOrders) {
        this.ProductOrders = ProductOrders;
    }


}
