package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.newClient;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.repositories.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    List<Client> getAllClient();

    List<ClientDTO> getAllClientDTO();
    ResponseEntity<ClientDTO> getClient(Authentication authentication);
    ResponseEntity<String> createClient(newClient newClient);
}
