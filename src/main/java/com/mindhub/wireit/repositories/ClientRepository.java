package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
