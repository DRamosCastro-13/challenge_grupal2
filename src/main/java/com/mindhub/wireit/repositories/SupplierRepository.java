package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
