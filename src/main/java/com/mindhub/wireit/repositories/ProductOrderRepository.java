package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
