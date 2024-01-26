package com.mindhub.wireit.models;

import com.mindhub.wireit.models.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private LocalDate shipment_date;

    private String additionalComment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private double discount, totalAmount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.EAGER)
    private List<ProductOrder> productOrders = new ArrayList<>();

    public PurchaseOrder() {
    }

    public PurchaseOrder(String orderNumber, LocalDate shipment_date, String additionalComment, OrderStatus orderStatus, double discount, double totalAmount) {
        this.orderNumber = orderNumber;
        this.shipment_date = shipment_date;
        this.additionalComment = additionalComment;
        this.orderStatus = orderStatus;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public void addProductOrder(ProductOrder productOrder){
        productOrder.setPurchaseOrder(this);
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
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

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> ProductOrders) {
        this.productOrders = ProductOrders;
    }
}
