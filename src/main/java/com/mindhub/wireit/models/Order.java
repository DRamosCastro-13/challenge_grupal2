package com.mindhub.wireit.models;

import com.mindhub.wireit.models.enums.orderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private LocalDate shipment_date;

    private String additionalComment;

    @Enumerated(EnumType.STRING)
    private orderStatus orderStatus;

    private double discount, totalAmount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<productOrder> productOrders = new HashSet<>();

    public Order() {
    }

    public Order(String orderNumber, LocalDate shipment_date, String additionalComment, orderStatus orderStatus, double discount, double totalAmount) {
        this.orderNumber = orderNumber;
        this.shipment_date = shipment_date;
        this.additionalComment = additionalComment;
        this.orderStatus = orderStatus;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public void addProductOrder(productOrder productOrder){
        productOrder.setOrder(this);
        this.productOrders.add(productOrder);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(LocalDate shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public orderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(orderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<productOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Set<productOrder> productOrders) {
        this.productOrders = productOrders;
    }
}
