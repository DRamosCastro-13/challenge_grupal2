package com.mindhub.wireit.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer_name;

    private Short stock;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String customer_name, Short stock) {
        this.customer_name = customer_name;
        this.stock = stock;
    }

    public void addProduct(Product product) {
        product.setSupplier(this);
        this.products.add(product);
    }

    public Long getId() {
        return id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Short getStock() {
        return stock;
    }

    public void setStock(Short stock) {
        this.stock = stock;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
