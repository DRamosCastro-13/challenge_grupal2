package com.mindhub.wireit.dto.bodyjson;

import java.util.List;

public class PurchaseRequest {
    private List<NewCart> items;
    private String comment;
    private double discount;

    public List<NewCart> getItems() {
        return items;
    }

    public String getComment() {
        return comment;
    }

    public double getDiscount() {
        return discount;
    }
}
