package com.mindhub.wireit.repositories.dto.bodyjson;

import com.mindhub.wireit.models.enums.ProductCategory;

public class NewProduct {
    private double price, discount;
    private String name, brand, image_url, description;
    private ProductCategory productCategory;
    private int stock;

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
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

    public int getStock() {
        return stock;
    }
}
