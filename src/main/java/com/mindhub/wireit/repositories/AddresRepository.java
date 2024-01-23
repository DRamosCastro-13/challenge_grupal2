package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddresRepository extends JpaRepository<Address,Long> {
}
