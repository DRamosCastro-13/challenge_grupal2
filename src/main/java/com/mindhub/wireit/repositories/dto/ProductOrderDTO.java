package com.mindhub.wireit.repositories.dto;

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

    private Long product_id;

    private String productName, productBrand;

    private double product_price;

    private int quantity;

    public ProductOrderDTO(ProductOrder productOrder) {
        this.id = productOrder.getId();
        this.product_id = productOrder.getProduct().getId();
        this.productName = productOrder.getProduct().getName();
        this.productBrand = productOrder.getProduct().getBrand();
        this.product_price = productOrder.getProduct().getPrice();
        this.quantity = productOrder.getProduct().getStock();
    }

    public Long getId() {
        return id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public double getProduct_price() {
        return product_price;
    }

    public int getQuantity() {
        return quantity;
    }
}
