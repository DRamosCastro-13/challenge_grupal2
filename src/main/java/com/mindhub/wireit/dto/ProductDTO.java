package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.ProductOrder;
import com.mindhub.wireit.models.Supplier;
import com.mindhub.wireit.models.enums.ProductCategory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {

    private Long id;

    private String name, brand, image_url, description;

    private ProductCategory productCategory;

    private double price, discount;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.image_url = product.getImage_url();
        this.description = product.getDescription();
        this.productCategory = product.getProductCategory();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

}
