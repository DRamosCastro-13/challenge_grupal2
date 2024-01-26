package com.mindhub.wireit.Utils;

import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.ProductOrder;
import com.mindhub.wireit.models.PurchaseOrder;

import java.util.List;

public class CalculateTotalAmount {
    private static List<ProductOrder> productOrders;

    // constructor y otros métodos

    public static void calculateTotalPrice(PurchaseOrder purchaseOrder, List<ProductOrder> productOrders) {
        double total = 0;

        for (ProductOrder productOrder : productOrders) {
            Product product = productOrder.getProduct();
            int quantity = productOrder.getQuantity();
            double price = product.getPrice();

            total += quantity * price;
        }
        purchaseOrder.setTotalAmount(total);
    }
}
