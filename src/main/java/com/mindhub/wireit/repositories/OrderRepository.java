package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
