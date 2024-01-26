package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.models.ProductOrder;
import com.mindhub.wireit.repositories.ProductOrderRepository;
import com.mindhub.wireit.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Override
    public void saveProductOrder(ProductOrder productOrder) {
        productOrderRepository.save(productOrder);
    }
}
