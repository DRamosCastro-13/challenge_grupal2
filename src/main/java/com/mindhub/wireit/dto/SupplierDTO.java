package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.Supplier;
import jakarta.persistence.*;

public class SupplierDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer_name;

    private Short stock;


    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.customer_name = supplier.getCustomer_name();
        this.stock = supplier.getStock();
    }

    public Long getId() {
        return id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public Short getStock() {
        return stock;
    }

}
