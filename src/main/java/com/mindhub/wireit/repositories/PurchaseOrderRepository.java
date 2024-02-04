package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    PurchaseOrder findByOrderNumber(String orderNumber);
    boolean existsByOrderNumber(String orderNumber);
}
