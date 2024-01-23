package com.mindhub.wireit.models;

import jakarta.persistence.*;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer_name;

    private Short stock;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Supplier() {
    }

    public Supplier(String customer_name, Short stock) {
        this.customer_name = customer_name;
        this.stock = stock;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
