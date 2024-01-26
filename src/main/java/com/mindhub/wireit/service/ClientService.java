package com.mindhub.wireit.service;

import com.mindhub.wireit.repositories.dto.ClientDTO;
import com.mindhub.wireit.repositories.dto.bodyjson.NewAddress;
import com.mindhub.wireit.repositories.dto.bodyjson.NewClient;
import com.mindhub.wireit.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    List<Client> getAllClient();

    List<ClientDTO> getAllClientDTO();
    ResponseEntity<ClientDTO> getClient(Authentication authentication);
    ResponseEntity<String> createClient(NewClient newClient);
    ResponseEntity<String> newAddress(NewAddress newAddress, Authentication authentication);
}
