package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.ProductOrder;
import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.models.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProductOrderDTO {

    private Long id;

    private Product product;


    private PurchaseOrder purchaseOrder;

    private byte quantity;

    public ProductOrderDTO(ProductOrder productOrder) {
        this.id = productOrder.getId();
        this.product = productOrder.getProduct();
        this.purchaseOrder = productOrder.getPurchaseOrder();
        this.quantity = productOrder.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public byte getQuantity() {
        return quantity;
    }
}
