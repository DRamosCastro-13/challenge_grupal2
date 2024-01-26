package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.models.enums.OrderStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class PurchaseOrderDTO {

    private Long id;
    private String orderNumber;
    private LocalDate shipment_date;
    private String additionalComment;
    private OrderStatus orderStatus;
    private double discount, totalAmount, totalToPay;
    private Set<ProductOrderDTO> productOrders;


    public PurchaseOrderDTO(PurchaseOrder purchaseOrder) {
        this.id = purchaseOrder.getId();
        this.orderNumber = purchaseOrder.getOrderNumber();
        this.shipment_date = purchaseOrder.getShipment_date();
        this.additionalComment = purchaseOrder.getAdditionalComment();
        this.orderStatus = purchaseOrder.getOrderStatus();
        this.discount = purchaseOrder.getDiscount();
        this.totalAmount = purchaseOrder.getTotalAmount();
        this.totalToPay = this.totalAmount *(1-(this.discount/100));
        this.productOrders = purchaseOrder.getProductOrders().stream().map(ProductOrderDTO :: new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getShipment_date() {
        return shipment_date;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Set<ProductOrderDTO> getProductOrders() {
        return productOrders;
    }

    public double getTotalToPay() {
        return totalToPay;
    }
}
