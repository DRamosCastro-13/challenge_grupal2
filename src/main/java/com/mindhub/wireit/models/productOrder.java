package com.mindhub.wireit.models;

import jakarta.persistence.*;

@Entity
public class productOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    private byte quantity;

    public productOrder() {
    }

    public productOrder(byte quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public byte getQuantity() {
        return quantity;
    }

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }
}
