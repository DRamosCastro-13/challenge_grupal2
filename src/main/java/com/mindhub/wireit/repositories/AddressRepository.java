package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface AddressRepository extends JpaRepository<Address,Long> {
}
