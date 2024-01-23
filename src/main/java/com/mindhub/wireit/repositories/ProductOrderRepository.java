package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
