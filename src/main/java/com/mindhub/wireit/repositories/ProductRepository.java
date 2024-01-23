package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
